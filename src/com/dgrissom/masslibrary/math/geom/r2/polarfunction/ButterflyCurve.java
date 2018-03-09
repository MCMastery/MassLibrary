package com.dgrissom.masslibrary.math.geom.r2.polarfunction;

// https://en.wikipedia.org/wiki/Butterfly_curve_(transcendental)
public class ButterflyCurve implements PolarFunction {
    @Override
    public double magnitude(double angle) {
        return Math.pow(Math.E, Math.sin(angle)) - 2 * Math.cos(4 * angle) + Math.pow(Math.sin((2 * angle - Math.PI) / (24)), 5);
    }
}
