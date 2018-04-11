package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.Random;
import com.dgrissom.masslibrary.math.geom.r2.Intersections2d;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;
import com.dgrissom.masslibrary.rendering.Image;

import java.util.ArrayList;
import java.util.List;

// https://en.wikipedia.org/wiki/Boolean_operations_on_polygons
public final class PolygonBooleanOperations {
    private PolygonBooleanOperations() {}

    // subdivides a so that intersections with b form new line segments
    private static Polygon subdivide(Polygon a, Polygon b) {
        List<LineSegment2d> subdividedSides = new ArrayList<>();
        for (LineSegment2d s : a.sides()) {
            List<Point2d> intersections = Intersections2d.intersections(s, b);
            if (intersections.size() == 0) {
                subdividedSides.add(s);
                continue;
            }

            Point2d start = s.getStart();
            for (Point2d p : intersections) {
                subdividedSides.add(new LineSegment2d(start, p));
                start = p;
            }
            subdividedSides.add(new LineSegment2d(start, s.getEnd()));
        }
        return Polygon.from(subdividedSides);
    }

    // area that both a and b contain
    public static Polygon and(Polygon a, Polygon b, Image image) {
        Polygon aSubdivided = subdivide(a, b);
        Polygon bSubdivided = subdivide(b, a);


        Rectangle2d bBounds = b.vertices().boundingBox();
        Point2d bCentroid = b.vertices().centroid();
        Rectangle2d aBounds = a.vertices().boundingBox();
        Point2d aCentroid = a.vertices().centroid();

        //todo instead of having to also do convexHull and just getting edge starts, actually get edges directly
        //todo the reason we don't add edges directly is because they often are not in the correct order, resulting in
        //todo a mess
        // now get all edges that both polygons contain
        PointSet2d and = new PointSet2d();
        for (LineSegment2d s : aSubdivided.sides()) {
            image.draw(s, Random.rgb());
            if (Intersections2d.contains(b, s, bBounds, bCentroid, true, 10e-2))
                and.add(s.getStart());
        }

        for (LineSegment2d s : bSubdivided.sides()) {
            image.draw(s, Random.rgb());
            if (Intersections2d.contains(a, s, aBounds, aCentroid, true, 10e-2))
                and.add(s.getStart());
        }
        return and.convexHull();
    }
}
