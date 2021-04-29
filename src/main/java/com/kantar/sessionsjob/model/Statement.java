package com.kantar.sessionsjob.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Getter
@Slf4j
@SuppressWarnings("unused")
public class Statement {

    @CsvBindByName(column = "HomeNo")
    @Getter(AccessLevel.NONE)
    private String homeNo;

    @CsvBindByName(column = "Channel")
    private String channel;

    @CsvBindByName(column = "Starttime")
    @Getter(AccessLevel.NONE)
    private String startTime;

    @CsvBindByName(column = "Activity")
    private String activity;

    public int getHomeNoInt() {
        int homeNoInt = 0;
        try {
            homeNoInt = Integer.parseInt(homeNo);
        } catch (NumberFormatException e) {
            log.info("Bad number");
        }
        return homeNoInt;
    }

    public LocalDateTime getStartTime() {
        String pattern = "yyyyMMddHHmmss";
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime time = null;
        try {
            time = LocalDateTime.parse(startTime, format);
        } catch (DateTimeParseException e) {
            log.info("Bad date time");
        }
        return time;
    }

}
