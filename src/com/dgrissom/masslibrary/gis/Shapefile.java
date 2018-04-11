package com.dgrissom.masslibrary.gis;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;
import com.dgrissom.masslibrary.gis.record.Record;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

import java.util.ArrayList;
import java.util.List;

// https://en.wikipedia.org/wiki/Shapefile
// http://www.esri.com/library/whitepapers/pdfs/shapefile.pdf
public class Shapefile {
    public static final int FILE_CODE = 0x0000270A;
    public static final int VERSION = 1000;

    private final int fileCode, version;
    private List<Record> records;
    @Formatted
    private ShapeType shapeType;
    // minimum bounding rectangle
    @Formatted
    private Rectangle2d boundingBox;
    @Formatted
    private double minZ, maxZ, minM, maxM;

    public Shapefile() {
        this.fileCode = FILE_CODE;
        this.version = VERSION;
        this.records = new ArrayList<>();
    }

    public int getFileCode() {
        return this.fileCode;
    }
    public int getVersion() {
        return this.version;
    }
    public List<Record> getRecords() {
        return this.records;
    }
    public void add(Record record) {
        this.records.add(record);
    }
    public ShapeType getShapeType() {
        return this.shapeType;
    }
    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }
    public Rectangle2d getBoundingBox() {
        return this.boundingBox;
    }
    public void setBoundingBox(Rectangle2d boundingBox) {
        this.boundingBox = boundingBox;
    }
    public double getMinZ() {
        return this.minZ;
    }
    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }
    public double getMaxZ() {
        return this.maxZ;
    }
    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }
    public double getMinM() {
        return this.minM;
    }
    public void setMinM(double minM) {
        this.minM = minM;
    }
    public double getMaxM() {
        return this.maxM;
    }
    public void setMaxM(double maxM) {
        this.maxM = maxM;
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
