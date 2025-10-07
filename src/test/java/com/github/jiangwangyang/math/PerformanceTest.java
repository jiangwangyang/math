package com.github.jiangwangyang.math;

import static com.github.jiangwangyang.math.BaseTest.randomArray;

public class PerformanceTest {

    private static void testArray(int count, int length, int rate) {
        Double[] array = randomArray(length, rate);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Double[] newArray = new Double[length];
            for (int j = 0; j < length; j++) {
                if (array[j] != null) {
                    newArray[j] = array[j] * 1.1;
                }
            }
            array = newArray;
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test Array [%d %d %d]: %dms\n", count, length, rate, end - start);
    }

    private static void testCompressedDoubleArray(int count, int length, int rate) {
        DoubleArray doubleArray = DoubleArray.compressFromArray(randomArray(length, rate));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doubleArray = doubleArray.map(x -> x * 1.1);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test CompressedDoubleArray [%d %d %d]: %dms\n", count, length, rate, end - start);
    }

    private static void testDefaultDoubleArray(int count, int length, int rate) {
        DoubleArray doubleArray = DoubleArray.fromArray(randomArray(length, rate));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doubleArray = doubleArray.map(x -> x * 1.1);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test DefaultDoubleArray [%d %d %d]: %dms\n", count, length, rate, end - start);
    }

    private static void testFullDoubleArray(int count, int length) {
        DoubleArray doubleArray = DoubleArray.fromArray(randomArray(length, 100));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doubleArray = doubleArray.map(x -> x * 1.1);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test FullDoubleArray [%d %d %d]: %dms\n", count, length, 100, end - start);
    }

    private static void test(int count, int length, int rate) {
        System.out.println("======================================");
        testArray(count, length, rate);
        testCompressedDoubleArray(count, length, rate);
        testDefaultDoubleArray(count, length, rate);
        testFullDoubleArray(count, length);
    }

    public static void main(String[] args) {
        test(100, 1000_000, 90);
        test(10_000, 10_000, 90);
        test(1000_000, 100, 90);
    }

}
