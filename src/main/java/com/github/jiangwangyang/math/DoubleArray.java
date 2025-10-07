package com.github.jiangwangyang.math;

import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface DoubleArray extends Iterable<Double> {

    static DoubleArray fromValues(double... values) {
        return new FullDoubleArray(values);
    }

    static DoubleArray fromList(List<Double> list) {
        boolean full = true;
        double[] value = new double[list.size()];
        boolean[] missing = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                value[i] = list.get(i);
            } else {
                missing[i] = true;
                full = false;
            }
        }
        if (full) {
            return new FullDoubleArray(value);
        }
        return new DefaultDoubleArray(value, missing);
    }

    static DoubleArray fromArray(Double... array) {
        return fromList(Arrays.asList(array));
    }

    static DoubleArray compressFromList(List<Double> list) {
        boolean full = true;
        double[] temp = new double[list.size()];
        long[] bitmap = new long[(list.size() + 63) >>> 6];
        int cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            Double v = list.get(i);
            if (v != null) {
                bitmap[i >>> 6] |= 1L << (i & 63);
                temp[cnt++] = v;
            } else {
                full = false;
            }
        }
        if (full) {
            return new FullDoubleArray(temp);
        }
        double[] values = Arrays.copyOf(temp, cnt);
        int words = bitmap.length;
        int[] prefix = new int[words + 1];
        for (int w = 0; w < words; w++) {
            prefix[w + 1] = prefix[w] + Long.bitCount(bitmap[w]);
        }
        return new CompressedDoubleArray(values, bitmap, prefix, list.size());
    }

    static DoubleArray compressFromArray(Double... array) {
        return compressFromList(Arrays.asList(array));
    }

    Double get(int i);

    double getValue(int i);

    double getValueOrElse(int i, double defaultValue);

    boolean isMissing(int i);

    int size();

    int length();

    @Override
    @NonNull
    default Iterator<Double> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < length();
            }

            @Override
            public Double next() {
                return get(i++);
            }
        };
    }

    @Override
    default Spliterator<Double> spliterator() {
        return Spliterators.spliterator(this.iterator(), length(), 0);
    }

    default Stream<Double> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<Double> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    DoubleArray missingTo(double value);

    DoubleArray map(DoubleUnaryOperator operator);

    DoubleArray zip(DoubleArray other, DoubleBinaryOperator operator);

    default DoubleArray add(DoubleArray other) {
        return zip(other, Double::sum);
    }

    default DoubleArray subtract(DoubleArray other) {
        return zip(other, (a, b) -> a - b);
    }

    default DoubleArray multiply(DoubleArray other) {
        return zip(other, (a, b) -> a * b);
    }

    default DoubleArray divide(DoubleArray other) {
        return zip(other, (a, b) -> a / b);
    }

    default DoubleArray mod(DoubleArray other) {
        return zip(other, (a, b) -> a % b);
    }

    default DoubleArray pow(DoubleArray other) {
        return zip(other, Math::pow);
    }

    default DoubleArray root(DoubleArray other) {
        return zip(other, (a, b) -> Math.pow(a, 1 / b));
    }

    default DoubleArray log(DoubleArray other) {
        return zip(other, (a, b) -> Math.log(a) / Math.log(b));
    }

}
