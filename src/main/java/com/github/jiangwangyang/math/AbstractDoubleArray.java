package com.github.jiangwangyang.math;

public abstract class AbstractDoubleArray implements DoubleArray {

    private final double[] values;
    private final long[] bitmap;
    private final int[] prefix;
    private final int length;

    protected AbstractDoubleArray(double[] values, long[] bitmap, int[] prefix, int length) {
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

}
