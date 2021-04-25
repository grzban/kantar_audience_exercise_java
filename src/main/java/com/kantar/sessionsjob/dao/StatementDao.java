package com.kantar.sessionsjob.dao;

import com.kantar.sessionsjob.model.Statement;
import lombok.Builder;
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

    @Builder
    public StatementDao(@NonNull List<Statement> statements) {
        this.statements = statements;
    }

    public Set<Integer> getHomeIDs() {
        Set<Integer> homeIDs = new LinkedHashSet<>();

        statements.forEach(statement ->
                homeIDs.add(statement.getHomeNo())
        );
        log.info("Home IDs: {}", homeIDs);
        return homeIDs;
    }

    public List<Statement> getStatementsForHomeID(int homeID) {
        return statements.stream()
                .filter(statement -> statement.getHomeNo() == homeID)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparingLong(date -> date.getStartTime().getTime()))
                .collect(Collectors.toList());
    }
}
