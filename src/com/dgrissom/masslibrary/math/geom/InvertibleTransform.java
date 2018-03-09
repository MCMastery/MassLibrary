package com.dgrissom.masslibrary.math.geom;

public interface InvertibleTransform extends Transform {
    InvertibleTransform inverse();
}
