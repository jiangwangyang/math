package com.github.jiangwangyang.math;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface DoubleArray extends Iterable<Double> {

    Double get(int i);

    double getValue(int i);

    double getValueOrElse(int i, double defaultValue);

    boolean isMissing(int i);

    int size();

    int length();

    @Override
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
