package com.github.jiangwangyang.math;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public final class CompressedDoubleArray implements DoubleArray {

    private final double[] values;
    private final long[] bitmap;
    private final int[] prefix;
    private final int length;

    CompressedDoubleArray(double[] values, long[] bitmap, int[] prefix, int length) {
        this.values = values;
        this.bitmap = bitmap;
        this.prefix = prefix;
        this.length = length;
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
    public DoubleArray missingTo(double value) {
        if (size() == length()) {
            return this;
        }
        double[] newValueArray = new double[length()];
        for (int i = 0; i < length(); i++) {
            newValueArray[i] = getValueOrElse(i, value);
        }
        return new FullDoubleArray(newValueArray);
    }

    @Override
    public DoubleArray map(DoubleUnaryOperator operator) {
        double[] value = new double[size()];
        int cnt = 0;
        for (int i = 0; i < length(); i++) {
            if (!isMissing(i)) {
                value[cnt++] = operator.applyAsDouble(getValue(i));
            }
        }
        return new CompressedDoubleArray(value, bitmap, prefix, length());
    }

    @Override
    public DoubleArray zip(DoubleArray other, DoubleBinaryOperator operator) {
        if (length() != other.length()) {
            throw new IllegalArgumentException("DoubleArray must have the same length");
        }
        double[] temp = new double[Math.min(size(), other.size())];
        long[] bitmap = new long[(length() + 63) >>> 6];
        int cnt = 0;
        for (int i = 0; i < length(); i++) {
            if (!isMissing(i) && !other.isMissing(i)) {
                bitmap[i >>> 6] |= 1L << (i & 63);
                temp[cnt++] = operator.applyAsDouble(getValue(i), other.getValue(i));
            }
        }
        double[] values = Arrays.copyOf(temp, cnt);
        int words = bitmap.length;
        int[] prefix = new int[words + 1];
        for (int w = 0; w < words; w++) {
            prefix[w + 1] = prefix[w] + Long.bitCount(bitmap[w]);
        }
        return new CompressedDoubleArray(values, bitmap, prefix, length());
    }

}
