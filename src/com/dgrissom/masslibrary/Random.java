package com.dgrissom.masslibrary;

import com.dgrissom.masslibrary.rendering.color.RGBColor;

public final class Random {
    private final static java.util.Random random = new java.util.Random();

    private Random() {}

    public static double random() {
        return Math.random();
    }
    // returns 0 <= d < max
    public static double random(double max) {
        return random() * max;
    }
    public static double random(double min, double max) {
        return random(max - min) + min;
    }

    public static int random(int max) {
        return random.nextInt(max);
    }
    public static int random(int min, int max) {
        return random(max - min) + max;
    }
    public static int randomInclusive(int min, int max) {
        return random(max - min + 1) + min;
    }

    public static RGBColor rgb() {
        return new RGBColor(random(), random(), random());
    }
}
