package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @NonNull
    public LocalDate parse(@NonNull String text, @Nullable Locale locale) throws ParseException {
        return LocalDate.parse(text, DATE_FORMATTER);
    }

    @Override
    @NonNull
    public String print(@NonNull LocalDate object, @Nullable Locale locale) {
        return DATE_FORMATTER.format(object);
    }
}
