package com.github.jiangwangyang.math;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleArrayTest {

    public static Double[] randomArray(int length, int rate) {
        return IntStream.range(0, length)
                .mapToObj(i -> {
                    if (ThreadLocalRandom.current().nextInt(100) < rate) {
                        return null;
                    }
                    return Double.valueOf(i);
                })
                .toArray(Double[]::new);
    }

    @Test
    public void testFromValueAndMissing() {
        Double[] array = randomArray(1000, 50);
        double[] valueArray = new double[array.length];
        boolean[] missingArray = new boolean[array.length];
        int size = Arrays.stream(array).filter(Objects::nonNull).toArray().length;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                valueArray[i] = array[i];
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
        Double[] array = randomArray(1000, 50);
        int size = Arrays.stream(array).filter(Objects::nonNull).toArray().length;
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
