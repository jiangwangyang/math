package com.github.jiangwangyang.math;

import org.junit.jupiter.api.Test;

import static com.github.jiangwangyang.math.PerformanceTest.randomArray;
import static org.assertj.core.api.Assertions.assertThat;

public class FullDoubleArrayTest {

    @Test
    public void testMap() {
        Double[] array = randomArray(1000, 100);
        DoubleArray doubleArray = DefaultDoubleArray.fromArray(array).map(x -> x + 1.0);
        for (int i = 0; i < array.length; i++) {
            assertThat(doubleArray.get(i)).isEqualTo(array[i] + 1.0);
        }
    }

    @Test
    public void testZip() {
        Double[] array1 = randomArray(1000, 100);
        Double[] array2 = randomArray(1000, 100);
        DoubleArray a = DefaultDoubleArray.fromArray(array1);
        DoubleArray b = DefaultDoubleArray.fromArray(array2);
        DoubleArray doubleArray = a.zip(b, Double::sum);
        for (int i = 0; i < 1000; i++) {
            assertThat(doubleArray.get(i)).isEqualTo(array1[i] + array2[i]);
        }
    }

}
