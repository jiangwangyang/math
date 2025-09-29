package com.github.jiangwangyang.math.util;

import com.github.jiangwangyang.math.DoubleArray;
import com.github.jiangwangyang.math.DoubleArrayTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleArrayAsyncUtilTest {

    @Test
    void testSetMissingTo() {
        Double[] a = DoubleArrayTest.randomArray(1000, 50);
        DoubleArray r = DoubleArrayAsyncUtil.setMissingTo(() -> DoubleArray.fromArray(a), 0.0).get();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) {
                assertThat(r.get(i)).isEqualTo(0.0);
            } else {
                assertThat(r.get(i)).isEqualTo(a[i]);
            }
        }
    }

    @Test
    void testApply() {
        Double[] a = DoubleArrayTest.randomArray(1000, 50);
        DoubleArray r = DoubleArrayAsyncUtil.apply(x -> x + 1.0, () -> DoubleArray.fromArray(a)).get();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) {
                assertThat(r.get(i)).isNull();
            } else {
                assertThat(r.get(i)).isEqualTo(a[i] + 1.0);
            }
        }
    }

    @Test
    void testApply2() {
        Double[] a = DoubleArrayTest.randomArray(1000, 50);
        Double[] b = DoubleArrayTest.randomArray(1000, 50);
        DoubleArray r = DoubleArrayAsyncUtil.apply(Double::sum, () -> DoubleArray.fromArray(a), () -> DoubleArray.fromArray(b)).get();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null || b[i] == null) {
                assertThat(r.get(i)).isNull();
            } else {
                assertThat(r.get(i)).isEqualTo(a[i] + b[i]);
            }
        }
    }

}
