package com.dgrissom.masslibrary.rendering.raytracer;

public class Material {
    private final double specular, diffuse, ambient, shininess;

    public Material(double specular, double diffuse, double ambient, double shininess) {
        this.specular = specular;
        this.diffuse = diffuse;
        this.ambient = ambient;
        this.shininess = shininess;
    }

    public double getSpecular() {
        return this.specular;
    }
    public double getDiffuse() {
        return this.diffuse;
    }
    public double getAmbient() {
        return this.ambient;
    }
    public double getShininess() {
        return this.shininess;
    }
}
