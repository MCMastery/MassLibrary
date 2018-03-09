package com.dgrissom.masslibrary.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

public class TextFile implements Iterable<String> {
    private List<String> lines;

    public TextFile(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return this.lines;
    }
    public String getLine(int line) {
        return this.lines.get(line);
    }
    public void appendLine(String line) {
        this.lines.add(line);
    }
    public void setLine(int line, String text) {
        this.lines.set(line, text);
    }
    public void removeLine(int line) {
        this.lines.remove(line);
    }

    // appends to the last line
    public void append(String text) {
        if (this.lines.size() == 0)
            this.lines.add("");
        int line = this.lines.size() - 1;
        this.lines.set(line, this.lines.get(line) + text);
    }

    @Override
    public Iterator<String> iterator() {
        return this.lines.iterator();
    }

    public void save(File file) throws IOException {
        Files.write(file.toPath(), this.lines);
    }
    public static TextFile load(File file) throws IOException {
        return new TextFile(Files.readAllLines(file.toPath()));
    }
}
