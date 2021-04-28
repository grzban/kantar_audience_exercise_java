package com.kantar.sessionsjob.logic;

import com.kantar.sessionsjob.util.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

class DurationCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "20200101070000,20200101175959,39600",
            "20200101180000,20200101182959,1800",
            "20200101183000,20200101202959,7200",
            "20200101203000,20200101235959,12600",
            "20200101060000,20200101185959,46800",
            "20200101190000,20200101192959,1800",
            "20200101193000,20200101205959,5400",
            "20200101210000,20200101235959,10800",
            "20200101200000,20200101235959,14400",
            "20200102060000,20200102235959,64800",
            "20200102060000,20200102235959,64800",
            "20210328000000,20210328005959,3600",
            "20210328010000,20210328015959,3600",
            "20210328020000,20210328025959,3600",
            "20210328030000,20210328235959,75600"
    })
    void durationCalculateWithStartAndEndTime(String startTime, String endTime, long expectedDuration) {
        DurationCalculator durationCalculator = new DurationCalculator();
        long duration = durationCalculator.durationCalculate(TimeUtil.convertStringToLocalDateTime(startTime), TimeUtil.convertStringToLocalDateTime(endTime));
        Assertions.assertEquals(expectedDuration, duration);
    }

    @ParameterizedTest
    @CsvSource({
            "20200101070000,20200101180000,39600",
            "20200101180000,20200101183000,1800",
            "20200101183000,20200101203000,7200",
            "20200101203000,,12600",
            "20200101060000,20200101190000,46800",
            "20200101190000,20200101193000,1800",
            "20200101193000,20200101210000,5400",
            "20200101210000,,10800",
            "20200101200000,,14400",
            "20200102060000,,64800",
            "20200102060000,20200104060000,64800",
            "20210328000000,20210328010000,3600",
            "20210328010000,20210328020000,3600",
            "20210328020000,20210328030000,3600",
            "20210328030000,,75600"
    })
    void durationCalculateWithStartAndNextActivityStartTime(String startTime, String nextActivityStartTime, long expectedDuration) {
        EndTimeCalculator endTimeCalculator = new EndTimeCalculator();
        LocalDateTime endTime = endTimeCalculator.endTimeCalculate(TimeUtil.convertStringToLocalDateTime(startTime),
                nextActivityStartTime != null ? TimeUtil.convertStringToLocalDateTime(nextActivityStartTime) : null);
        DurationCalculator durationCalculator = new DurationCalculator();
        long duration = durationCalculator.durationCalculate(TimeUtil.convertStringToLocalDateTime(startTime), endTime);
        Assertions.assertEquals(expectedDuration, duration);
    }
}