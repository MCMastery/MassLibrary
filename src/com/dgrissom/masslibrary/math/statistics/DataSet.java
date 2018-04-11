package com.dgrissom.masslibrary.math.statistics;

import com.dgrissom.masslibrary.math.NumberTheory;

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

    public DataSet subset(int start, int end) {
        return new DataSet(super.subList(start, end));
    }

    public void sort() {
        sort((o1, o2) -> (int) (o1 - o2));
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

    public double min() {
        double min = 0;
        for (int i = 0; i < size(); i++)
            if (i == 0 || get(i) < min)
                min = get(i);
        return min;
    }
    public double max() {
        double max = 0;
        for (int i = 0; i < size(); i++)
            if (i == 0 || get(i) > max)
                max = get(i);
        return max;
    }

    public double mean() {
        return sum() / size();
    }
    public double median() {
        DataSet clone = clone();
        clone.sort();
        if (NumberTheory.isEven(clone.size())) {
            // avg of the two middle items
            // + 1 since end index is exclusive
            return clone.subset(clone.size() / 2 - 1, clone.size() / 2 + 1).mean();
        }
        return clone.get(clone.size() / 2);
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

    @Override
    public DataSet clone() {
        return new DataSet((ArrayList<Double>) super.clone());
    }
}
