package com.github.jiangwangyang.math.util;

import com.github.jiangwangyang.math.DoubleArray;

import java.util.function.DoubleBinaryOperator;

public class DoubleArrayUtil {

    private DoubleArrayUtil() {
    }

    public static DoubleArray setMissingTo(DoubleArray a, double defaultValue) {
        if (a.size() == a.length()) {
            return a;
        }
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            value[i] = a.getValue(i, defaultValue);
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray apply(DoubleBinaryOperator operator, DoubleArray a, double b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = operator.applyAsDouble(a.getValue(i, Double.NaN), b);
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray apply(DoubleBinaryOperator operator, DoubleArray a, DoubleArray b) {
        if (a.length() != b.length()) {
            throw new IllegalArgumentException("a and b must have the same length");
        }
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i) || b.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = operator.applyAsDouble(a.getValue(i, Double.NaN), b.getValue(i, Double.NaN));
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

}
