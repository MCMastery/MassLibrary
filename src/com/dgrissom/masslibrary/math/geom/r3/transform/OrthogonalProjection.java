package com.dgrissom.masslibrary.math.geom.r3.transform;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;

public class OrthogonalProjection implements Transform {
    @Override
    public Matrix matrix() {
        return Matrix.square(1, 0, 0, 0,
                             0, 1, 0, 0,
                             0, 0, 0, 0,
                             0, 0, 0, 1);
    }
}
