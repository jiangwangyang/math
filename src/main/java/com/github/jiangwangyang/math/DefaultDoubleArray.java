package com.github.jiangwangyang.math;

import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public final class DefaultDoubleArray extends AbstractDoubleArray {

    private DefaultDoubleArray(double[] values, long[] bitmap, int[] prefix, int length) {
        super(values, bitmap, prefix, length);
    }

    public static DefaultDoubleArray fromValueMissing(double[] value, boolean[] missing) {
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
        return new DefaultDoubleArray(values, bitmap, prefix, length);
    }

    public static DefaultDoubleArray fromList(List<Double> list) {
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
        return new DefaultDoubleArray(values, bitmap, prefix, list.size());
    }

    public static DefaultDoubleArray fromArray(Double[] array) {
        return fromList(Arrays.asList(array));
    }

    public static DefaultDoubleArray fromDoubles(Double... doubles) {
        return fromList(Arrays.asList(doubles));
    }

    public static DefaultDoubleArray fromArray(double[] array) {
        return fromValueMissing(array, new boolean[array.length]);
    }

    public static DefaultDoubleArray fromValues(double... values) {
        return fromValueMissing(values, new boolean[values.length]);
    }

    @Override
    public DefaultDoubleArray missingTo(double value) {
        if (size() == length()) {
            return this;
        }
        double[] newValueArray = new double[length()];
        for (int i = 0; i < length(); i++) {
            newValueArray[i] = getValueOrElse(i, value);
        }
        return DefaultDoubleArray.fromValueMissing(newValueArray, new boolean[length()]);
    }

    @Override
    public DefaultDoubleArray map(DoubleUnaryOperator operator) {
        double[] value = new double[length()];
        boolean[] missing = new boolean[length()];
        for (int i = 0; i < length(); i++) {
            if (isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = operator.applyAsDouble(getValue(i));
            }
        }
        return DefaultDoubleArray.fromValueMissing(value, missing);
    }

    @Override
    public DefaultDoubleArray zip(DoubleArray other, DoubleBinaryOperator operator) {
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
        return DefaultDoubleArray.fromValueMissing(value, missing);
    }

}
