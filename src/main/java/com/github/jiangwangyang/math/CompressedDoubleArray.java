package com.github.jiangwangyang.math;

import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public final class CompressedDoubleArray implements DoubleArray {

    private final double[] values;
    private final long[] bitmap;
    private final int[] prefix;
    private final int length;

    private CompressedDoubleArray(double[] values, long[] bitmap, int[] prefix, int length) {
        this.values = values;
        this.bitmap = bitmap;
        this.prefix = prefix;
        this.length = length;
    }

    public static CompressedDoubleArray fromValueMissing(double[] value, boolean[] missing) {
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
        return new CompressedDoubleArray(values, bitmap, prefix, length);
    }

    public static CompressedDoubleArray fromList(List<Double> list) {
        double[] temp = new double[list.size()];
        long[] bitmap = new long[(list.size() + 63) >>> 6];
        int cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            Double v = list.get(i);
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
        return new CompressedDoubleArray(values, bitmap, prefix, list.size());
    }

    public static CompressedDoubleArray fromArray(Double[] array) {
        return fromList(Arrays.asList(array));
    }

    public static CompressedDoubleArray fromDoubles(Double... doubles) {
        return fromList(Arrays.asList(doubles));
    }

    public static CompressedDoubleArray fromArray(double[] array) {
        return fromValueMissing(array, new boolean[array.length]);
    }

    public static CompressedDoubleArray fromValues(double... values) {
        return fromValueMissing(values, new boolean[values.length]);
    }

    @Override
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

    @Override
    public double getValue(int i) {
        if (i < 0 || i >= length) {
            throw new IndexOutOfBoundsException();
        }
        int w = i >>> 6;
        long m = 1L << (i & 63);
        if ((bitmap[w] & m) == 0) {
            throw new IllegalArgumentException("index " + i + " is missing");
        }
        int rank = prefix[w] + Long.bitCount(bitmap[w] & (m - 1));
        return values[rank];
    }

    @Override
    public double getValueOrElse(int i, double defaultValue) {
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

    @Override
    public boolean isMissing(int i) {
        if (i < 0 || i >= length) {
            throw new IndexOutOfBoundsException();
        }
        return (bitmap[i >>> 6] & (1L << (i & 63))) == 0;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public CompressedDoubleArray missingTo(double value) {
        if (size() == length()) {
            return this;
        }
        double[] newValueArray = new double[length()];
        for (int i = 0; i < length(); i++) {
            newValueArray[i] = getValueOrElse(i, value);
        }
        return CompressedDoubleArray.fromValueMissing(newValueArray, new boolean[length()]);
    }

    @Override
    public CompressedDoubleArray map(DoubleUnaryOperator operator) {
        double[] value = new double[length()];
        boolean[] missing = new boolean[length()];
        for (int i = 0; i < length(); i++) {
            if (isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = operator.applyAsDouble(getValue(i));
            }
        }
        return CompressedDoubleArray.fromValueMissing(value, missing);
    }

    @Override
    public CompressedDoubleArray zip(DoubleArray other, DoubleBinaryOperator operator) {
        if (length() != other.length()) {
            throw new IllegalArgumentException("DoubleArray must have the same length");
        }
        double[] value = new double[length()];
        boolean[] missing = new boolean[length()];
        for (int i = 0; i < length(); i++) {
            if (isMissing(i) || other.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = operator.applyAsDouble(getValue(i), other.getValue(i));
            }
        }
        return CompressedDoubleArray.fromValueMissing(value, missing);
    }

}
