package com.dgrissom.masslibrary.math.statistics;

import com.dgrissom.masslibrary.math.functions.LinearFunction;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;

// https://en.wikipedia.org/wiki/Simple_linear_regression
public class SimpleLinearRegression {
    private final PointSet2d points;

    public SimpleLinearRegression(PointSet2d points) {
        this.points = points;
    }

    public PointSet2d getPoints() {
        return this.points;
    }

    public LinearFunction calculate() {
        DataSet xSet = this.points.xSet();
        DataSet ySet = this.points.ySet();
        double covariance = this.points.covariance();
        double variance = xSet.sampleVariance();
        double slope = (covariance / variance);
        // y = b + mx
        // b = y - mx
        double yIntercept = ySet.mean() - slope * xSet.mean();
        return new LinearFunction(slope, yIntercept);
    }
}
