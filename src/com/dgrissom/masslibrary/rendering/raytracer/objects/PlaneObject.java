package com.dgrissom.masslibrary.rendering.raytracer.objects;

import com.dgrissom.masslibrary.math.geom.r3.*;
import com.dgrissom.masslibrary.rendering.raytracer.Material;
import com.dgrissom.masslibrary.rendering.raytracer.RayTraceable;

public class PlaneObject extends Plane implements RayTraceable {
    private final Material material;

    public PlaneObject(Point3d point, Vector3d normal, Material material) {
        super(point, normal);
        this.material = material;
    }

    // https://en.wikipedia.org/wiki/Line–plane_intersection
    @Override
    public Point3d intersection(Ray3d ray) {
        Point3d p0 = getPoint();
        Point3d l0 = ray.getOrigin();
        Vector3d n = getNormal();
        Vector3d l = ray.getDirection();

        return null;
    }
    @Override
    public Vector3d normal(Point3d point) {
        return getNormal();
    }
    @Override
    public Material getMaterial() {
        return this.material;
    }
}
