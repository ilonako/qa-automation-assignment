package com.flamingo.qa.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DateUtils {

    private static final DateTimeFormatter BOOKING_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH);
    private static final DateTimeFormatter UI_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy").withLocale(Locale.ENGLISH);

    public static String toBookingDate(LocalDate date) {
        return date.format(BOOKING_FORMAT);
    }

    public static LocalDate parseBookingDate(String date) {
        return LocalDate.parse(date, BOOKING_FORMAT);
    }

    public static String toUiDate(LocalDate date) {
        return date.format(UI_FORMAT);
    }

    public static LocalDate futureDate(int daysFromNow) {
        return LocalDate.now().plusDays(daysFromNow);
    }
}
