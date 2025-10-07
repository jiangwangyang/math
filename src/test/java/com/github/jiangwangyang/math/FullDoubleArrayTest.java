package com.github.jiangwangyang.math;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.jiangwangyang.math.BaseTest.randomArray;
import static com.github.jiangwangyang.math.BaseTest.testValue;
import static org.assertj.core.api.Assertions.assertThat;

public class FullDoubleArrayTest {

    @Test
    public void testFromArray() {
        Double[] array = randomArray(1000, 100);
        DoubleArray doubleArray = DoubleArray.fromValues(Arrays.stream(array).mapToDouble(Double::doubleValue).toArray());
        testValue(doubleArray, array);
        assertThat(doubleArray).isInstanceOf(FullDoubleArray.class);
    }

    @Test
    public void testMissingTo() {
        Double[] array = randomArray(1000, 100);
        DoubleArray doubleArray = DoubleArray.fromValues(Arrays.stream(array).mapToDouble(Double::doubleValue).toArray()).missingTo(0);
        testValue(doubleArray, array);
        assertThat(doubleArray).isInstanceOf(FullDoubleArray.class);
    }

    @Test
    public void testMap() {
        Double[] array = randomArray(1000, 100);
        DoubleArray doubleArray = DoubleArray.fromValues(Arrays.stream(array).mapToDouble(Double::doubleValue).toArray()).map(x -> x + 1);
        testValue(doubleArray, Arrays.stream(array).map(x -> x + 1).toArray(Double[]::new));
        assertThat(doubleArray).isInstanceOf(FullDoubleArray.class);
    }

    @Test
    public void testZip() {
        Double[] array1 = randomArray(1000, 100);
        Double[] array2 = randomArray(1000, 100);
        DoubleArray a = DoubleArray.fromValues(Arrays.stream(array1).mapToDouble(Double::doubleValue).toArray());
        DoubleArray b = DoubleArray.fromValues(Arrays.stream(array2).mapToDouble(Double::doubleValue).toArray());
        DoubleArray c = a.zip(b, Double::sum);
        DoubleArray d = b.zip(a, Double::sum);
        testValue(c, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), Double::sum).toArray(Double[]::new));
        testValue(d, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), Double::sum).toArray(Double[]::new));
        assertThat(c).isInstanceOf(FullDoubleArray.class);
        assertThat(d).isInstanceOf(FullDoubleArray.class);
    }

    @Test
    public void testZipWithDefault() {
        Double[] array1 = randomArray(1000, 100);
        Double[] array2 = randomArray(1000, 50);
        DoubleArray a = DoubleArray.fromValues(Arrays.stream(array1).mapToDouble(Double::doubleValue).toArray());
        DoubleArray b = DoubleArray.fromArray(array2);
        DoubleArray c = a.zip(b, Double::sum);
        DoubleArray d = b.zip(a, Double::sum);
        testValue(c, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        testValue(d, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        assertThat(c).isInstanceOf(DefaultDoubleArray.class);
        assertThat(d).isInstanceOf(DefaultDoubleArray.class);
    }

    @Test
    public void testZipWithCompressed() {
        Double[] array1 = randomArray(1000, 100);
        Double[] array2 = randomArray(1000, 50);
        DoubleArray a = DoubleArray.fromValues(Arrays.stream(array1).mapToDouble(Double::doubleValue).toArray());
        DoubleArray b = DoubleArray.compressFromArray(array2);
        DoubleArray c = a.zip(b, Double::sum);
        DoubleArray d = b.zip(a, Double::sum);
        testValue(c, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        testValue(d, Streams.zip(Arrays.stream(array1), Arrays.stream(array2), (x, y) -> x == null || y == null ? null : x + y).toArray(Double[]::new));
        assertThat(c).isInstanceOf(CompressedDoubleArray.class);
        assertThat(d).isInstanceOf(CompressedDoubleArray.class);
    }

}
