package com.dgrissom.masslibrary.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;

public final class FileUtils {
    private FileUtils() {}

    public static void deleteNonEmptyFolder(File file) throws FileSystemException {
        File[] contents = file.listFiles();
        if (contents != null)
            for (File f : contents)
                deleteNonEmptyFolder(f);
        if (!file.delete())
            throw new FileSystemException("could not delete file " + file.getName());
    }

    public static void createFileIfNeeded(File file) throws IOException {
        if (!file.exists())
            if (!file.createNewFile())
                throw new FileSystemException("could not create file " + file.getName());
    }
    public static void createFolderIfNeeded(File folder) throws FileSystemException {
        if (!folder.exists())
            if (!folder.mkdir())
                throw new FileSystemException("could not create folder");
    }
}
