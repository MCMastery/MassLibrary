package com.dgrissom.masslibrary.math.geom.r2.transform;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;
import com.dgrissom.masslibrary.math.geom.r2.XY;

public class Translate2d implements InvertibleTransform, XY {
    @Formatted
    private final double tx, ty;

    public Translate2d(double tx, double ty) {
        this.tx = tx;
        this.ty = ty;
    }

    @Override
    public double getX() {
        return this.tx;
    }
    @Override
    public double getY() {
        return this.ty;
    }

    @Override
    public Matrix matrix() {
        return Matrix.square(1, 0, this.tx,
                             0, 1, this.ty,
                             0, 0, 1);
    }
    @Override
    public Translate2d inverse() {
        return new Translate2d(-this.tx, -this.ty);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
