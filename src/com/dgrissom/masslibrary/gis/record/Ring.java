package com.dgrissom.masslibrary.gis.record;

import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// a closed set of at least 4 points (first point must equal last point)
public class Ring implements Polygon {
    private final Point2d[] points;

    public Ring(Point2d[] points) {
        this.points = points;
    }

    public Point2d[] getPoints() {
        return this.points;
    }

    // first must = last
    public boolean isValid() {
        return this.points.length >= 4 && this.points[0].equals(this.points[this.points.length - 1]);
    }

    // remember, first point = last
    // so we don't have to "complete it" by connecting points[0] to points[points.length - 1]
    @Override
    public List<LineSegment2d> sides() {
        List<LineSegment2d> sides = new ArrayList<>();
        for (int i = 0; i < this.points.length - 1; i++)
            sides.add(new LineSegment2d(this.points[i], this.points[i + 1]));
        return sides;
    }

    @Override
    public String toString() {
        return "Ring" + Arrays.toString(this.points);
    }
}
