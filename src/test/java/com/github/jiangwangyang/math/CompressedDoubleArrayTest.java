package com.github.jiangwangyang.math;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.jiangwangyang.math.BaseTest.randomArray;
import static com.github.jiangwangyang.math.BaseTest.testValue;
import static org.assertj.core.api.Assertions.assertThat;

public class CompressedDoubleArrayTest {

    @Test
    public void testFromArray() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = DoubleArray.compressFromArray(array);
        testValue(doubleArray, array);
        assertThat(doubleArray).isInstanceOf(CompressedDoubleArray.class);
    }

    @Test
    public void testMissingTo() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = DoubleArray.compressFromArray(array).missingTo(0);
        testValue(doubleArray, Arrays.stream(array).map(x -> x == null ? 0 : x).toArray(Double[]::new));
        assertThat(doubleArray).isInstanceOf(FullDoubleArray.class);
    }

    @Test
    public void testMap() {
        Double[] array = randomArray(1000, 50);
        DoubleArray doubleArray = DoubleArray.compressFromArray(array).map(x -> x + 1);
        testValue(doubleArray, Arrays.stream(array).map(x -> x == null ? null : x + 1).toArray(Double[]::new));
        assertThat(doubleArray).isInstanceOf(CompressedDoubleArray.class);
    }

    @Test
    public void testZip() {
        Double[] array1 = randomArray(1000, 50);
        Double[] array2 = randomArray(1000, 50);
        DoubleArray a = DoubleArray.compressFromArray(array1);
        DoubleArray b = DoubleArray.compressFromArray(array2);
        DoubleArray c = a.zip(b, Double::sum);
        DoubleArray d = b.zip(a, Double::sum);
        testValue(c, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        testValue(d, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        assertThat(c).isInstanceOf(CompressedDoubleArray.class);
        assertThat(d).isInstanceOf(CompressedDoubleArray.class);
    }

}
