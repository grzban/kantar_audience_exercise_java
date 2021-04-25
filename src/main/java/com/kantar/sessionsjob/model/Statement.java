package com.kantar.sessionsjob.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;

import java.util.Date;

@Getter
@SuppressWarnings("unused")
public class Statement {

    @CsvBindByName(column = "HomeNo")
    private int homeNo;

    @CsvBindByName(column = "Channel")
    private String channel;

    @CsvBindByName(column = "Starttime")
    @CsvDate("yyyyMMddHHmmss")
    private Date startTime;

    @CsvBindByName(column = "Activity")
    private String activity;
}
