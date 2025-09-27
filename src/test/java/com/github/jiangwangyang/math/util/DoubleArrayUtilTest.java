package com.github.jiangwangyang.math.util;

import com.github.jiangwangyang.math.DoubleArray;
import com.github.jiangwangyang.math.DoubleArrayTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleArrayUtilTest {

    @Test
    void testSetMissingTo() {
        Double[] array = DoubleArrayTest.randomArray(1000, 50);
        DoubleArray a = DoubleArray.fromArray(array);
        DoubleArray r = DoubleArrayUtil.setMissingTo(a, 0);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                assertThat(r.get(i)).isEqualTo(0.0);
            } else {
                assertThat(r.get(i)).isEqualTo(array[i]);
            }
        }
    }

    @Test
    void testApply() {
        Double[] array = DoubleArrayTest.randomArray(1000, 50);
        DoubleArray a = DoubleArray.fromArray(array);
        DoubleArray r = DoubleArrayUtil.apply(Double::sum, a, 1.0);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                assertThat(r.get(i)).isNull();
            } else {
                assertThat(r.get(i)).isEqualTo(array[i] + 1.0);
            }
        }
    }

    @Test
    void testApply2() {
        Double[] array1 = DoubleArrayTest.randomArray(1000, 50);
        Double[] array2 = DoubleArrayTest.randomArray(1000, 50);
        DoubleArray a = DoubleArray.fromArray(array1);
        DoubleArray b = DoubleArray.fromArray(array2);
        DoubleArray r = DoubleArrayUtil.apply(Double::sum, a, b);
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] == null || array2[i] == null) {
                assertThat(r.get(i)).isNull();
            } else {
                assertThat(r.get(i)).isEqualTo(array1[i] + array2[i]);
            }
        }
    }

}
