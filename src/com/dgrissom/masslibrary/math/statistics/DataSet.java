package com.dgrissom.masslibrary.math.statistics;

import java.util.ArrayList;
import java.util.Collection;

public class DataSet extends ArrayList<Double> {
    public DataSet(double... data) {
        super();
        for (double d : data)
            add(d);
    }
    public DataSet(Collection<Double> data) {
        super(data);
    }

    public double sum() {
        double sum = 0;
        for (double d : this)
            sum += d;
        return sum;
    }
    public DataSet cumulativeSum() {
        DataSet cumulative = new DataSet();
        double sum = 0;
        for (double d : this)
            cumulative.add(sum += d);
        return cumulative;
    }

    public double mean() {
        return sum() / size();
    }

    // https://en.wikipedia.org/wiki/Variance#Sample_variance
    public double populationVariance() {
        double sum = 0;
        double mean = mean();
        for (int i = 0; i < size(); i++)
            sum += Math.pow(get(i) - mean, 2);
        return (1.0 / size()) * sum;
    }
    public double sampleVariance() {
        return (size() / (size() - 1.0)) * populationVariance();
    }
    // https://en.wikipedia.org/wiki/Sample_mean_and_covariance#Sample_covariance
    public double covariance(DataSet other) {
        if (size() != other.size())
            throw new IllegalArgumentException("DataSets must be the same size");
        double sum = 0;
        double ourMean = mean(), theirMean = other.mean();
        for (int i = 0; i < size(); i++)
            sum += (get(i) - ourMean) * (other.get(i) - theirMean);
        return 1 / (size() - 1.0) * sum;
    }
}
