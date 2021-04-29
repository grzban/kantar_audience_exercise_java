package com.kantar.sessionsjob.dao;

import com.kantar.sessionsjob.model.Statement;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class StatementDao {

    private final List<Statement> statements;

    @Getter
    private int badIDCounter;

    @Builder
    public StatementDao(@NonNull List<Statement> statements) {
        this.statements = statements;
    }

    public Set<Integer> getHomeIDs() {
        Set<Integer> homeIDs = new LinkedHashSet<>();
        badIDCounter = 0;
        statements.forEach(statement -> {
            int homeNo = statement.getHomeNoInt();
            if (homeNo != 0) {
                homeIDs.add(statement.getHomeNoInt());
            } else {
                badIDCounter++;
            }
        });
        log.info("Home IDs: {}", homeIDs);
        return homeIDs;
    }

    public List<Statement> getStatementsForHomeID(int homeID) {
        return statements.stream()
                .filter(statement -> statement.getHomeNoInt() == homeID && statement.getStartTime() != null)
                .sorted(Comparator.comparing(Statement::getStartTime))
                .collect(Collectors.toList());
    }
}
