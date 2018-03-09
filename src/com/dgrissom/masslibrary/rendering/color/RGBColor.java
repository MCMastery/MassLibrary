package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.GeneralMath;

public class RGBColor {
    public static final RGBColor BLACK = new RGBColor(0, 0, 0);
    public static final RGBColor GRAY = new RGBColor(0.5, 0.5, 0.5);
    public static final RGBColor LIGHT_GRAY = new RGBColor(0.75, 0.75, 0.75);
    public static final RGBColor WHITE = new RGBColor(1, 1, 1);
    public static final RGBColor RED = new RGBColor(1, 0, 0);
    public static final RGBColor GREEN = new RGBColor(0, 1, 0);
    public static final RGBColor BLUE = new RGBColor(0, 0, 1);

    @Formatted
    private final double r, g, b, a;

    public RGBColor(double r, double g, double b) {
        this(r, g, b, 1);
    }
    public RGBColor(double r, double g, double b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public double getRed() {
        return this.r;
    }
    public RGBColor setRed(double r) {
        return new RGBColor(r, this.g, this.b, this.a);
    }
    public double getGreen() {
        return this.g;
    }
    public RGBColor setGreen(double g) {
        return new RGBColor(this.r, g, this.b, this.a);
    }
    public double getBlue() {
        return this.b;
    }
    public RGBColor setBlue(double b) {
        return new RGBColor(this.r, this.g, b, this.a);
    }
    public double getOpacity() {
        return this.a;
    }
    public RGBColor setOpacity(double a) {
        return new RGBColor(this.r, this.g, this.b, a);
    }

    public RGBColor add(double r, double g, double b, double a) {
        return new RGBColor(this.r + r, this.g + g, this.b + b, this.a + a);
    }
    public RGBColor add(double r, double g, double b) {
        return add(r, g, b, 0);
    }
    public RGBColor add(RGBColor color) {
        return add(color.getRed(), color.getGreen(), color.getBlue());
    }
    public RGBColor add(double d) {
        return add(d, d, d);
    }

    public RGBColor subtract(double r, double g, double b, double a) {
        return new RGBColor(this.r - r, this.g - g, this.b - b, this.a - a);
    }
    public RGBColor subtract(double r, double g, double b) {
        return subtract(r, g, b, 1);
    }
    public RGBColor subtract(RGBColor color) {
        return subtract(color.getRed(), color.getGreen(), color.getBlue());
    }
    public RGBColor subtract(double d) {
        return subtract(d, d, d);
    }

    public RGBColor multiply(double r, double g, double b, double a) {
        return new RGBColor(this.r * r, this.g * g, this.b * b, this.a * a);
    }
    public RGBColor multiply(double r, double g, double b) {
        return multiply(r, g, b, 1);
    }
    public RGBColor multiply(RGBColor color) {
        return multiply(color.getRed(), color.getGreen(), color.getBlue());
    }
    public RGBColor multiply(double d) {
        return multiply(d, d, d);
    }

    public RGBColor divide(double r, double g, double b, double a) {
        return new RGBColor(this.r / r, this.g / g, this.b / b, this.a / a);
    }
    public RGBColor divide(double r, double g, double b) {
        return divide(r, g, b, 1);
    }
    public RGBColor divide(RGBColor color) {
        return divide(color.getRed(), color.getGreen(), color.getBlue());
    }
    public RGBColor divide(double d) {
        return divide(d, d, d);
    }

    public RGBColor clamp() {
        return new RGBColor(GeneralMath.clamp(this.r, 0, 1), GeneralMath.clamp(this.g, 0, 1),
                GeneralMath.clamp(this.b, 0, 1), GeneralMath.clamp(this.a, 0, 1));
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }

    public static RGBColor fromRGB(int rgb) {
        int a = rgb >> 24 & 0xFF;
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb >> 0 & 0xFF;
        return new RGBColor(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
    }
}
