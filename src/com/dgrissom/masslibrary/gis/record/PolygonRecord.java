package com.dgrissom.masslibrary.gis.record;

import com.dgrissom.masslibrary.gis.ShapeType;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

import java.util.ArrayList;
import java.util.List;

public class PolygonRecord implements Record {
    private final Rectangle2d boundingBox;
    // each part specifies the start of a new ring corresponding to the start index in points[]
    private int[] parts;
    private Point2d[] points;

    public PolygonRecord(Rectangle2d boundingBox, int numParts, int numPoints) {
        this(boundingBox, new int[numParts], new Point2d[numPoints]);
    }
    public PolygonRecord(Rectangle2d boundingBox, int[] parts, Point2d[] points) {
        this.boundingBox = boundingBox;
        this.parts = parts;
        this.points = points;
    }

    public Rectangle2d getBoundingBox() {
        return this.boundingBox;
    }
    public int[] getParts() {
        return this.parts;
    }
    public void setParts(int[] parts) {
        this.parts = parts;
    }
    public Point2d[] getPoints() {
        return this.points;
    }
    public void setPoints(Point2d[] points) {
        this.points = points;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.POLYGON;
    }

    public List<Ring> rings() {
        List<Ring> rings = new ArrayList<>();
        for (int i = 0; i < this.parts.length; i++) {
            int startIndex = this.parts[i];
            int endIndex = (i == this.parts.length - 1) ? this.points.length : this.parts[i + 1];

            Point2d[] ringPoints = new Point2d[endIndex - startIndex];
            for (int j = startIndex; j < endIndex; j++)
                ringPoints[j - startIndex] = this.points[j];
            rings.add(new Ring(ringPoints));
        }
        return rings;
    }

    @Override
    public String toString() {
        return "PolygonRecord" + rings().toString();
    }
}
