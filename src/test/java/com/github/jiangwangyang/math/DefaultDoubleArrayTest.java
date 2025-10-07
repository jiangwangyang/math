package com.github.jiangwangyang.math;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultDoubleArrayTest {

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
    public void testFromValueMissing() {
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
        DoubleArray doubleArray = DefaultDoubleArray.fromValueMissing(valueArray, missingArray);
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
        assertThat(doubleArray.size()).isEqualTo(size);
        assertThat(doubleArray.length()).isEqualTo(array.length);
    }

    @Test
    public void testFromArray() {
        Double[] array = randomArray(1000, 50);
        int size = Arrays.stream(array).filter(Objects::nonNull).toArray().length;
        DoubleArray doubleArray = DefaultDoubleArray.fromArray(array);
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
        assertThat(doubleArray.size()).isEqualTo(size);
        assertThat(doubleArray.length()).isEqualTo(array.length);
    }

    @Test
    public void testMissingTo() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = DefaultDoubleArray.fromArray(array).missingTo(0);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                assertThat(doubleArray.get(i)).isEqualTo(0.0);
            } else {
                assertThat(doubleArray.get(i)).isEqualTo(array[i]);
            }
        }
    }

    @Test
    public void testMap() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = DefaultDoubleArray.fromArray(array).map(x -> x + 1.0);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                assertThat(doubleArray.get(i)).isNull();
            } else {
                assertThat(doubleArray.get(i)).isEqualTo(array[i] + 1.0);
            }
        }
    }

    @Test
    public void testZip() {
        Double[] array1 = randomArray(1000, 50);
        Double[] array2 = randomArray(1000, 50);
        DoubleArray a = DefaultDoubleArray.fromArray(array1);
        DoubleArray b = DefaultDoubleArray.fromArray(array2);
        DoubleArray doubleArray = a.zip(b, Double::sum);
        for (int i = 0; i < 1000; i++) {
            if (array1[i] == null || array2[i] == null) {
                assertThat(doubleArray.get(i)).isNull();
            } else {
                assertThat(doubleArray.get(i)).isEqualTo(array1[i] + array2[i]);
            }
        }
    }

}
