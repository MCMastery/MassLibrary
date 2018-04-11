package com.dgrissom.masslibrary.math.functions;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;

public class LinearFunction implements ExplicitFunction {
    @Formatted
    private final double slope, yIntercept;

    public LinearFunction(double slope, double yIntercept) {
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public double getSlope() {
        return this.slope;
    }
    public double getYIntercept() {
        return this.yIntercept;
    }

    public double y(double x) {
        return this.yIntercept + this.slope * x;
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }

    // returns the linear function going a given point with the given slope
    // y-y1 = m(x-x1)
    // y = m(x-x1) + y1
    // y = mx-mx1 + y1
    // y = mx + (y1-mx1)
    public static LinearFunction from(Point2d p, double slope) {
        double b = p.getY() - slope * p.getX();
        return new LinearFunction(slope, b);
    }
    // returns the linear function going through two points
    // m = (y2-y1) / (x2-x1)
    public static LinearFunction from(Point2d p1, Point2d p2) {
        double m = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        return from(p1, m);
    }
}
