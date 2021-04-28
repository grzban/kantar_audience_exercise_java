package com.kantar.sessionsjob.dao;

import com.kantar.sessionsjob.model.Statement;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class StatementDao {

    private final List<Statement> statements;

    @Builder
    public StatementDao(@NonNull List<Statement> statements) {
        this.statements = statements;
    }

    public Set<Integer> getHomeIDs() {
        Set<Integer> homeIDs = new LinkedHashSet<>();

        statements.forEach(statement -> {
            int homeNo = statement.parseHomeNoInt();
            if (homeNo != 0) {
                homeIDs.add(statement.parseHomeNoInt());
            }
        });
        log.info("Home IDs: {}", homeIDs);
        return homeIDs;
    }

    public List<Statement> getStatementsForHomeID(int homeID) {
        return statements.stream()
                .filter(statement -> statement.parseHomeNoInt() == homeID)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(Statement :: parseTime))
                .collect(Collectors.toList());
    }
}
