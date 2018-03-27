package com.dgrissom.masslibrary.math.geom.r3.transform;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;
import com.dgrissom.masslibrary.math.geom.r3.XYZ;

public class Translate3d implements InvertibleTransform, XYZ {
    @Formatted
    private final double tx, ty, tz;

    public Translate3d(double tx, double ty, double tz) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
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
    public double getZ() {
        return this.tz;
    }

    @Override
    public Matrix matrix() {
        return Matrix.square(1, 0, 0, this.tx,
                             0, 1, 0, this.ty,
                             0, 0, 1, this.tz,
                             0, 0, 0, 1);
    }
    @Override
    public Translate3d inverse() {
        return new Translate3d(-this.tx, -this.ty, -this.tz);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
