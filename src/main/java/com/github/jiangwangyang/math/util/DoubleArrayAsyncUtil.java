package com.github.jiangwangyang.math.util;

import com.github.jiangwangyang.math.DoubleArray;
import com.google.common.base.Suppliers;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;

public class DoubleArrayAsyncUtil {

    private DoubleArrayAsyncUtil() {
    }

    public static Supplier<DoubleArray> setMissingTo(Supplier<DoubleArray> a, double defaultValue) {
        return Suppliers.memoize(() -> DoubleArrayUtil.setMissingTo(a.get(), defaultValue));
    }

    public static Supplier<DoubleArray> apply(DoubleBinaryOperator operator, Supplier<DoubleArray> a, double b) {
        return Suppliers.memoize(() -> DoubleArrayUtil.apply(operator, a.get(), b));
    }

    public static Supplier<DoubleArray> apply(DoubleBinaryOperator operator, Supplier<DoubleArray> a, Supplier<DoubleArray> b) {
        return Suppliers.memoize(() -> DoubleArrayUtil.apply(operator, a.get(), b.get()));
    }

}
