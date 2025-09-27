package com.github.jiangwangyang.math;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleArrayTest {

    @Test
    public void testFromArrayAndMissing() {
        Double[] array = IntStream.range(0, 1000)
                .mapToObj(i -> {
                    if (ThreadLocalRandom.current().nextInt(100) < 50) {
                        return null;
                    }
                    return Double.valueOf(i);
                })
                .toArray(Double[]::new);
        double[] valueArray = new double[array.length];
        boolean[] missingArray = new boolean[array.length];
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                valueArray[i] = array[i];
                missingArray[i] = false;
                size++;
            } else {
                missingArray[i] = true;
            }
        }
        DoubleArray doubleArray = DoubleArray.fromValueAndMissing(valueArray, missingArray);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                assertThat(doubleArray.get(i)).isEqualTo(array[i]);
                assertThat(doubleArray.getValue(i, 0.0)).isEqualTo(array[i]);
                assertThat(doubleArray.isMissing(i)).isFalse();
            } else {
                assertThat(doubleArray.get(i)).isNull();
                assertThat(doubleArray.getValue(i, 0.0)).isEqualTo(0.0);
                assertThat(doubleArray.isMissing(i)).isTrue();
            }
        }
        assertThat(doubleArray.size()).isEqualTo(size);
        assertThat(doubleArray.length()).isEqualTo(array.length);
    }

    @Test
    public void testFromArray() {
        Double[] array = IntStream.range(0, 1000)
                .mapToObj(i -> {
                    if (ThreadLocalRandom.current().nextInt(100) < 50) {
                        return null;
                    }
                    return Double.valueOf(i);
                })
                .toArray(Double[]::new);
        int size = 0;
        for (Double v : array) {
            if (v != null) {
                size++;
            }
        }
        DoubleArray doubleArray = DoubleArray.fromArray(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                assertThat(doubleArray.get(i)).isEqualTo(array[i]);
                assertThat(doubleArray.getValue(i, 0.0)).isEqualTo(array[i]);
                assertThat(doubleArray.isMissing(i)).isFalse();
            } else {
                assertThat(doubleArray.get(i)).isNull();
                assertThat(doubleArray.getValue(i, 0.0)).isEqualTo(0.0);
                assertThat(doubleArray.isMissing(i)).isTrue();
            }
        }
        assertThat(doubleArray.size()).isEqualTo(size);
        assertThat(doubleArray.length()).isEqualTo(array.length);
    }

}
