package com.kantar.sessionsjob.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourcePathUtil {
    public static String getStringAbsolutePathToResource(Object context, String resource) {
        ClassLoader classLoader = context.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resource)).getFile());
        return file.getAbsolutePath();
    }

    public static Path getPathFromString(String resource) {
        return Paths.get(resource);
    }

    public static void deleteFileIfExists(String path) {
        try {
            Files.deleteIfExists(getPathFromString(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
