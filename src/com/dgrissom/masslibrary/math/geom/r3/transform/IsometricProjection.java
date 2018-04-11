package com.dgrissom.masslibrary.math.geom.r3.transform;

import com.dgrissom.masslibrary.math.geom.CompositeTransform;

// https://en.wikipedia.org/wiki/Isometric_projection
public class IsometricProjection extends CompositeTransform {
    private static final double rotationAlpha = Math.asin(Math.toRadians(30));
    private static final double rotationBeta = Math.toRadians(45);

    public IsometricProjection() {
        super(new TaitBryanAngles(rotationAlpha, rotationBeta, 0, TaitBryanAngles.MultiplicationOrder.XYZ),
                new OrthogonalProjection());
    }
}
