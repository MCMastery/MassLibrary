package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

public class LineSegment3d {
    @Formatted
    private final Point3d start, end;

    public LineSegment3d(double x1, double y1, double z1, double x2, double y2, double z2) {
        this(new Point3d(x1, y1, z1), new Point3d(x2, y2, z2));
    }
    public LineSegment3d(Point3d start, Point3d end) {
        this.start = start;
        this.end = end;
    }

    public Point3d getStart() {
        return this.start;
    }
    public LineSegment3d setStart(Point3d start) {
        return new LineSegment3d(start, this.end);
    }
    public Point3d getEnd() {
        return this.end;
    }
    public LineSegment3d setEnd(Point3d end) {
        return new LineSegment3d(this.start, end);
    }

    public double getX1() {
        return this.start.getX();
    }
    public LineSegment3d setX1(double x1) {
        return setStart(new Point3d(x1, this.start.getY(), this.start.getZ()));
    }
    public double getY1() {
        return this.start.getY();
    }
    public LineSegment3d setY1(double y1) {
        return setStart(new Point3d(this.start.getY(), y1, this.start.getZ()));
    }
    public double getZ1() {
        return this.start.getZ();
    }
    public LineSegment3d setZ1(double z1) {
        return setStart(new Point3d(this.start.getY(), this.start.getY(), z1));
    }
    public double getX2() {
        return this.end.getX();
    }
    public LineSegment3d setX2(double x2) {
        return setEnd(new Point3d(x2, this.end.getY(), this.end.getZ()));
    }
    public double getY2() {
        return this.end.getY();
    }
    public LineSegment3d setY2(double y2) {
        return setEnd(new Point3d(this.end.getX(), y2, this.end.getZ()));
    }
    public double getZ2() {
        return this.end.getZ();
    }
    public LineSegment3d setZ2(double z2) {
        return setEnd(new Point3d(this.end.getY(), this.end.getY(), z2));
    }

    public LineSegment3d swapPoints() {
        return new LineSegment3d(this.end, this.start);
    }

    public Vector3d direction() {
        return Vector3d.direction(this.start, this.end);
    }

    public Point3d midpoint() {
        return this.start.add(this.end).divide(2);
    }
    public double lengthSquared() {
        return this.start.distanceSquared(this.end);
    }
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    // keep start position, changes end position
    public LineSegment3d setLength(double length) {
        return setEnd(this.start.add(direction().multiply(length)));
    }
    public LineSegment3d extend(double amount) {
        return setLength(length() + amount);
    }

    public Point3d point(double distanceAlongLine) {
        return this.start.add(direction().multiply(distanceAlongLine));
    }

    public LineSegment3d translate(double tx, double ty, double tz) {
        return setStart(this.start.add(tx, ty, tz))
                .setEnd(this.end.add(tx, ty, tz));
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LineSegment3d))
            return false;
        LineSegment3d other = (LineSegment3d) o;
        return this.start.equals(other.getStart()) && this.end.equals(other.getEnd());
    }
}
