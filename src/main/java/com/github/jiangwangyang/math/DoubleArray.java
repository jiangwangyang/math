package com.github.jiangwangyang.math;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class DoubleArray implements Iterable<Double> {

    private final double[] values;
    private final long[] bitmap;
    private final int[] prefix;
    private final int length;

    private DoubleArray(double[] values, long[] bitmap, int[] prefix, int length) {
        this.values = values;
        this.bitmap = bitmap;
        this.prefix = prefix;
        this.length = length;
    }

    public static DoubleArray fromValueAndMissing(double[] value, boolean[] missing) {
        if (value.length != missing.length) {
            throw new IllegalArgumentException("missing[] length must match array[]");
        }
        int length = value.length;
        double[] temp = new double[length];
        long[] bitmap = new long[(length + 63) >>> 6];
        int cnt = 0;
        for (int i = 0; i < length; i++) {
            if (!missing[i]) {
                bitmap[i >>> 6] |= 1L << (i & 63);
                temp[cnt++] = value[i];
            }
        }
        double[] values = Arrays.copyOf(temp, cnt);
        int words = bitmap.length;
        int[] prefix = new int[words + 1];
        for (int w = 0; w < words; w++) {
            prefix[w + 1] = prefix[w] + Long.bitCount(bitmap[w]);
        }
        return new DoubleArray(values, bitmap, prefix, length);
    }

    public static DoubleArray fromList(List<Double> list) {
        return fromIterator(list.iterator(), list.size());
    }

    public static DoubleArray fromArray(Double[] array) {
        return fromIterator(Arrays.stream(array).iterator(), array.length);
    }

    private static DoubleArray fromIterator(Iterator<Double> iterator, int length) {
        double[] temp = new double[length];
        long[] bitmap = new long[(length + 63) >>> 6];
        int cnt = 0;
        for (int i = 0; i < length && iterator.hasNext(); i++) {
            Double v = iterator.next();
            if (v != null) {
                bitmap[i >>> 6] |= 1L << (i & 63);
                temp[cnt++] = v;
            }
        }
        double[] values = Arrays.copyOf(temp, cnt);
        int words = bitmap.length;
        int[] prefix = new int[words + 1];
        for (int w = 0; w < words; w++) {
            prefix[w + 1] = prefix[w] + Long.bitCount(bitmap[w]);
        }
        return new DoubleArray(values, bitmap, prefix, length);
    }

    public Double get(int i) {
        if (i < 0 || i >= length) {
            throw new IndexOutOfBoundsException();
        }
        int w = i >>> 6;
        long m = 1L << (i & 63);
        if ((bitmap[w] & m) == 0) {
            return null;
        }
        int rank = prefix[w] + Long.bitCount(bitmap[w] & (m - 1));
        return values[rank];
    }

    public double getValue(int i, double defaultValue) {
        if (i < 0 || i >= length) {
            throw new IndexOutOfBoundsException();
        }
        int w = i >>> 6;
        long m = 1L << (i & 63);
        if ((bitmap[w] & m) == 0) {
            return defaultValue;
        }
        int rank = prefix[w] + Long.bitCount(bitmap[w] & (m - 1));
        return values[rank];
    }

    public boolean isMissing(int i) {
        if (i < 0 || i >= length) {
            throw new IndexOutOfBoundsException();
        }
        return (bitmap[i >>> 6] & (1L << (i & 63))) == 0;
    }

    public int size() {
        return values.length;
    }

    public int length() {
        return length;
    }

    public Stream<Double> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    @NonNull
    public Iterator<Double> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < length;
            }

            @Override
            public Double next() {
                return get(i++);
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Double> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Double> spliterator() {
        return Iterable.super.spliterator();
    }
}
