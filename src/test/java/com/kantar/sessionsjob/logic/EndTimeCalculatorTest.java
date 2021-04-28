package com.kantar.sessionsjob.logic;

import com.kantar.sessionsjob.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

@Slf4j
class EndTimeCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "20200101070000,20200101180000,20200101175959",
            "20200101180000,20200101183000,20200101182959",
            "20200101183000,20200101203000,20200101202959",
            "20200101203000,,20200101235959",
            "20200101060000,20200101190000,20200101185959",
            "20200101190000,20200101193000,20200101192959",
            "20200101193000,20200101210000,20200101205959",
            "20200101210000,,20200101235959",
            "20200101200000,,20200101235959",
            "20200102060000,,20200102235959",
            "20200102060000,20200104060000,20200102235959",
            "20210328000000,20210328010000,20210328005959",
            "20210328010000,20210328020000,20210328015959",
            "20210328020000,20210328030000,20210328025959",
            "20210328030000,,20210328235959"
    })
    void endTimeCalculate(String startTime, String nextActivityStartTime, String expected) {
        EndTimeCalculator endTimeCalculator = new EndTimeCalculator();
        LocalDateTime endTime = endTimeCalculator.endTimeCalculate(TimeUtil.convertStringToLocalDateTime(startTime),
                nextActivityStartTime != null ? TimeUtil.convertStringToLocalDateTime(nextActivityStartTime) : null);

        Assertions.assertEquals(expected, TimeUtil.convertLocalDateTimeToString(endTime));
    }
}