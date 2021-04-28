package com.kantar.sessionsjob.logic;

import com.kantar.sessionsjob.model.Session;
import com.kantar.sessionsjob.model.Statement;
import com.kantar.sessionsjob.util.TimeUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class SessionsReport {
    private final List<Session> dataToWrite;

    @Builder
    public SessionsReport() {
        dataToWrite = new ArrayList<>();
        dataToWrite.add(createHeader());
    }

    public void addDataToWrite(List<Statement> data) {
        dataToWrite.addAll(prepareSessionReport(data));
    }

    private List<Session> prepareSessionReport(List<Statement> statements) {
        List<Session> sessions = new ArrayList<>();
        for (Statement statement : statements) {
            LocalDateTime startTime = statement.parseTime();

            int indexOfActualElement = statements.indexOf(statement);
            int nextIndex = indexOfActualElement + 1;
            LocalDateTime nextSessionStartTime;
            try {
                nextSessionStartTime = statements.get(nextIndex).parseTime();
            } catch (IndexOutOfBoundsException e) {
                nextSessionStartTime = null;
            }

            LocalDateTime endTime = new EndTimeCalculator().endTimeCalculate(startTime, nextSessionStartTime);

            sessions.add(createSession(
                    statement.parseHomeNoInt() + "",
                    TimeUtil.convertLocalDateTimeToString(startTime),
                    TimeUtil.convertLocalDateTimeToString(endTime),
                    statement.getChannel(),
                    statement.getActivity(),
                    new DurationCalculator().durationCalculate(startTime, endTime) + ""));
        }
        return sessions;
    }

    private Session createSession(String homeNo, String startTime, String endTime,
                                  String channel, String activity, String duration) {
        return Session
                .builder()
                .homeNo(homeNo)
                .startTime(startTime)
                .endTime(endTime)
                .channel(channel)
                .activity(activity)
                .duration(duration)
                .build();
    }

    private Session createHeader() {
        return Session
                .builder()
                .homeNo("HomeNo")
                .channel("Channel")
                .startTime("Starttime")
                .activity("Activity")
                .endTime("EndTime")
                .duration("Duration")
                .build();
    }
}
