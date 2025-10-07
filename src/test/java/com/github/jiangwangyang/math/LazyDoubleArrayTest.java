package com.github.jiangwangyang.math;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jiangwangyang.math.BaseTest.randomArray;
import static com.github.jiangwangyang.math.BaseTest.testValue;
import static org.assertj.core.api.Assertions.assertThat;

public class LazyDoubleArrayTest {

    @Test
    public void testFromArray() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = LazyDoubleArray.of(DoubleArray.fromArray(array));
        testValue(doubleArray, array);
        assertThat(doubleArray).isInstanceOf(LazyDoubleArray.class);
    }

    @Test
    public void testMissingTo() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = LazyDoubleArray.of(DoubleArray.fromArray(array)).missingTo(0);
        testValue(doubleArray, Arrays.stream(array).map(x -> x == null ? 0 : x).toArray(Double[]::new));
        assertThat(doubleArray).isInstanceOf(LazyDoubleArray.class);
    }

    @Test
    public void testMap() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = LazyDoubleArray.of(DoubleArray.fromArray(array)).map(x -> x + 1);
        testValue(doubleArray, Arrays.stream(array).map(x -> x == null ? null : x + 1).toArray(Double[]::new));
        assertThat(doubleArray).isInstanceOf(LazyDoubleArray.class);
    }

    @Test
    public void testZip() {
        Double[] array1 = randomArray(1000, 50);
        Double[] array2 = randomArray(1000, 50);
        DoubleArray a = LazyDoubleArray.of(DoubleArray.fromArray(array1));
        DoubleArray b = LazyDoubleArray.of(DoubleArray.fromArray(array2));
        DoubleArray c = a.zip(b, Double::sum);
        DoubleArray d = b.zip(a, Double::sum);
        testValue(c, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        testValue(d, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        assertThat(c).isInstanceOf(LazyDoubleArray.class);
        assertThat(d).isInstanceOf(LazyDoubleArray.class);
    }

    @Test
    public void testLazy() {
        AtomicInteger count = new AtomicInteger(0);
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = LazyDoubleArray.of(DoubleArray.fromArray(array)).map(x -> {
            count.incrementAndGet();
            return x + 1.0;
        });
        assertThat(count.get()).isEqualTo(0);
        doubleArray.get(0);
        assertThat(count.get()).isEqualTo(doubleArray.size());
    }

}
