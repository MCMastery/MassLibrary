package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;

public interface RayTraceable {
    // return null if ray never hits us
    Point3d intersection(Ray3d ray);
    Vector3d normal(Point3d point);
    // returns null if ray never hits us
    default RayHit hit(Ray3d ray) {
        Point3d point = intersection(ray);
        if (point == null)
            return null;
        return new RayHit(this, ray, point, normal(point));
    }
    Material getMaterial();
}
