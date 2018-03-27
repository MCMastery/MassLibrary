package com.dgrissom.masslibrary.rendering.color;

// a color space with defined gamma, so the compand function is simply gamma companding
// http://www.brucelindbloom.com/Eqn_XYZ_to_RGB.html
public class SimpleColorSpace extends ColorSpace {
    private final double gamma;

    public SimpleColorSpace(XYYColor whitepoint, XYYColor r, XYYColor g, XYYColor b, double gamma) {
        super(whitepoint, r, g, b);
        this.gamma = gamma;
    }

    public double getGamma() {
        return this.gamma;
    }

    @Override
    public double compand(double v) {
        return Math.pow(v, 1 / this.gamma);
    }
    @Override
    public double inverseCompand(double v) {
        return Math.pow(v, this.gamma);
    }
}
