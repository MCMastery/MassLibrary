package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;

import java.util.ArrayList;
import java.util.List;

// https://en.wikipedia.org/wiki/Star_polygon
public final class RegularStarPolygon implements Polygon {
    private final List<LineSegment2d> sides;

    private RegularStarPolygon(List<LineSegment2d> sides) {
        this.sides = sides;
    }

    @Override
    public List<LineSegment2d> sides() {
        return this.sides;
    }

    // https://upload.wikimedia.org/wikipedia/commons/7/76/Regular_star_polygons.svg
    // helps visualize this
    // circumradius = distance from center to vertices
    public static RegularStarPolygon fromSchlafliSymbol(SchlafliSymbol schlafliSymbol, Point2d center, double circumradius) {
        int vertexCount = schlafliSymbol.getP();
        int skip = schlafliSymbol.getS();
        if (skip < 2)
            skip = 1;

        RegularPolygon convexEquivalent = RegularPolygon.fromVertices(vertexCount, center, circumradius);
        PointSet2d vertices = convexEquivalent.vertices();
        List<LineSegment2d> sides = new ArrayList<>();

        // loop through each vertex, draw line from vertex to vertices(i+skip)
        for (int start = 0; start < vertexCount; start++) {
            // modulo so we wrap around
            int end = (start + skip) % vertexCount;
            sides.add(new LineSegment2d(vertices.get(start), vertices.get(end)));
        }
        return new RegularStarPolygon(sides);
    }
}
