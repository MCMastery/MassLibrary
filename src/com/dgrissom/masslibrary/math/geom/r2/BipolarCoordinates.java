package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

// https://en.wikipedia.org/wiki/Bipolar_coordinates
public final class BipolarCoordinates {
    // foci located at (-focusMagnitude, 0), (focusMagnitude, 0)
    @Formatted
    private final double sigma, tau, focusMagnitude;

    public BipolarCoordinates(double sigma, double tau, double focusMagnitude) {
        this.sigma = sigma;
        this.tau = tau;
        this.focusMagnitude = focusMagnitude;
    }

    public Point2d toCartesian() {
        double denominator = Math.cosh(this.tau) - Math.cos(this.sigma);
        double x = this.focusMagnitude * (Math.sinh(this.tau) / denominator);
        double y = this.focusMagnitude * (Math.sinh(this.sigma) / denominator);
        return new Point2d(x, y);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
