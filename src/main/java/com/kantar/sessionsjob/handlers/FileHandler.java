package com.kantar.sessionsjob.handlers;

import com.kantar.sessionsjob.model.Session;
import com.kantar.sessionsjob.model.Statement;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

@Slf4j
@Getter
public class FileHandler {
    private final String inputFilePath;
    private final String outputFilePath;

    @Builder
    public FileHandler(@NonNull String inputFilePath, @NonNull String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public List<Statement> readDataFromFile() throws FileNotFoundException {
        return new CsvToBeanBuilder<Statement>(new FileReader(inputFilePath))
                .withType(Statement.class)
                .withSeparator('|')
                .build()
                .parse();
    }

    public void writeDataToFile(List<Session> dataToWrite) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter(outputFilePath);

        StatefulBeanToCsvBuilder<Session> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<Session> beanToCsv = builder
                .withSeparator('|')
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withLineEnd(CSVWriter.RFC4180_LINE_END)
                .build();
        beanToCsv.write(dataToWrite);
        writer.close();
    }
}
