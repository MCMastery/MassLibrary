package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

public class Plane {
    @Formatted
    private final Point3d point;
    @Formatted
    private final Vector3d normal;

    public Plane(Point3d point, Vector3d normal) {
        this.point = point;
        this.normal = normal;
    }

    public Point3d getPoint() {
        return this.point;
    }
    public Plane setPoint(Point3d point) {
        return new Plane(point, this.normal);
    }
    public Vector3d getNormal() {
        return this.normal;
    }
    public Plane setNormal(Vector3d normal) {
        return new Plane(this.point, normal);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
