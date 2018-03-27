package com.dgrissom.masslibrary.math;

public final class GeneralMath {
    private GeneralMath() {}

    public static boolean isInt(double d) {
        return d == Math.round(d);
    }

    public static double clamp(double value, double min, double max) {
        return (value < min) ? min : (value > max) ? max : value;
    }

    public static double min(double... values) {
        double min = 0;
        for (int i = 0; i < values.length; i++)
            if (i == 0 || values[i] < min)
                min = values[i];
        return min;
    }
    public static double max(double... values) {
        double max = 0;
        for (int i = 0; i < values.length; i++)
            if (i == 0 || values[i] > max)
                max = values[i];
        return max;
    }
}
