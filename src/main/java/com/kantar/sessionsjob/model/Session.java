package com.kantar.sessionsjob.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Session {
    @CsvBindByName(column = "HomeNo")
    @CsvBindByPosition(position = 0)
    private final String homeNo;

    @CsvBindByName(column = "Channel")
    @CsvBindByPosition(position = 1)
    private final String channel;

    @CsvBindByName(column = "Starttime")
    @CsvBindByPosition(position = 2)
    private final String startTime;

    @CsvBindByName(column = "Activity")
    @CsvBindByPosition(position = 3)
    private final String activity;

    @CsvBindByName(column = "EndTime")
    @CsvBindByPosition(position = 4)
    private final String endTime;

    @CsvBindByName(column = "Duration")
    @CsvBindByPosition(position = 5)
    private final String duration;

    @Builder
    public Session(String homeNo, String channel, String startTime, String activity, String endTime, String duration) {
        this.homeNo = homeNo;
        this.channel = channel;
        this.startTime = startTime;
        this.activity = activity;
        this.endTime = endTime;
        this.duration = duration;
    }
}
