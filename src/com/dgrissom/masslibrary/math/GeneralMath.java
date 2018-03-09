package com.dgrissom.masslibrary.math;

public final class GeneralMath {
    private GeneralMath() {}

    public static boolean isInt(double d) {
        return d == Math.round(d);
    }

    public static double clamp(double value, double min, double max) {
        return (value < min) ? min : (value > max) ? max : value;
    }
}
