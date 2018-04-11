package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Random;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.math.statistics.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PointSet2d extends ArrayList<Point2d> {
    public PointSet2d(Point2d... points) {
        this(Arrays.asList(points));
    }
    public PointSet2d(Collection<Point2d> points) {
        super(points);
    }

    public DataSet xSet() {
        DataSet xSet = new DataSet();
        for (Point2d p : this)
            xSet.add(p.getX());
        return xSet;
    }
    public DataSet ySet() {
        DataSet ySet = new DataSet();
        for (Point2d p : this)
            ySet.add(p.getY());
        return ySet;
    }

    public double covariance() {
        return xSet().covariance(ySet());
    }

    public Point2d centroid() {
        return new Point2d(xSet().mean(), ySet().mean());
    }

    public Point2d random() {
        return get(Random.random(size()));
    }

    public Point2d farthest(Point2d p) {
        Point2d farthest = null;
        double farthestDistSq = 0;
        for (Point2d p2 : this) {
            if (p2.equals(p))
                continue;

            double distSq = p2.distanceSquared(p);
            if (farthest == null || distSq > farthestDistSq) {
                farthest = p2;
                farthestDistSq = distSq;
            }
        }
        return farthest;
    }
    public Point2d closest(Point2d p) {
        Point2d closest = null;
        double closestDistSq = 0;
        for (Point2d p2 : this) {
            if (p2.equals(p))
                continue;

            double distSq = p2.distanceSquared(p);
            if (closest == null || distSq < closestDistSq) {
                closest = p2;
                closestDistSq = distSq;
            }
        }
        return closest;
    }

    public Rectangle2d boundingBox() {
        DataSet xSet = xSet();
        DataSet ySet = ySet();
        return Rectangle2d.from(xSet.min(), ySet.min(), xSet.max(), ySet.max());
    }

    // returns sides from vertices
    // same order as vertices list is given in
    public Polygon toPolygon() {
        List<LineSegment2d> sides = new ArrayList<>();
        Point2d last = null;
        // loop around to first
        for (int i = 0; i <= size(); i++) {
            // loop around to first
            int index = i;
            if (index == size())
                index = 0;

            Point2d vertex = get(index);
            if (last != null)
                sides.add(new LineSegment2d(last, vertex));
            last = vertex;
        }
        return Polygon.from(sides);
    }

    // https://en.wikipedia.org/wiki/Gift_wrapping_algorithm
    // a convex hull is the polygon created by some (or all, if small amount) of the points which contains all the points
    public Polygon convexHull() {
        Point2d pointOnHull = null;
        for (Point2d p : this)
            if (pointOnHull == null || p.getX() < pointOnHull.getX())
                pointOnHull = p;

        int i = 0;
        PointSet2d convexHull = new PointSet2d();
        Point2d endPoint = null;
        while (convexHull.size() == 0 || endPoint != convexHull.get(0)) {
            convexHull.add(pointOnHull);
            endPoint = get(0);
            for (int j = 1; j < size(); j++) {
                LineSegment2d l = new LineSegment2d(convexHull.get(i), endPoint);
                boolean toLeftOfLine = (get(j).getX() - l.getX1()) * (l.getY2() - l.getY1())
                        - (get(j).getY() - l.getY1()) * (l.getX2() - l.getX1()) < 0;
                if (endPoint == pointOnHull || toLeftOfLine)
                    endPoint = get(j);
            }
            i++;
            pointOnHull = endPoint;
        }
        return convexHull.toPolygon();
    }
}
