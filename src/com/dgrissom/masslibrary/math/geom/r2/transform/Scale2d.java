package com.dgrissom.masslibrary.math.geom.r2.transform;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;

public final class Scale2d implements InvertibleTransform {
    @Formatted
    private final double sx, sy;

    public Scale2d(double sx, double sy) {
        this.sx = sx;
        this.sy = sy;
    }

    public double getScaleX() {
        return this.sx;
    }
    public double getScaleY() {
        return this.sy;
    }

    @Override
    public Matrix matrix() {
        return Matrix.square(this.sx, 0,
                             0, this.sy);
    }
    @Override
    public Scale2d inverse() {
        return new Scale2d(1 / this.sx, 1 / this.sy);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
