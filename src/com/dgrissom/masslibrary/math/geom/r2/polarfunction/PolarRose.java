package com.dgrissom.masslibrary.math.geom.r2.polarfunction;

// https://en.wikipedia.org/wiki/Polar_coordinate_system#Polar_rose
public class PolarRose implements PolarFunction {
    private final double petalLength, k, gamma;

    public PolarRose(double petalLength, double k, double gamma) {
        this.petalLength = petalLength;
        this.k = k;
        this.gamma = gamma;
    }

    public double getPetalLength() {
        return this.petalLength;
    }
    public double getK() {
        return this.k;
    }
    public double getGamma() {
        return this.gamma;
    }

    @Override
    public double magnitude(double angle) {
        return this.petalLength * Math.cos(this.k * angle + this.gamma);
    }
}
