package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.rendering.color.RGBColor;

public class Material {
    private final RGBColor ambient;
    private final double specularReflectionRatio, diffuseReflectionRatio, shininess;

    public Material(RGBColor ambient, double specularReflectionRatio, double diffuseReflectionRatio, double shininess) {
        this.ambient = ambient;
        this.specularReflectionRatio = specularReflectionRatio;
        this.diffuseReflectionRatio = diffuseReflectionRatio;
        this.shininess = shininess;

    }

    public RGBColor getAmbient() {
        return this.ambient;
    }
    public double getSpecularReflectionRatio() {
        return this.specularReflectionRatio;
    }
    public double getDiffuseReflectionRatio() {
        return this.diffuseReflectionRatio;
    }
    public double getShininess() {
        return this.shininess;
    }
}
