package com.github.jiangwangyang.math.util;

import com.github.jiangwangyang.math.DoubleArray;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class DoubleArrayAsyncUtil {

    public static Future<DoubleArray> setMissingTo(Future<DoubleArray> a, double defaultValue) {
        return new FutureTask<>(() -> DoubleArrayUtil.setMissingTo(a.get(), defaultValue));
    }

    public static Future<DoubleArray> add(Future<DoubleArray> a, double b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.add(a.get(), b));
    }

    public static Future<DoubleArray> subtract(Future<DoubleArray> a, double b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.subtract(a.get(), b));
    }

    public static Future<DoubleArray> multiply(Future<DoubleArray> a, double b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.multiply(a.get(), b));
    }

    public static Future<DoubleArray> divide(Future<DoubleArray> a, double b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.divide(a.get(), b));
    }

    public static Future<DoubleArray> pow(Future<DoubleArray> a, double b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.pow(a.get(), b));
    }

    public static Future<DoubleArray> add(Future<DoubleArray> a, Future<DoubleArray> b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.add(a.get(), b.get()));
    }

    public static Future<DoubleArray> subtract(Future<DoubleArray> a, Future<DoubleArray> b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.subtract(a.get(), b.get()));
    }

    public static Future<DoubleArray> multiply(Future<DoubleArray> a, Future<DoubleArray> b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.multiply(a.get(), b.get()));
    }

    public static Future<DoubleArray> divide(Future<DoubleArray> a, Future<DoubleArray> b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.divide(a.get(), b.get()));
    }

    public static Future<DoubleArray> pow(Future<DoubleArray> a, Future<DoubleArray> b, int length) {
        return new FutureTask<>(() -> DoubleArrayUtil.pow(a.get(), b.get()));
    }

}
