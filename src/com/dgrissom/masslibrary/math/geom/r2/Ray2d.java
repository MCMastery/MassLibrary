package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

public final class Ray2d {
    @Formatted
    private final Point2d origin;
    @Formatted
    private final Vector2d direction;

    // please give us a normalized vector!
    public Ray2d(Point2d origin, Vector2d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Point2d getOrigin() {
        return this.origin;
    }
    public Ray2d setOrigin(Point2d origin) {
        return new Ray2d(origin, this.direction);
    }
    public Vector2d getDirection() {
        return this.direction;
    }
    // please give us a normalized vector!
    public Ray2d setDirection(Vector2d direction) {
        return new Ray2d(this.origin, direction);
    }

    // point along ray
    public Point2d point(double distance) {
        return this.direction.multiply(distance).toPoint().add(this.origin);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }

    // returns a ray starting at origin going in the direction of point
    public static Ray2d from(Point2d origin, Point2d point) {
        return new Ray2d(origin, Vector2d.direction(origin, point));
    }
}
