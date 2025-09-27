package com.github.jiangwangyang.math.util;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {

    @Test
    void testRangeYear() {
        List<LocalDate> yearList = DateUtil.rangeYear(Year.of(2000), Year.of(2010));
        assertThat(yearList.size()).isEqualTo(10);
        assertThat(LocalDate.of(2000, 1, 1)).isEqualTo(yearList.get(0));
        assertThat(LocalDate.of(2009, 1, 1)).isEqualTo(yearList.get(9));
    }

    @Test
    void testRangeQuarter() {
        List<LocalDate> quarterList = DateUtil.rangeQuarter(YearMonth.of(2000, 1), YearMonth.of(2001, 1));
        assertThat(quarterList.size()).isEqualTo(4);
        assertThat(LocalDate.of(2000, 1, 1)).isEqualTo(quarterList.get(0));
        assertThat(LocalDate.of(2000, 10, 1)).isEqualTo(quarterList.get(3));
    }

    @Test
    void testRangeMonth() {
        List<LocalDate> monthList = DateUtil.rangeMonth(YearMonth.of(2000, 1), YearMonth.of(2001, 1));
        assertThat(monthList.size()).isEqualTo(12);
        assertThat(LocalDate.of(2000, 1, 1)).isEqualTo(monthList.get(0));
        assertThat(LocalDate.of(2000, 12, 1)).isEqualTo(monthList.get(11));
    }

    @Test
    void testRangeDay() {
        List<LocalDate> dateList = DateUtil.rangeDay(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 2, 1));
        assertThat(dateList.size()).isEqualTo(31);
        assertThat(LocalDate.of(2000, 1, 1)).isEqualTo(dateList.get(0));
        assertThat(LocalDate.of(2000, 1, 31)).isEqualTo(dateList.get(30));
    }

}
