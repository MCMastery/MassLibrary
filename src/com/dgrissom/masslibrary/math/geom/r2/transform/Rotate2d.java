package com.dgrissom.masslibrary.math.geom.r2.transform;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;

// clockwise
public class Rotate2d implements InvertibleTransform {
    @Formatted
    private final double radians;

    public Rotate2d(double radians) {
        this.radians = radians;
    }

    public double getRadians() {
        return this.radians;
    }

    @Override
    public Matrix matrix() {
        double cos = Math.cos(this.radians);
        double sin = Math.sin(this.radians);
        return Matrix.square(cos, sin,
                             -sin, cos);
    }
    @Override
    public Rotate2d inverse() {
        return new Rotate2d(Math.PI * 2 - this.radians);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }

    public double toDegrees() {
        return Math.toDegrees(this.radians);
    }
    public static Rotate2d fromDegrees(double degrees) {
        return new Rotate2d(Math.toRadians(degrees));
    }
}
