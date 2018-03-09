package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.math.geom.r2.Intersections2d;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;

import java.util.ArrayList;
import java.util.List;

// https://en.wikipedia.org/wiki/Boolean_operations_on_polygons
public final class PolygonBooleanOperations {
    private PolygonBooleanOperations() {}

    private static List<LineSegment2d> subdivide(Polygon a, Polygon b) {
        List<LineSegment2d> subdividedSides = new ArrayList<>();
        for (LineSegment2d side : a.getSides()) {
            List<Point2d> points = new ArrayList<>();
            points.add(side.getStart());
            points.addAll(Intersections2d.intersections(side, b));
            // sort intersections so closest to start are first
            points.sort((o1, o2) -> (int) (o1.distanceSquared(side.getStart()) - o2.distanceSquared(side.getStart())));
            points.add(side.getEnd());

            for (int i = 0; i < points.size() - 1; i++)
                subdividedSides.add(new LineSegment2d(points.get(i), points.get(i + 1)));
        }
        return subdividedSides;
    }

    // area that both a and b contain
    public static Polygon and(Polygon a, Polygon b) {
        // subdivide sides so that when a side intersects the other polygon's side, the side is split up into different parts
        // relating to the intersection points
        List<LineSegment2d> aSubdividedSides = subdivide(a, b);
        List<LineSegment2d> bSubdividedSides = subdivide(b, a);

        List<LineSegment2d> sides = new ArrayList<>();
        Rectangle2d bBounds = b.boundingBox();
        Point2d bCentroid = b.centroid();
        for (LineSegment2d side : aSubdividedSides)
            if (Intersections2d.contains(b, side, bBounds, bCentroid, true, 0.001))
                sides.add(side);
        Rectangle2d aBounds = a.boundingBox();
        Point2d aCentroid = a.centroid();
        for (LineSegment2d side : bSubdividedSides)
            if (Intersections2d.contains(a, side, aBounds, aCentroid, true, 0.001))
                sides.add(side);

        return Polygon.from(sides);
    }
}
