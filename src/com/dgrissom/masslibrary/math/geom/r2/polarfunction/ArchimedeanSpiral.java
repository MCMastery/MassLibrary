package com.dgrissom.masslibrary.math.geom.r2.polarfunction;

// https://en.wikipedia.org/wiki/Polar_coordinate_system#Archimedean_spiral
public class ArchimedeanSpiral implements PolarFunction {
    private double a, armSpacing;

    public ArchimedeanSpiral(double a, double armSpacing) {
        this.a = a;
        this.armSpacing = armSpacing;
    }

    public double getA() {
        return this.a;
    }
    public void setA(double a) {
        this.a = a;
    }
    public double getArmSpacing() {
        return this.armSpacing;
    }
    public void setArmSpacing(double armSpacing) {
        this.armSpacing = armSpacing;
    }

    @Override
    public double magnitude(double angle) {
        return this.a + this.armSpacing * angle;
    }
}
