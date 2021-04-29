package com.kantar.sessionsjob.dao;

import com.kantar.sessionsjob.handlers.FileHandler;
import com.kantar.sessionsjob.model.Statement;
import com.kantar.sessionsjob.util.ResourcePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
class StatementDaoTest {
    private List<Statement> statements;
    private final String outputFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "") + "/output-statements.psv";

    @BeforeEach
    void setUp() {
        String inputFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "input-statements.psv");
        FileHandler fileHandler = FileHandler.builder()
                .inputFilePath(inputFile)
                .outputFilePath(outputFile)
                .build();
        try {
            statements = fileHandler.readDataFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getHomeIDs() {
        Set<Integer> expectedHomeIDsList = new LinkedHashSet<>(Arrays.asList(1234, 45678, 777, 900, 12345));
        StatementDao dao = StatementDao.builder().statements(statements).build();
        Assertions.assertEquals(expectedHomeIDsList, dao.getHomeIDs());
    }

    @ParameterizedTest
    @CsvSource({
            "1234,4",
            "45678,4",
            "777,1",
            "900,1",
            "12345,4"})
    void getStatementsForHomeIDBySizeOfList(int homeID, int expectedSizeOfList) {
        StatementDao dao = StatementDao.builder().statements(statements).build();
        Assertions.assertEquals(expectedSizeOfList, dao.getStatementsForHomeID(homeID).size());
    }

    @AfterEach
    void tearDown() {
        ResourcePathUtil.deleteFileIfExists(outputFile);
    }
}