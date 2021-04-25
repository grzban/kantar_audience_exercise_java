package com.kantar.sessionsjob.util;

import java.io.File;
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
}
