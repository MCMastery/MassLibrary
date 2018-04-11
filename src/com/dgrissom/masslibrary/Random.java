package com.dgrissom.masslibrary;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.color.HSVColor;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

public final class Random {
    private final static java.util.Random random = new java.util.Random();

    private Random() {}

    public static double random() {
        return Math.random();
    }
    // returns 0 <= d < max
    public static double random(double max) {
        return random() * max;
    }
    public static double random(double min, double max) {
        return random(max - min) + min;
    }

    public static int random(int max) {
        return random.nextInt(max);
    }
    public static int random(int min, int max) {
        return random(max - min) + max;
    }
    public static int randomInclusive(int min, int max) {
        return random(max - min + 1) + min;
    }

    public static Point2d point2d(double minX, double minY, double maxX, double maxY) {
        return new Point2d(random(minX, maxX), random(minY, maxY));
    }
    public static Point2d point2d(Point2d min, Point2d max) {
        return point2d(min.getX(), min.getY(), max.getX(), max.getY());
    }
    public static Point2d point2d(Rectangle2d bounds) {
        return point2d(bounds.left(), bounds.top(), bounds.right(), bounds.bottom());
    }

    public static PointSet2d pointSet2d(int points, double minX, double minY, double maxX, double maxY) {
        PointSet2d set = new PointSet2d();
        for (int i = 0; i < points; i++)
            set.add(point2d(minX, minY, maxX, maxY));
        return set;
    }
    public static PointSet2d pointSet2d(int points, Point2d min, Point2d max) {
        return pointSet2d(points, min.getX(), min.getY(), max.getX(), max.getY());
    }
    public static PointSet2d pointSet2d(int points, Rectangle2d bounds) {
        return pointSet2d(points, bounds.left(), bounds.top(), bounds.right(), bounds.bottom());
    }

    public static RGBColor rgb() {
        return new RGBColor(random(), random(), random());
    }
    public static HSVColor hsv() {
        return new HSVColor(random(0, 360.0), random(), random());
    }
}
