package com.dgrissom.masslibrary.gis;

import com.dgrissom.masslibrary.ByteUtils;
import com.dgrissom.masslibrary.files.FileUtils;
import com.dgrissom.masslibrary.gis.record.NullRecord;
import com.dgrissom.masslibrary.gis.record.PolygonRecord;
import com.dgrissom.masslibrary.gis.record.Record;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

// https://en.wikipedia.org/wiki/Shapefile
// http://www.esri.com/library/whitepapers/pdfs/shapefile.pdf
public class ShapefileParser {
    private final File file;
    private final byte[] bytes;
    private Shapefile shapefile;
    private int currentIndex;

    public ShapefileParser(File file) throws IOException {
        this.file = file;
        this.bytes = FileUtils.bytes(file);
    }

    public File getFile() {
        return this.file;
    }

    // converts the 4 bytes at currentIndex to a 32 bit integer
    private int nextInt(ByteOrder byteOrder) {
        int i = ByteUtils.toInt32(this.bytes, this.currentIndex, byteOrder);
        this.currentIndex += 4;
        return i;
    }
    // converts the 8 bytes at currentIndex to a double
    private double nextDouble(ByteOrder byteOrder) {
        double d = ByteUtils.toDouble(this.bytes, this.currentIndex, byteOrder);
        this.currentIndex += 8;
        return d;
    }
    private ShapeType nextShapeType(ByteOrder byteOrder) {
        return ShapeType.fromId(nextInt(byteOrder));
    }
    private Point2d nextPoint(ByteOrder byteOrder) {
        double x = nextDouble(byteOrder);
        double y = nextDouble(byteOrder);
        return new Point2d(x, y);
    }
    private Rectangle2d nextBoundingBox(ByteOrder byteOrder) {
        double minX = nextDouble(byteOrder);
        double minY = nextDouble(byteOrder);
        double maxX = nextDouble(byteOrder);
        double maxY = nextDouble(byteOrder);
        return Rectangle2d.from(minX, minY, maxX, maxY);
    }


    private void parseMainHeader() {
        int fileCode = nextInt(ByteOrder.BIG_ENDIAN);
        if (fileCode != Shapefile.FILE_CODE)
            throw new IllegalArgumentException("Shapefile file code != " + Shapefile.FILE_CODE);

        // bytes 4 up to 24 are not used
        this.currentIndex = 24;
        int fileLength = nextInt(ByteOrder.BIG_ENDIAN);

        int version = nextInt(ByteOrder.LITTLE_ENDIAN);
        if (version != Shapefile.VERSION)
            throw new IllegalArgumentException("Shapefile version != " + Shapefile.VERSION);

        this.shapefile.setShapeType(nextShapeType(ByteOrder.LITTLE_ENDIAN));
        this.shapefile.setBoundingBox(nextBoundingBox(ByteOrder.LITTLE_ENDIAN));

        // will all == 0 if unused
        this.shapefile.setMinZ(nextDouble(ByteOrder.LITTLE_ENDIAN));
        this.shapefile.setMaxZ(nextDouble(ByteOrder.LITTLE_ENDIAN));
        this.shapefile.setMinM(nextDouble(ByteOrder.LITTLE_ENDIAN));
        this.shapefile.setMaxM(nextDouble(ByteOrder.LITTLE_ENDIAN));
    }


    private void parseNextRecord() {
        int recordNumber = nextInt(ByteOrder.BIG_ENDIAN);
        int contentLength = nextInt(ByteOrder.BIG_ENDIAN);
        // range is [this.currentIndex, endIndex)
        // * 2 because contentLength is measured as the number of 16-bit words
        int endIndex = this.currentIndex + contentLength * 2;

        ShapeType shapeType = nextShapeType(ByteOrder.LITTLE_ENDIAN);
        Record record;
        switch (shapeType) {
            case NULL:
                record = new NullRecord();
                break;
            case POLYGON:
                Rectangle2d boundingBox = nextBoundingBox(ByteOrder.LITTLE_ENDIAN);
                int numParts = nextInt(ByteOrder.LITTLE_ENDIAN);
                int numPoints = nextInt(ByteOrder.LITTLE_ENDIAN);

                int[] parts = new int[numParts];
                for (int i = 0; i < parts.length; i++)
                    parts[i] = nextInt(ByteOrder.LITTLE_ENDIAN);

                Point2d[] points = new Point2d[numPoints];
                for (int i = 0; i < points.length; i++)
                    points[i] = nextPoint(ByteOrder.LITTLE_ENDIAN);
                record = new PolygonRecord(boundingBox, parts, points);
                break;
            default:
                throw new IllegalArgumentException("illegal ShapeType (" + shapeType + ")");
        }

        this.shapefile.add(record);
        this.currentIndex = endIndex;
    }
    private void parseRecords() {
        parseNextRecord();
    }



    public Shapefile parse() {
        this.shapefile = new Shapefile();
        this.currentIndex = 0;
        parseMainHeader();
        parseRecords();
        return this.shapefile;
    }
}
