package com.kantar.sessionsjob.facade;

import com.kantar.sessionsjob.dao.StatementDao;
import com.kantar.sessionsjob.handlers.FileHandler;
import com.kantar.sessionsjob.logic.SessionReport;
import com.kantar.sessionsjob.model.Session;
import com.kantar.sessionsjob.model.Statement;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Facade {
    private final String inputFilePath;
    private final String outputFilePath;
    private final FileHandler fileHandler;
    private List<Statement> statements;

    @Builder
    public Facade(@NonNull String inputFilePath, @NonNull String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        fileHandler = buildFileHandler();
    }

    private FileHandler buildFileHandler() {
        return FileHandler
                .builder()
                .inputFilePath(inputFilePath)
                .outputFilePath(outputFilePath)
                .build();
    }

    private StatementDao buildStatementsDao() {
        return StatementDao
                .builder()
                .statements(statements)
                .build();
    }

    public void prepareSessionReport() {
        readDataFromFile();
        StatementDao statementDao = buildStatementsDao();
        List<Session> dataToWrite = new ArrayList<>();
        dataToWrite.add(createHeader());
        statementDao.getHomeIDs().forEach(homeID -> {
            List<Statement> sessionsForHomeID = statementDao.getStatementsForHomeID(homeID);
            dataToWrite.addAll(new SessionReport().prepareSessionReport(sessionsForHomeID));
        });
        writeDataToFile(dataToWrite);
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

    private void readDataFromFile() {
        try {
            statements = fileHandler.readDataFromFile();
        } catch (FileNotFoundException e) {
            log.info("File: {} does not exist", fileHandler.getInputFilePath());
        }
    }

    private void writeDataToFile(List<Session> sessionBeans) {
        try {
            fileHandler.writeDataToFile(sessionBeans);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
