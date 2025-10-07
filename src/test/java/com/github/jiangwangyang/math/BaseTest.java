package com.github.jiangwangyang.math;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseTest {

    public static Double[] randomArray(int length, int rate) {
        return IntStream.range(0, length)
                .mapToObj(i -> {
                    if (ThreadLocalRandom.current().nextInt(100) < rate) {
                        return ThreadLocalRandom.current().nextDouble();
                    }
                    return null;
                })
                .toArray(Double[]::new);
    }

    public static void testValue(DoubleArray doubleArray, Double[] array) {
        int size = Arrays.stream(array).filter(Objects::nonNull).toArray().length;
        assertThat(doubleArray.size()).isEqualTo(size);
        assertThat(doubleArray.length()).isEqualTo(array.length);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                assertThat(doubleArray.get(i)).isEqualTo(array[i]);
                assertThat(doubleArray.getValue(i)).isEqualTo(array[i]);
                assertThat(doubleArray.getValueOrElse(i, 0.0)).isEqualTo(array[i]);
                assertThat(doubleArray.isMissing(i)).isFalse();
            } else {
                assertThat(doubleArray.get(i)).isNull();
                assertThat(doubleArray.getValueOrElse(i, 0.0)).isEqualTo(0.0);
                assertThat(doubleArray.isMissing(i)).isTrue();
            }
        }
    }

}
