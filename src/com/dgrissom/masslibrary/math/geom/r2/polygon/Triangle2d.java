package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;
import com.dgrissom.masslibrary.math.geom.r2.*;

import java.util.Arrays;
import java.util.List;

// https://en.wikipedia.org/wiki/Triangle
public class Triangle2d implements Polygon {
    @Formatted
    private final Point2d a, b, c;

    public Triangle2d(Point2d a, Point2d b, Point2d c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Point2d getA() {
        return this.a;
    }
    public Triangle2d setA(Point2d a) {
        return new Triangle2d(a, this.b, this.c);
    }
    public Point2d getB() {
        return this.b;
    }
    public Triangle2d setB(Point2d b) {
        return new Triangle2d(this.a, b, this.c);
    }
    public Point2d getC() {
        return this.c;
    }
    public Triangle2d setC(Point2d c) {
        return new Triangle2d(this.a, this.b, c);
    }

    public LineSegment2d ab() {
        return new LineSegment2d(this.a, this.b);
    }
    public Triangle2d setAB(LineSegment2d ab) {
        return new Triangle2d(ab.getStart(), ab.getEnd(), this.c);
    }
    public LineSegment2d bc() {
        return new LineSegment2d(this.b, this.c);
    }
    public Triangle2d setBC(LineSegment2d bc) {
        return new Triangle2d(this.a, bc.getStart(), bc.getEnd());
    }
    public LineSegment2d ca() {
        return new LineSegment2d(this.c, this.a);
    }
    public Triangle2d setCA(LineSegment2d ca) {
        return new Triangle2d(ca.getEnd(), this.b, ca.getStart());
    }

    public double a() {
        return bc().length();
    }
    public double b() {
        return ca().length();
    }
    public double c() {
        return ab().length();
    }
    public double perimeter() {
        return a() + b() + c();
    }

    // intersection of the perpendicular bisectors
    public Point2d circumcenter() {
        return Intersections2d.intersection(ab().perpendicularBisector(), bc().perpendicularBisector());
    }
    public Circle2d circumcircle() {
        Point2d center = circumcenter();
        return new Circle2d(center, center.distance(this.a));
    }
    // https://www.mathopenref.com/coordincenter.html
    public Point2d incenter() {
        double a = a(), b = b(), c = c();
        double p = a + b + c;
        double x = (a * this.a.getX() + b * this.b.getX() + c * this.c.getX()) / p;
        double y = (a * this.a.getY() + b * this.b.getY() + c * this.c.getY()) / p;
        return new Point2d(x, y);
    }
    public Circle2d incircle() {
        Point2d center = incenter();
        double a = a(), b = b(), c = c();
        // https://en.wikipedia.org/wiki/Incircle_and_excircles_of_a_triangle#Other_properties_2
        double radius = (a * b * c) / (2 * (a + b + c)) / circumcircle().getRadius();
        return new Circle2d(center, radius);
    }

    @Override
    public PointSet2d vertices() {
        return new PointSet2d(this.a, this.b, this.c);
    }
    @Override
    public List<LineSegment2d> sides() {
        return Arrays.asList(ab(), bc(), ca());
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
