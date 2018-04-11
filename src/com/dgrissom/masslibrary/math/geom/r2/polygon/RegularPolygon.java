package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.math.geom.r2.Circle2d;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PolarCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://en.wikipedia.org/wiki/Regular_polygon
public final class RegularPolygon implements Polygon {
    private final List<LineSegment2d> sides;

    private RegularPolygon(List<LineSegment2d> sides) {
        this.sides = Collections.unmodifiableList(sides);
    }

    @Override
    public List<LineSegment2d> sides() {
        return this.sides;
    }

    public double interiorAngle() {
        return interiorAngle(vertexCount());
    }
    public double exteriorAngle() {
        return exteriorAngle(vertexCount());
    }
    // just get first vertex (circumradius = distance from center to any vertex)
    public double circumradius() {
        return vertices().centroid().distance(this.sides.get(0).getStart());
    }
    // distance from center to midpoint on side
    public double apothem() {
        return vertices().centroid().distance(this.sides.get(0).midpoint());
    }
    public double sideLength() {
        return this.sides.get(0).length();
    }
    public double perimeter() {
        return sideLength() * vertexCount();
    }
    // A = 0.5nsa
    // also 0.5pa, 0.25ns^2 * cot(pi/n),
    // na^2 * tan(pi/2), 0.5 * nR^2 * sin((2pi)/n)
    // A = 0.5nsa would be fastest (no trig functions)
    @Override
    public double area() {
        return 0.5 * vertexCount() * sideLength() * apothem();
    }

    public Circle2d circumcircle() {
        return new Circle2d(vertices().centroid(), circumradius());
    }

    public SchlafliSymbol schlafliSymbol() {
        return new SchlafliSymbol(vertexCount());
    }

    // https://en.wikipedia.org/wiki/Regular_polygon#Angles
    private static double interiorAngle(int vertices) {
        return ((vertices - 2) * Math.PI) / vertices;
    }
    private static double exteriorAngle(int vertices) {
        return (2 * Math.PI) / vertices;
    }

    // circumradius = distance from center to vertices
    public static RegularPolygon fromVertices(int vertexCount, Point2d center, double circumradius) {
        double exteriorAngle = exteriorAngle(vertexCount);
        List<LineSegment2d> sides = new ArrayList<>();
        // regular polygons are always oriented so that the exact top is where a vertex is located
        // 270 degrees
        double angle = Math.PI * 1.5;
        Point2d first = null;
        Point2d last = null;
        for (int i = 0; i < vertexCount; i++) {
            PolarCoordinates polarCoordinates = new PolarCoordinates(center, circumradius, angle);
            Point2d vertex = polarCoordinates.toCartesian();
            if (last != null)
                sides.add(new LineSegment2d(last, vertex));
            if (i == 0)
                first = vertex;

            angle += exteriorAngle;
            last = vertex;
        }
        // draw last line from last vertex to first
        sides.add(new LineSegment2d(last, first));
        return new RegularPolygon(sides);
    }
}
