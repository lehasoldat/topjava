package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime getStartDateTime(LocalDate localDate) {
        if (localDate == null) return null;
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime getEndDateTime(LocalDate localDate) {
        if (localDate == null) return null;
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }
}

