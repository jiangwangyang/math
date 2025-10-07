package com.github.jiangwangyang.math;

import com.google.common.base.Suppliers;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;

public final class LazyDoubleArray implements DoubleArray {

    private final Supplier<DoubleArray> supplier;

    private LazyDoubleArray(Supplier<DoubleArray> supplier) {
        this.supplier = supplier;
    }

    public static LazyDoubleArray of(DoubleArray doubleArray) {
        return new LazyDoubleArray(() -> doubleArray);
    }

    @Override
    public Double get(int i) {
        return supplier.get().get(i);
    }

    @Override
    public double getValue(int i) {
        return supplier.get().getValue(i);
    }

    @Override
    public double getValueOrElse(int i, double defaultValue) {
        return supplier.get().getValueOrElse(i, defaultValue);
    }

    @Override
    public boolean isMissing(int i) {
        return supplier.get().isMissing(i);
    }

    @Override
    public int size() {
        return supplier.get().size();
    }

    @Override
    public int length() {
        return supplier.get().length();
    }

    @Override
    public LazyDoubleArray missingTo(double value) {
        return new LazyDoubleArray(Suppliers.memoize(() -> supplier.get().missingTo(value)));
    }

    @Override
    public LazyDoubleArray map(DoubleUnaryOperator operator) {
        return new LazyDoubleArray(Suppliers.memoize(() -> supplier.get().map(operator)));
    }

    @Override
    public LazyDoubleArray zip(DoubleArray other, DoubleBinaryOperator operator) {
        return new LazyDoubleArray(Suppliers.memoize(() -> supplier.get().zip(other, operator)));
    }
    
}
