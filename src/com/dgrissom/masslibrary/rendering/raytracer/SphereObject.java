package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.math.geom.r3.Sphere;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;

public class SphereObject extends Sphere implements RayTraceable {
    private Material material;

    public SphereObject(Point3d center, double radius, Material material) {
        super(center, radius);
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    // https://en.wikipedia.org/wiki/Line–sphere_intersection
    @Override
    public RayHit hit(Ray3d ray) {
        Point3d c = getCenter();
        double r = getRadius();
        Vector3d l = ray.getDirection();
        Point3d o = ray.getOrigin();
        double dotProd = l.dotProduct(o.subtract(c));
        double addend = -dotProd;
        double discriminant = dotProd * dotProd - Vector3d.from(o.subtract(c)).magnitudeSquared() + r * r;

        // no solution
        if (discriminant < 0)
            return null;

        double sqrt = Math.sqrt(discriminant);
        double d1 = addend + sqrt;
        double d2 = addend - sqrt;
        double d = Math.min(d1, d2);
        return new RayHit(this, ray, ray.point(d));
    }
}
