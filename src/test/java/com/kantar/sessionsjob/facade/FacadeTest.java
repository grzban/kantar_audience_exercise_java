package com.kantar.sessionsjob.facade;

import com.kantar.sessionsjob.util.ResourcePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

@Slf4j
class FacadeTest {
    private String actualSessionBeansFile;

    @Test
    void prepareSessionReport() throws IOException {
        String inputFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "input-statements.psv");
        String expectedSessionsFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "expected-sessions.psv");
        actualSessionBeansFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "") + "/actual-sessionBeans.psv";
        log.info("actualSessionBeansFile: {}", actualSessionBeansFile);

        new Facade(inputFile, actualSessionBeansFile).prepareSessionReport();

        byte[] actual = Files.readAllBytes(ResourcePathUtil.getPathFromString(actualSessionBeansFile));
        byte[] expected = Files.readAllBytes(ResourcePathUtil.getPathFromString(expectedSessionsFile));

        Assertions.assertArrayEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        try {
            Files.delete(ResourcePathUtil.getPathFromString(actualSessionBeansFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}