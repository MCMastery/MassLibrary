package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;

public interface RayTraceable {
    Material getMaterial();
    // return first intersection with ray
    // return null if ray never intersects us
    RayHit hit(Ray3d ray);
    Vector3d normal(Point3d point);
}
