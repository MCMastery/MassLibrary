package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

public final class Ray3d {
    @Formatted
    private final Point3d origin;
    @Formatted
    private final Vector3d direction;

    // please give us a normalized vector!
    public Ray3d(Point3d origin, Vector3d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Point3d getOrigin() {
        return this.origin;
    }
    public Ray3d setOrigin(Point3d origin) {
        return new Ray3d(origin, this.direction);
    }
    public Vector3d getDirection() {
        return this.direction;
    }
    // please give us a normalized vector!
    public Ray3d setDirection(Vector3d direction) {
        return new Ray3d(this.origin, direction);
    }

    // point along ray
    public Point3d point(double distance) {
        return this.direction.multiply(distance).toPoint().add(this.origin);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }

    // returns a ray starting at origin going in the direction of point
    public static Ray3d from(Point3d origin, Point3d point) {
        return new Ray3d(origin, Vector3d.direction(origin, point));
    }
}
