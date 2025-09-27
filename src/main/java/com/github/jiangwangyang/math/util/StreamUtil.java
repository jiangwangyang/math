package com.github.jiangwangyang.math.util;

import com.google.common.collect.Streams;

import java.util.stream.Stream;

public class StreamUtil {

    public static Stream<Double> setMissingTo(Stream<Double> stream, double defaultValue) {
        return stream.map(a -> a == null ? defaultValue : a);
    }

    public static Stream<Double> add(Stream<Double> stream, double b) {
        return stream.map(a -> a == null ? null : a + b);
    }

    public static Stream<Double> subtract(Stream<Double> stream, double b) {
        return stream.map(a -> a == null ? null : a - b);
    }

    public static Stream<Double> multiply(Stream<Double> stream, double b) {
        return stream.map(a -> a == null ? null : a * b);
    }

    public static Stream<Double> divide(Stream<Double> stream, double b) {
        return stream.map(a -> a == null ? null : a / b);
    }

    public static Stream<Double> power(Stream<Double> stream, double b) {
        return stream.map(a -> a == null ? null : Math.pow(a, b));
    }

    public static Stream<Double> add(Stream<Double> s1, Stream<Double> s2) {
        return Streams.zip(s1, s2, (a, b) -> a == null || b == null ? null : a + b);
    }

    public static Stream<Double> subtract(Stream<Double> s1, Stream<Double> s2) {
        return Streams.zip(s1, s2, (a, b) -> a == null || b == null ? null : a - b);
    }

    public static Stream<Double> multiply(Stream<Double> s1, Stream<Double> s2) {
        return Streams.zip(s1, s2, (a, b) -> a == null || b == null ? null : a * b);
    }

    public static Stream<Double> divide(Stream<Double> s1, Stream<Double> s2) {
        return Streams.zip(s1, s2, (a, b) -> a == null || b == null ? null : a / b);
    }

    public static Stream<Double> pow(Stream<Double> s1, Stream<Double> s2) {
        return Streams.zip(s1, s2, (a, b) -> a == null || b == null ? null : Math.pow(a, b));
    }

}
