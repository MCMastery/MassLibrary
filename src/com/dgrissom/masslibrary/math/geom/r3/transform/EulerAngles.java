package com.dgrissom.masslibrary.math.geom.r3.transform;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;

// https://en.wikipedia.org/wiki/Euler_angles#Rotation_matrix
public class EulerAngles implements Transform {
    // different people use different conventions for the multiplication order
    public enum MultiplicationOrder {
        XZX {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c2, -c3*s2, s2*s3,
                                     c1*s2, c1*c2*c3-s1*s3, -c3*s1-c1*c2*s3,
                                     s1*s2, c1*s3+c2*c3*s1, c1*c3-c2*s1*s3);
            }
        },
        XYX {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c2, s2*s3, c3*s2,
                                     s1*s2, c1*c3-c2*s1*s3, -c1*s3-c2*c3*s1,
                                     -c1*s2, c3*s1+c1*c2*s3, c1*c2*c3-s1*s3);
            }
        },
        YXY {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c3-c2*s1*s3, s1*s2, c1*s3+c2*c3*s1,
                                     s2*s3, c2, -c3*s2,
                                     -c3*s1-c1*c2*s3, c1*s2, c1*c2*c3-s1*s3);
            }
        },
        YZY {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c2*c3-s1*s3, c1*s2, c3*s1+c1*c2*c3,
                                     c3*s2, c2, s2*s3,
                                     -c1*c3-c2*c3*s1, s1*s2, c1*c3-c2*s1*s3);
            }
        },
        ZYZ {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c2*c3-s1*s3, -c3*s1-c1*c2*c3, c1*s2,
                                     c1*s3+c2*c3*s1, c1*c3-c2*s1*s3, s1*s2,
                                     -c3*s2, s2*s3, c2);
            }
        },
        // most common
        ZXZ {
            @Override
            public Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3) {
                return Matrix.square(c1*c3-c2*s1*s3, -c1*s3-c2*c3*s1, s1*s2,
                                     c3*s1+c1*c2*s3, c1*c2*c3-s1*s3, -c1*s2,
                                     s2*s3, c3*s2, c2);
            }
        };

        // sin / cos of components alpha, beta, gamma
        public abstract Matrix matrix(double s1, double s2, double s3, double c1, double c2, double c3);
    }

    private final MultiplicationOrder order;
    private final double alpha, beta, gamma;

    public EulerAngles(double alpha, double beta, double gamma) {
        this(MultiplicationOrder.ZXZ, alpha, beta, gamma);
    }
    public EulerAngles(MultiplicationOrder order, double alpha, double beta, double gamma) {
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
