package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.NumberTheory;
import com.dgrissom.masslibrary.math.functions.LinearFunction;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

public final class Intersections2d {
    private Intersections2d() {}

    public static boolean contains(LineSegment2d l, Point2d p, double error) {
        // distance(start,point) + distance(point,end) = length
        return Math.abs(l.getStart().distance(p) + p.distance(l.getEnd()) - l.length()) < error;
    }

    public static boolean contains(Circle2d c, Point2d p) {
        return p.distanceSquared(c.getCenter()) < c.getRadius() * c.getRadius();
    }
    public static boolean contains(Rectangle2d r, Point2d p) {
        return p.getX() >= r.left() && p.getY() >= r.top()
                && p.getX() < r.right() && p.getY() < r.bottom();
    }

    // polygon must be simple
    // https://en.wikipedia.org/wiki/Point_in_polygon#Ray_casting_algorithm
    // line from point to centroid, extending far out, past the boundary of the polygon
    // if # of intersections of line with polygon sides is odd, the polygon contains the point
    public static boolean contains(Polygon polygon, Point2d point, Rectangle2d polygonBoundingBox, Point2d polygonCentroid,
                                   boolean trueOnEdge, double error) {
        if (!contains(polygonBoundingBox, point))
            return false;

        // add a bit to the point so the line going through the centroid won't hit a vertex exactly
        // (this would not return an intersection)
        point = point.add(10e-8);

        int intersections = 0;
        // line from point to centroid
        LineSegment2d line = new LineSegment2d(point, polygonCentroid);
        // now extend so that the end of the line is outside the polygon
        line = line.extend(polygonBoundingBox.diagonal().length() * 8);
        for (LineSegment2d side : polygon.sides()) {
            if (contains(side, point, error))
                return trueOnEdge;
            if (intersects(line, side))
                intersections++;
        }
        return NumberTheory.isOdd(intersections);
    }

    // only works for convex polygons
    public static boolean contains(Polygon polygon, LineSegment2d line, Rectangle2d polygonBoundingBox, Point2d polygonCentroid,
                                   boolean trueOnEdge, double error) {
        return contains(polygon, line.getStart(), polygonBoundingBox, polygonCentroid, trueOnEdge, error)
                && contains(polygon, line.getEnd(), polygonBoundingBox, polygonCentroid, trueOnEdge, error);
    }

    // does not return anything if one polygon contains the other
    public static PointSet2d intersections(LineSegment2d l, Polygon p) {
        PointSet2d intersections = new PointSet2d();
        for (LineSegment2d side : p.sides()) {
            Point2d intersection = intersection(l, side);
            if (intersection != null)
                intersections.add(intersection);
        }
        return intersections;
    }
    public static PointSet2d intersections(Polygon p1, Polygon p2) {
        PointSet2d intersections = new PointSet2d();
        for (LineSegment2d s1 : p1.sides())
            intersections.addAll(intersections(s1, p2));
        return intersections;
    }

    //todo faster way of doing this
    // maybe first check if bounding boxes intersect?
    public static boolean intersects(LineSegment2d l1, LineSegment2d l2) {
        return intersection(l1, l2) != null;
    }
    public static boolean intersects(LineSegment2d l, Polygon p) {
        for (LineSegment2d side : p.sides())
            if (intersection(l, side) != null)
                return true;
        return false;
    }
    // https://en.wikipedia.org/wiki/Line–line_intersection
    // throws IllegalArgumentException if lines overlap or if they are parallel
    public static Point2d intersection(LineSegment2d l1, LineSegment2d l2) {
        // parallel or overlapping?
        if (l1.parallel(l2))
            throw new IllegalArgumentException("lines are parallel");
        else if (l1.overlaps(l2, 10e-5))
            throw new IllegalArgumentException("lines overlap");

        double x1 = l1.getX1(), y1 = l1.getY1(), x2 = l1.getX2(), y2 = l1.getY2();
        double x3 = l2.getX1(), y3 = l2.getY1(), x4 = l2.getX2(), y4 = l2.getY2();

        Matrix m1 = Matrix.square(x1, y1, x2, y2);
        Matrix m2 = Matrix.square(x1, 1, x2, 1);
        Matrix m3 = Matrix.square(x3, y3, x4, y4);
        Matrix m4 = Matrix.square(x3, 1, x4, 1);
        Matrix m5 = Matrix.square(y1, 1, y2, 1);
        Matrix m6 = Matrix.square(y3, 1, y4, 1);
        double m1d = m1.determinant();
        double m2d = m2.determinant();
        double m3d = m3.determinant();
        double m4d = m4.determinant();
        double m5d = m5.determinant();
        double m6d = m6.determinant();

        double xNumerator = Matrix.square(m1d, m2d, m3d, m4d).determinant();
        // denominator is the same for both
        double denominator = Matrix.square(m2d, m5d, m4d, m6d).determinant();
        double yNumerator = Matrix.square(m1d, m5d, m3d, m6d).determinant();

        Point2d point = new Point2d(xNumerator / denominator, yNumerator / denominator);
        // at this point, we've been assuming lines are infinite
        // now we have to make sure point is actually on both lines
        if (contains(l1, point, 10e-10) && contains(l2, point, 10e-10))
            return point;
        return null;
    }

    // https://en.wikipedia.org/wiki/Line–line_intersection
    public static Point2d intersection(LinearFunction l1, LinearFunction l2) {
        double a = l1.getSlope(), b = l2.getSlope();
        double c = l1.getYIntercept(), d = l2.getYIntercept();
        return new Point2d((d - c) / (a - b), (a * d - b * c) / (a - b));
    }
}
