package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Triangle2d;

// https://en.wikipedia.org/wiki/Circle
public final class Circle2d {
    @Formatted
    private final Point2d center;
    @Formatted
    private final double radius;

    public Circle2d() {
        this(1);
    }
    public Circle2d(double radius) {
        this(0, 0, radius);
    }
    public Circle2d(double cx, double cy, double radius) {
        this(new Point2d(cx, cy), radius);
    }
    public Circle2d(Point2d center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point2d getCenter() {
        return this.center;
    }
    public Circle2d setCenter(Point2d center) {
        return new Circle2d(center, this.radius);
    }
    public double getRadius() {
        return this.radius;
    }
    public Circle2d setRadius(double radius) {
        return new Circle2d(this.center, radius);
    }

    public double diameter() {
        return 2 * this.radius;
    }
    public Circle2d setDiameter(double diameter) {
        return new Circle2d(this.center, diameter / 2);
    }
    public double circumference() {
        return 2 * Math.PI * this.radius;
    }
    public double area() {
        return Math.PI * this.radius * this.radius;
    }

    // returns the point along the circumference of the circle at the given angle, in polar coordinates
    public PolarCoordinates point(double angle) {
        return new PolarCoordinates(this.center, this.radius, angle);
    }
    // returns the line segment starting at the center going to point(angle)
    public LineSegment2d radius(double angle) {
        return this.center.lineTo(point(angle).toCartesian());
    }
    // returns the line segment going through the center, starting at point(startAngle)
    public LineSegment2d diameter(double startAngle) {
        double endAngle = startAngle + Math.PI;
        return chord(startAngle, endAngle);
    }
    // returns the line segment start at point(startAngle) going to point(endAngle)
    public LineSegment2d chord(double startAngle, double endAngle) {
        return point(startAngle).toCartesian().lineTo(point(endAngle).toCartesian());
    }

    public Circle2d translate(double tx, double ty) {
        return setCenter(this.center.add(tx, ty));
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }

    public static Circle2d from(Point2d a, Point2d b, Point2d c) {
        return new Triangle2d(a, b, c).circumcircle();
    }
    public static Circle2d from(Point2d a, Point2d b) {
        return new Circle2d(a.add(b).divide(2), a.distance(b) / 2);
    }
}
