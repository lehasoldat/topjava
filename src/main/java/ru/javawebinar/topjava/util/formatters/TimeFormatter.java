package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    @NonNull
    public LocalTime parse(@NonNull String text, @Nullable Locale locale) throws ParseException {
        return LocalTime.parse(text, TIME_FORMATTER);
    }

    @Override
    @NonNull
    public String print(@NonNull LocalTime object, @Nullable Locale locale) {
        return TIME_FORMATTER.format(object);
    }
}
