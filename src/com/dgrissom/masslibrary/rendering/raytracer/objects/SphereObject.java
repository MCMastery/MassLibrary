package com.dgrissom.masslibrary.rendering.raytracer.objects;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.math.geom.r3.Sphere;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;
import com.dgrissom.masslibrary.rendering.raytracer.Material;
import com.dgrissom.masslibrary.rendering.raytracer.RayTraceable;

public class SphereObject extends Sphere implements RayTraceable {
    private final Material material;

    public SphereObject(Point3d center, double radius, Material material) {
        super(center, radius);
        this.material = material;
    }

    // https://en.wikipedia.org/wiki/Line–sphere_intersection
    @Override
    public Point3d intersection(Ray3d ray) {
        Point3d c = getCenter();
        double r = getRadius();
        Vector3d l = ray.getDirection();
        Point3d o = ray.getOrigin();

        Vector3d oMinusC = Vector3d.from(o.subtract(c));
        double dotProd = l.dotProduct(oMinusC);
        double discriminant = dotProd * dotProd - oMinusC.magnitudeSquared() + r * r;
        if (discriminant < 0)
            return null;

        double sqrt = Math.sqrt(discriminant);
        double d1 = -dotProd + sqrt;
        double d2 = -dotProd - sqrt;

        // return closest point
        double d = (d1 < d2) ? d1 : d2;
        return ray.point(d);
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }
}
