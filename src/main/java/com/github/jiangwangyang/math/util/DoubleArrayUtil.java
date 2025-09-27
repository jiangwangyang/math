package com.github.jiangwangyang.math.util;

import com.github.jiangwangyang.math.DoubleArray;

public class DoubleArrayUtil {

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

    public static DoubleArray add(DoubleArray a, double b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) + b;
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray subtract(DoubleArray a, double b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) - b;
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray multiply(DoubleArray a, double b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) * b;
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray divide(DoubleArray a, double b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) / b;
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray pow(DoubleArray a, double b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = Math.pow(a.getValue(i, Double.NaN), b);
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray add(DoubleArray a, DoubleArray b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i) || b.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) + b.getValue(i, Double.NaN);
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray subtract(DoubleArray a, DoubleArray b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i) || b.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) - b.getValue(i, Double.NaN);
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray multiply(DoubleArray a, DoubleArray b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i) || b.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) * b.getValue(i, Double.NaN);
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray divide(DoubleArray a, DoubleArray b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i) || b.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = a.getValue(i, Double.NaN) / b.getValue(i, Double.NaN);
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

    public static DoubleArray pow(DoubleArray a, DoubleArray b) {
        double[] value = new double[a.length()];
        boolean[] missing = new boolean[a.length()];
        for (int i = 0; i < a.length(); i++) {
            if (a.isMissing(i) || b.isMissing(i)) {
                missing[i] = true;
            } else {
                value[i] = Math.pow(a.getValue(i, Double.NaN), b.getValue(i, Double.NaN));
            }
        }
        return DoubleArray.fromValueAndMissing(value, missing);
    }

}
