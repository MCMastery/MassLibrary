package com.dgrissom.masslibrary.math.geom.r3.transform;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;

public class Scale3d implements InvertibleTransform {
    @Formatted
    private final double sx, sy, sz;

    public Scale3d(double sx, double sy, double sz) {
        this.sx = sx;
        this.sy = sy;
        this.sz = sz;
    }

    public double getX() {
        return this.sx;
    }
    public double getY() {
        return this.sy;
    }
    public double getZ() {
        return this.sz;
    }

    @Override
    public Matrix matrix() {
        return Matrix.square(this.sx, 0, 0,
                             0, this.sy, 0,
                             0, 0, this.sz);
    }
    @Override
    public Scale3d inverse() {
        return new Scale3d(1 / this.sx, 1 / this.sy, 1 / this.sz);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
