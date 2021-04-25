package com.kantar.sessionsjob.facade;

import com.kantar.sessionsjob.util.ResourcePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
class FacadeTest {

    @Test
    void prepareSessionReport() throws IOException {
        String inputFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "input-statements.psv");
        String expectedSessionsFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "expected-sessions.psv");
        String actualSessionBeansFile = ResourcePathUtil.getStringAbsolutePathToResource(this, "") + "/actual-sessionBeans.psv";

        new Facade(inputFile, actualSessionBeansFile).prepareSessionReport();

        byte[] actual = Files.readAllBytes(ResourcePathUtil.getPathFromString(actualSessionBeansFile));
        byte[] expected = Files.readAllBytes(ResourcePathUtil.getPathFromString(expectedSessionsFile));

        Assertions.assertArrayEquals(expected, actual);
    }
}