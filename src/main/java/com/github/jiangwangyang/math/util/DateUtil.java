package com.github.jiangwangyang.math.util;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DateUtil {

    public static List<LocalDate> rangeYear(Year startYear, Year endYear) {
        return IntStream.range(startYear.getValue(), endYear.getValue())
                .mapToObj(y -> LocalDate.of(y, 1, 1))
                .toList();
    }

    public static List<LocalDate> rangeQuarter(YearMonth startYearMonth, YearMonth endYearMonth) {
        if (startYearMonth.getMonthValue() % 3 != 1) {
            throw new UnsupportedOperationException("startYearMonth must be the first month of the quarter");
        }
        return Stream.iterate(startYearMonth, ym -> ym.plusMonths(3))
                .limit(startYearMonth.until(endYearMonth, ChronoUnit.MONTHS) / 3)
                .map(ym -> ym.atDay(1))
                .toList();
    }

    public static List<LocalDate> rangeMonth(YearMonth startYearMonth, YearMonth endYearMonth) {
        return Stream.iterate(startYearMonth, ym -> ym.plusMonths(1))
                .limit(startYearMonth.until(endYearMonth, ChronoUnit.MONTHS))
                .map(ym -> ym.atDay(1))
                .toList();
    }

    public static List<LocalDate> rangeDay(LocalDate startDate, LocalDate endDate) {
        return Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(startDate.until(endDate, ChronoUnit.DAYS))
                .toList();
    }

}
