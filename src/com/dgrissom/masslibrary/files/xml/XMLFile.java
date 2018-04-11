package com.dgrissom.masslibrary.files.xml;

public class XMLFile {
    private final String version, encoding;

    public XMLFile(String version, String encoding) {
        this.version = version;
        this.encoding = encoding;
    }

    public String getVersion() {
        return this.version;
    }
    public String getEncoding() {
        return this.encoding;
    }
}
