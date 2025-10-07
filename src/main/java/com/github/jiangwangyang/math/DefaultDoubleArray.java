package com.github.jiangwangyang.math;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public final class DefaultDoubleArray implements DoubleArray {

    private final double[] value;
    private final boolean[] missing;
    private Integer size;

    DefaultDoubleArray(double[] value, boolean[] missing) {
        this.value = value;
        this.missing = missing;
    }

    @Override
    public Double get(int i) {
        return missing[i] ? null : value[i];
    }

    @Override
    public double getValue(int i) {
        if (missing[i]) {
            throw new IllegalArgumentException("index " + i + " is missing");
        }
        return value[i];
    }

    @Override
    public double getValueOrElse(int i, double defaultValue) {
        return missing[i] ? defaultValue : value[i];
    }

    @Override
    public boolean isMissing(int i) {
        return missing[i];
    }

    @Override
    public int size() {
        if (size != null) {
            return size;
        }
        synchronized (this) {
            if (size != null) {
                return size;
            }
            int sizeValue = 0;
            for (int i = 0; i < value.length; i++) {
                if (!missing[i]) {
                    sizeValue++;
                }
            }
            size = sizeValue;
            return size;
        }
    }

    @Override
    public int length() {
        return value.length;
    }

    @Override
    public FullDoubleArray missingTo(double value) {
        double[] newValueArray = new double[length()];
        for (int i = 0; i < length(); i++) {
            newValueArray[i] = getValueOrElse(i, value);
        }
        return new FullDoubleArray(newValueArray);
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
        return new DefaultDoubleArray(value, missing);
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
        return new DefaultDoubleArray(value, missing);
    }
}
