package com.dgrissom.masslibrary.math.functions;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

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
        return Formatter.format(this);
    }
}
