package com.dgrissom.masslibrary.files;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.List;

public final class FileUtils {
    private FileUtils() {}

    public static List<String> lines(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }
    public static byte[] bytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

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


    public static Document parseXML(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }
}
