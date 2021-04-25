package com.kantar.sessionsjob.logic;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationCalculator {
    public long durationCalculate(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).plusSeconds(1).getSeconds();
    }
}
