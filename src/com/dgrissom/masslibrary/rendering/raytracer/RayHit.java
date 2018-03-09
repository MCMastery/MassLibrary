package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;

public class RayHit {
    @Formatted
    private final RayTraceable object;
    @Formatted
    private final Ray3d ray;
    @Formatted
    private final Point3d point;
    @Formatted
    private final Vector3d normal;

    public RayHit(RayTraceable object, Ray3d ray, Point3d point, Vector3d normal) {
        this.object = object;
        this.ray = ray;
        this.point = point;
        this.normal = normal;
    }

    public RayTraceable getObject() {
        return this.object;
    }
    public Ray3d getRay() {
        return this.ray;
    }
    public Point3d getPoint() {
        return this.point;
    }
    public Vector3d getNormal() {
        return this.normal;
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
