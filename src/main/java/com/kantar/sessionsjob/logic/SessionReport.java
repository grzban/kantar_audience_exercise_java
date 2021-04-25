package com.kantar.sessionsjob.logic;

import com.kantar.sessionsjob.model.Session;
import com.kantar.sessionsjob.model.Statement;
import com.kantar.sessionsjob.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SessionReport {
    public List<Session> prepareSessionReport(List<Statement> statements) {
        int statementsSize = statements.size();
        List<Session> sessions = new ArrayList<>();
        for (Statement statement : statements) {
            LocalDateTime startTime = TimeUtil.convertDateToLocalDateTime(statement.getStartTime());

            int indexOfActualElement = statements.indexOf(statement);
            int nextIndex = indexOfActualElement + 1;

            LocalDateTime endTime;
            if (nextIndex < statementsSize) {
                endTime = TimeUtil.convertDateToLocalDateTime(statements.get(nextIndex).getStartTime()).minusSeconds(1);
                if (TimeUtil.isNotSameDate(statement.getStartTime(), TimeUtil.convertLocalDateTimeToDate(endTime))) {
                    endTime = startTime.toLocalDate().atTime(LocalTime.MAX);
                }
            } else {
                endTime = startTime.toLocalDate().atTime(LocalTime.MAX);
            }

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
