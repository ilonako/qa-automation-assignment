package com.flamingo.qa.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter BOOKING_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter UI_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private DateUtils() {
    }

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
