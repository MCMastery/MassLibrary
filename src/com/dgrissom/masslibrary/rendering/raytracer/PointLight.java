package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;

public class PointLight {
    private final Point3d position;
    private final double specular, diffuse;

    public PointLight(Point3d position, double specular, double diffuse) {
        this.position = position;
        this.specular = specular;
        this.diffuse = diffuse;
    }

    public Point3d getPosition() {
        return this.position;
    }
    public double getSpecular() {
        return this.specular;
    }
    public double getDiffuse() {
        return this.diffuse;
    }
}
