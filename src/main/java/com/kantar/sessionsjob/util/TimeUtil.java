package com.kantar.sessionsjob.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

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

    public static String convertDateToString(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }
}