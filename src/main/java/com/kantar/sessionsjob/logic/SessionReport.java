package com.kantar.sessionsjob.logic;

import com.kantar.sessionsjob.model.Session;
import com.kantar.sessionsjob.model.Statement;
import com.kantar.sessionsjob.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SessionReport {
    public List<Session> prepareSessionReport(List<Statement> statements) {
        List<Session> sessions = new ArrayList<>();
        for (Statement statement : statements) {
            LocalDateTime startTime = TimeUtil.convertDateToLocalDateTime(statement.getStartTime());

            int indexOfActualElement = statements.indexOf(statement);
            int nextIndex = indexOfActualElement + 1;
            LocalDateTime nextSessionStartTime;
            try {
                nextSessionStartTime = TimeUtil.convertDateToLocalDateTime(statements.get(nextIndex).getStartTime());
            } catch (IndexOutOfBoundsException e) {
                nextSessionStartTime = null;
            }

            LocalDateTime endTime = new EndTimeCalculator().endTimeCalculate(startTime, nextSessionStartTime);

            sessions.add(createSession(
                    statement.getHomeNo() + "",
                    TimeUtil.convertDateToString(TimeUtil.convertLocalDateTimeToDate(startTime)),
                    TimeUtil.convertDateToString(TimeUtil.convertLocalDateTimeToDate(endTime)),
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
}
