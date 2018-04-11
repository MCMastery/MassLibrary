package com.dgrissom.masslibrary.math.geom.r3.transform;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;

// https://en.wikipedia.org/wiki/Euler_angles#Rotation_matrix
public class TaitBryanAngles implements Transform {
    // different people use different conventions for the multiplication order
    public enum MultiplicationOrder {
        XZY {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c2*c3, -s2, c2*s3, 0,
                                     s1*s3+c1*c3*s2, c1*c2, c1*s2*s3-c3*s1, 0,
                                     c3*s1*s2-c1*s3, c2*s1, c1*c3+s1*s2*s3, 0,
                                     0, 0, 0, 1);
            }
        },
        XYZ {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c2*c3, -c2*s3, s2, 0,
                                     c1*s3+c3*s1*s2, c1*c3-s1*s2*s3, -c2*s1, 0,
                                     s1*s3-c1*c3*s2, c3*s1+c1*s2*s3, c1*c2, 0,
                                     0, 0, 0, 1);
            }
        },
        YXZ {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c3+s1*s2*s3, c3*s1*s2-c1*s3, c2*s1, 0,
                                     c2*s3, c2*s3, -s2, 0,
                                     c1*s2*s3-c3*s1, c1*c3*s2+s1*s3, c1*c2, 0,
                                     0, 0, 0, 1);
            }
        },
        YZX {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c2, s1*s3-c1*c3*s2, c3*s1+c1*s2*s3, 0,
                                     s2, c2*c3, -c2*s3, 0,
                                     -c2*s1, c1*s3+c3*s1*s2, c1*c3-s1*s2*s3, 0,
                                     0, 0, 0, 1);
            }
        },
        ZYX {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c2, c1*c2*c3-c3*s1, s1*s3+c1*c3*s2, 0,
                                     c2*s1, c1*c3+s1*s2*s3, c3*s1*s2-c1*s3, 0,
                                     -s2, c2*s3, c2*c3, 0,
                                     0, 0, 0, 1);
            }
        },
        // most common
        ZXY {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c3-s1*s2*s3, -c2*s1, c1*s3+c3*s1*s2, 0,
                                     c3*s1+c1*s2*s3, c1*c2, s1*s3-c1*c3*s2, 0,
                                     -c2*s3, s2, c2*c3, 0,
                                     0, 0, 0, 1);
            }
        };

        // sin / cos of components alpha, beta, gamma
        public abstract Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3);
    }

    private final MultiplicationOrder order;
    private final double alpha, beta, gamma;

    public TaitBryanAngles(double alpha, double beta, double gamma) {
        this(alpha, beta, gamma, MultiplicationOrder.ZXY);
    }
    public TaitBryanAngles(double alpha, double beta, double gamma, MultiplicationOrder order) {
        this.order = order;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    public MultiplicationOrder getOrder() {
        return this.order;
    }
    public double getAlpha() {
        return this.alpha;
    }
    public double getBeta() {
        return this.beta;
    }
    public double getGamma() {
        return this.gamma;
    }

    @Override
    public Matrix matrix() {
        return this.order.matrix(Math.sin(this.alpha), Math.sin(this.beta), Math.sin(this.gamma),
                Math.cos(this.alpha), Math.cos(this.beta), Math.cos(this.gamma));
    }
}
