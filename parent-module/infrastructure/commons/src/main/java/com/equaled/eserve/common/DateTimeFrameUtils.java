package com.equaled.eserve.common;

import com.equaled.eserve.common.to.TimeFrame;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateTimeFrameUtils {

    public static Map<String,Long> findDateTimeFrame(TimeFrame timeFrame){
        Map<String,Long> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        YearMonth thisMonth = YearMonth.now();
        switch (timeFrame) {
            case TODAY:
                Instant todayFrom = LocalDateTime.of(localDate, LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant todayTo = LocalDateTime.of(localDate, LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",todayFrom.toEpochMilli());
                map.put("to",todayTo.toEpochMilli());
                return map;
            case YESTERDAY:
                localDate = LocalDate.now().minusDays(1);
                Instant yesterdayFrom = LocalDateTime.of(localDate,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant yesterdayTo = LocalDateTime.of(localDate,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",yesterdayFrom.toEpochMilli());
                map.put("to",yesterdayTo.toEpochMilli());
                return map;
            case THIS_WEEK:
                localDate = LocalDate.now();
                TemporalField weekStart = WeekFields.of(Locale.getDefault()).dayOfWeek();
                LocalDate startOfWeek = localDate.with(weekStart, 1);
                LocalDate endOfWeek = localDate.with(weekStart, 7);
                Instant thisWeekFrom = LocalDateTime.of(startOfWeek,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant thisWeekTo = LocalDateTime.of(endOfWeek,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",thisWeekFrom.toEpochMilli());
                map.put("to",thisWeekTo.toEpochMilli());
                return map;
            case LAST_WEEK:
                localDate = LocalDate.now();
                TemporalField weekStart1 = WeekFields.of(Locale.getDefault()).dayOfWeek();
                LocalDate endOfLastWeek = localDate.with(weekStart1, 1).minus(1, ChronoUnit.DAYS);
                LocalDate startOfLastWeek = localDate.with(weekStart1, 1).minus(7,ChronoUnit.DAYS);
                Instant lastWeekFrom = LocalDateTime.of(startOfLastWeek,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant lastWeekTo = LocalDateTime.of(endOfLastWeek,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",lastWeekFrom.toEpochMilli());
                map.put("to",lastWeekTo.toEpochMilli());
                return map;
            case THIS_MONTH:
                LocalDate startOfMonth = thisMonth.atDay(1);
                LocalDate endOfMonth = thisMonth.atEndOfMonth();
                Instant thisMonthFrom = LocalDateTime.of(startOfMonth,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant thisMonthTo = LocalDateTime.of(endOfMonth,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",thisMonthFrom.toEpochMilli());
                map.put("to",thisMonthTo.toEpochMilli());
                return map;
            case LAST_MONTH:
                YearMonth lastMonth = thisMonth.minusMonths(1);
                LocalDate startOfMonth1 = lastMonth.atDay(1);
                LocalDate endOfMonth1 = lastMonth.atEndOfMonth();
                Instant lastMonthFrom = LocalDateTime.of(startOfMonth1,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant lastMonthTo = LocalDateTime.of(endOfMonth1,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",lastMonthFrom.toEpochMilli());
                map.put("to",lastMonthTo.toEpochMilli());
                return map;
            case LAST_THREE_MONTH:
                YearMonth lastThreeMonth= thisMonth.minusMonths(3);
                LocalDate startOfMonth2 = lastThreeMonth.atDay(1);
                LocalDate endOfMonth2 = thisMonth.minusMonths(1).atEndOfMonth();
                Instant lastThreeMonthFrom = LocalDateTime.of(startOfMonth2,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant lastThreeMonthTo = LocalDateTime.of(endOfMonth2,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",lastThreeMonthFrom.toEpochMilli());
                map.put("to",lastThreeMonthTo.toEpochMilli());
                return map;
            case LAST_SIX_MONTH:
                YearMonth lastSixMonth= thisMonth.minusMonths(6);
                LocalDate startOfMonth3 = lastSixMonth.atDay(1);
                LocalDate endOfMonth3 = thisMonth.minusMonths(1).atEndOfMonth();
                Instant lastSixMonthFrom = LocalDateTime.of(startOfMonth3,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant lastSixMonthTo = LocalDateTime.of(endOfMonth3,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",lastSixMonthFrom.toEpochMilli());
                map.put("to",lastSixMonthTo.toEpochMilli());
                return map;
            case THIS_YEAR:
                LocalDate startOfYear = LocalDate.of(localDate.getYear(), 1, 1);
                LocalDate endOfYear = LocalDate.of(localDate.getYear(), 12, 31);
                Instant thisYearFrom = LocalDateTime.of(startOfYear,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant thisYearTo = LocalDateTime.of(endOfYear,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",thisYearFrom.toEpochMilli());
                map.put("to",thisYearTo.toEpochMilli());
                return map;
            case LAST_YEAR:
                LocalDate startOfLastYear = LocalDate.of(localDate.getYear() - 1, 1, 1);
                LocalDate endOfLastYear = LocalDate.of(localDate.getYear() - 1, 12, 31);
                Instant lastYearFrom = LocalDateTime.of(startOfLastYear,LocalTime.MIN).atOffset(ZoneOffset.UTC).toInstant();
                Instant lastYearTo = LocalDateTime.of(endOfLastYear,LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant();
                map.put("from",lastYearFrom.toEpochMilli());
                map.put("to",lastYearTo.toEpochMilli());
                return map;
        } return map;
    }
}
