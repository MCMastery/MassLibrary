package com.dgrissom.masslibrary.math.statistics;

import com.dgrissom.masslibrary.math.functions.LinearFunction;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;

import java.util.Collection;

// https://en.wikipedia.org/wiki/Simple_linear_regression
public class SimpleLinearRegression {
    private final Collection<Point2d> data;

    public SimpleLinearRegression(Collection<Point2d> data) {
        this.data = data;
    }

    public Collection<Point2d> getData() {
        return this.data;
    }

    public LinearFunction calculate() {
        DataSet xSet = new DataSet();
        DataSet ySet = new DataSet();
        for (Point2d point : this.data) {
            xSet.add(point.getX());
            ySet.add(point.getY());
        }
        double covariance = xSet.covariance(ySet);
        double variance = xSet.sampleVariance();
        double slope = (covariance / variance);
        // y = b + mx
        // b = y - mx
        double yIntercept = ySet.mean() - slope * xSet.mean();
        return new LinearFunction(slope, yIntercept);
    }
}
