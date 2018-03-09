package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

public class Sphere {
    @Formatted
    private final Point3d center;
    @Formatted
    private final double radius;

    public Sphere(Point3d center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3d getCenter() {
        return this.center;
    }
    public Sphere setCenter(Point3d center) {
        return new Sphere(center, this.radius);
    }
    public double getRadius() {
        return this.radius;
    }
    public Sphere setRadius(double radius) {
        return new Sphere(this.center, radius);
    }

    public Vector3d normal(Point3d point) {
        return Vector3d.direction(this.center, point);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
