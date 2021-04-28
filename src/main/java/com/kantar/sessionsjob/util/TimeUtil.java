package com.kantar.sessionsjob.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {
    private static final String dateFormatterPatten = "yyyyMMddHHmmss";

    public static Date convertLocalDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static boolean isNotSameDate(Date date1, Date date2) {
        return !getInstant(date1).equals(getInstant(date2));
    }

    private static Instant getInstant(Date date) {
        return date.toInstant().truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime convertStringToLocalDateTime(String stringDate) {
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(dateFormatterPatten));
    }

    public static String convertLocalDateTimeToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(dateFormatterPatten));
    }
}