package com.kantar.sessionsjob.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
    private static final String dateFormatterPatten = "yyyyMMddHHmmss";

    public static boolean isNotSameDate(LocalDateTime date1, LocalDateTime date2) {
        return !getInstant(date1).equals(getInstant(date2));
    }

    private static Instant getInstant(LocalDateTime date) {
        return date.toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime convertStringToLocalDateTime(String stringDate) {
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(dateFormatterPatten));
    }

    public static String convertLocalDateTimeToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(dateFormatterPatten));
    }
}