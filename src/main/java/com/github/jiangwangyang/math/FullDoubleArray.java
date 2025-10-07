package com.github.jiangwangyang.math;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public final class FullDoubleArray implements DoubleArray {

    private final double[] value;

    FullDoubleArray(double[] value) {
        this.value = value;
    }

    @Override
    public Double get(int i) {
        return value[i];
    }

    @Override
    public double getValue(int i) {
        return value[i];
    }

    @Override
    public double getValueOrElse(int i, double defaultValue) {
        return value[i];
    }

    @Override
    public boolean isMissing(int i) {
        return false;
    }

    @Override
    public int size() {
        return value.length;
    }

    @Override
    public int length() {
        return value.length;
    }

    @Override
    public FullDoubleArray missingTo(double value) {
        return this;
    }

    @Override
    public FullDoubleArray map(DoubleUnaryOperator operator) {
        double[] value = new double[length()];
        for (int i = 0; i < length(); i++) {
            value[i] = operator.applyAsDouble(getValue(i));
        }
        return new FullDoubleArray(value);
    }

    @Override
    public DoubleArray zip(DoubleArray other, DoubleBinaryOperator operator) {
        if (length() != other.length()) {
            throw new IllegalArgumentException("DoubleArray must have the same length");
        }
        if (!(other instanceof FullDoubleArray)) {
            return other.zip(this, operator);
        }
        double[] value = new double[length()];
        for (int i = 0; i < length(); i++) {
            value[i] = operator.applyAsDouble(getValue(i), other.getValue(i));
        }
        return new FullDoubleArray(value);
    }
}
