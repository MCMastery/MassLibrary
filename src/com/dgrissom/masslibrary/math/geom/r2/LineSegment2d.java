package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.functions.LinearFunction;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

import java.util.List;

public final class LineSegment2d {
    @Formatted
    private final Point2d start, end;

    public LineSegment2d(double x1, double y1, double x2, double y2) {
        this(new Point2d(x1, y1), new Point2d(x2, y2));
    }
    public LineSegment2d(Point2d start, Point2d end) {
        this.start = start;
        this.end = end;
    }

    public Point2d getStart() {
        return this.start;
    }
    public LineSegment2d setStart(Point2d start) {
        return new LineSegment2d(start, this.end);
    }
    public Point2d getEnd() {
        return this.end;
    }
    public LineSegment2d setEnd(Point2d end) {
        return new LineSegment2d(this.start, end);
    }

    public double getX1() {
        return this.start.getX();
    }
    public LineSegment2d setX1(double x1) {
        return setStart(new Point2d(x1, this.start.getY()));
    }
    public double getY1() {
        return this.start.getY();
    }
    public LineSegment2d setY1(double y1) {
        return setStart(new Point2d(this.start.getY(), y1));
    }
    public double getX2() {
        return this.end.getX();
    }
    public LineSegment2d setX2(double x2) {
        return setEnd(new Point2d(x2, this.end.getY()));
    }
    public double getY2() {
        return this.end.getY();
    }
    public LineSegment2d setY2(double y2) {
        return setEnd(new Point2d(this.end.getX(), y2));
    }

    public LineSegment2d swapPoints() {
        return new LineSegment2d(this.end, this.start);
    }

    public Vector2d direction() {
        return Vector2d.direction(this.start, this.end);
    }

    public Point2d midpoint() {
        return this.start.add(this.end).divide(2);
    }
    public double slope() {
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }
    public double lengthSquared() {
        return this.start.distanceSquared(this.end);
    }
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    // keep start position, changes end position
    public LineSegment2d setLength(double length) {
        return setEnd(this.start.add(direction().multiply(length)));
    }
    public LineSegment2d extend(double amount) {
        return setLength(length() + amount);
    }
    // angle with start as origin and end as the point
    public double angle() {
        return this.end.angle(this.start);
    }
    public Vector2d normal() {
        double magnitude = 1;
        double angle = Math.PI * 2 - angle();
        return new PolarCoordinates(magnitude, angle).toVector();
    }

    public Point2d point(double distanceAlongLine) {
        return this.start.add(direction().multiply(distanceAlongLine));
    }

    // returns null if line segment never crosses bounds
    public LineSegment2d clamp(Rectangle2d bounds) {
        List<Point2d> intersections = Intersections2d.intersections(this, bounds);
        if (intersections == null || intersections.size() == 0)
            return null;
        // one point is inside
        if (intersections.size() == 1) {
            if (Intersections2d.contains(bounds, this.start)) {
                // then it's the end that's not inside
                return setEnd(intersections.get(0));
            }
            // it's the start that's not inside
            return setStart(intersections.get(0));
        }
        // both are outside
        return new LineSegment2d(intersections.get(0), intersections.get(1));
    }

    public boolean parallel(LineSegment2d other) {
        return slope() == other.slope();
    }
    public boolean overlaps(LineSegment2d other, double error) {
        return slope() == other.slope() && (Intersections2d.contains(this, this.start, error)
                || Intersections2d.contains(this, this.end, error));
    }

    public LineSegment2d translate(double tx, double ty) {
        return setStart(this.start.add(tx, ty))
                .setEnd(this.end.add(tx, ty));
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LineSegment2d))
            return false;
        LineSegment2d other = (LineSegment2d) o;
        return this.start.equals(other.getStart()) && this.end.equals(other.getEnd());
    }

    public static LineSegment2d from(Point2d start, double angle, double length) {
        PolarCoordinates endPolar = new PolarCoordinates(start, length, angle);
        return new LineSegment2d(start, endPolar.toCartesian());
    }
    public static LineSegment2d from(LinearFunction function, Point2d start, double length) {
        // just get (x,y) of another point as the end
        double endX = start.getX() + 1;
        return new LineSegment2d(start, new Point2d(endX, function.y(endX))).setLength(length);
    }
}
