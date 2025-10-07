package com.github.jiangwangyang.math;


import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class PerformanceTest {

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
        DoubleArray doubleArray = CompressedDoubleArray.fromArray(randomArray(length, rate));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doubleArray = doubleArray.map(x -> x * 1.1);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test CompressedDoubleArray [%d %d %d]: %dms\n", count, length, rate, end - start);
    }

    private static void testDefaultDoubleArray(int count, int length, int rate) {
        DoubleArray doubleArray = DefaultDoubleArray.fromArray(randomArray(length, rate));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doubleArray = doubleArray.map(x -> x * 1.1);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test DefaultDoubleArray [%d %d %d]: %dms\n", count, length, rate, end - start);
    }

    private static void testFullDoubleArray(int count, int length) {
        DoubleArray doubleArray = DefaultDoubleArray.fromArray(randomArray(length, 100)).missingTo(0.0);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doubleArray = doubleArray.map(x -> x * 1.1);
        }
        long end = System.currentTimeMillis();
        System.out.printf("Test FullDoubleArray [%d %d %d]: %dms\n", count, length, 100, end - start);
    }

    private static void test(int count, int length, int rate) {
        testArray(count, length, rate);
        testCompressedDoubleArray(count, length, rate);
        testDefaultDoubleArray(count, length, rate);
        if (rate == 100) {
            testFullDoubleArray(count, length);
        }
    }

    public static void main(String[] args) {
        test(100, 1000_000, 100);
        test(10_000, 10_000, 100);
        test(1000_000, 100, 100);
    }

}
