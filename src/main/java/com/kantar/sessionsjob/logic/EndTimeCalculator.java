package com.kantar.sessionsjob.logic;

import com.kantar.sessionsjob.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EndTimeCalculator {
    public LocalDateTime endTimeCalculate(LocalDateTime startTime, LocalDateTime nextStartTime) {
        LocalDateTime endTime;
        if (nextStartTime == null) {
            endTime = getEndOfDay(startTime);
        } else {
            endTime = nextStartTime.minusSeconds(1);
        }
        if (TimeUtil.isNotSameDate(startTime, endTime)) {
            endTime = getEndOfDay(startTime);
        }

        return endTime;
    }

    private LocalDateTime getEndOfDay(LocalDateTime startTime) {
        return startTime.toLocalDate().atTime(LocalTime.MAX);
    }
}
