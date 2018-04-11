package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;

public class RayHit {
    private final RayTraceable object;
    private final Ray3d ray;
    private final Point3d point;

    public RayHit(RayTraceable object, Ray3d ray, Point3d point) {
        this.object = object;
        this.ray = ray;
        this.point = point;
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

    public Vector3d normal() {
        return this.object.normal(this.point);
    }
}
