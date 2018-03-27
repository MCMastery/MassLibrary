package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.GeneralMath;
import com.dgrissom.masslibrary.math.Matrix;

public class RGBColor implements Color {
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

    public double getR() {
        return this.r;
    }
    public RGBColor setR(double r) {
        return new RGBColor(r, this.g, this.b, this.a);
    }
    public double getG() {
        return this.g;
    }
    public RGBColor setG(double g) {
        return new RGBColor(this.r, g, this.b, this.a);
    }
    public double getB() {
        return this.b;
    }
    public RGBColor setB(double b) {
        return new RGBColor(this.r, this.g, b, this.a);
    }
    public double getA() {
        return this.a;
    }
    public RGBColor setA(double a) {
        return new RGBColor(this.r, this.g, this.b, a);
    }

    public RGBColor add(double r, double g, double b, double a) {
        return new RGBColor(this.r + r, this.g + g, this.b + b, this.a + a);
    }
    public RGBColor add(double r, double g, double b) {
        return add(r, g, b, 0);
    }
    public RGBColor add(RGBColor color) {
        return add(color.getR(), color.getG(), color.getB());
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
        return subtract(color.getR(), color.getG(), color.getB());
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
        return multiply(color.getR(), color.getG(), color.getB());
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
        return divide(color.getR(), color.getG(), color.getB());
    }
    public RGBColor divide(double d) {
        return divide(d, d, d);
    }

    public double euclideanDistanceSquared(RGBColor other) {
        return Math.pow(other.getR() - this.r, 2) + Math.pow(other.getG() - this.g, 2) + Math.pow(other.getB() - this.b, 2);
    }
    public double euclideanDistance(RGBColor other) {
        return Math.sqrt(euclideanDistanceSquared(other));
    }

    public RGBColor clamp() {
        return new RGBColor(GeneralMath.clamp(this.r, 0, 1), GeneralMath.clamp(this.g, 0, 1),
                GeneralMath.clamp(this.b, 0, 1), GeneralMath.clamp(this.a, 0, 1));
    }
    // scales all values so that the value which is most outside the range [0,1] becomes in-range
    // better than clamp in some situations as this keeps the actual color better
    public RGBColor scaleToRange() {
        RGBColor corrected = this;
        // add min value if it is negative, so min value becomes 0
        double min = GeneralMath.min(this.r, this.g, this.b);
        double rangeError = 10e-10;
        if (min >= -rangeError && GeneralMath.max(this.r, this.g, this.b) <= 1 + rangeError)
            return this;
        if (min < 0)
            corrected = corrected.add(-min);

        // scale so max value becomes 0
        double max = GeneralMath.max(corrected.getR(), corrected.getG(), corrected.getB());

        double factor = 1 / max;
        return corrected.multiply(factor);
    }

    public Matrix toColumnMatrix() {
        return Matrix.column(this.r, this.g, this.b);
    }
    public static RGBColor fromColumnMatrix(Matrix m) {
        return new RGBColor(m.get(0, 0), m.get(1, 0), m.get(2, 0));
    }

    // used for converting to hsl / hsv
    private double hue(double Cmax, double delta) {
        double h;
        if (delta == 0)
            return 0;
        else if (Cmax == this.r)
            h = 60 * (((this.g - this.b) / delta) % 6);
        else if (Cmax == this.g)
            h = 60 * (((this.b - this.r) / delta) + 2);
        // Cmax = this.b
        else
            h = 60 * (((this.r - this.g) / delta) + 4);
        return (h < 0) ? h + 360 : h;
    }
    // https://www.rapidtables.com/convert/color/rgb-to-hsl.html
    public HSLColor toHSL() {
        double Cmax = GeneralMath.max(this.r, this.g, this.b);
        double Cmin = GeneralMath.min(this.r, this.g, this.b);
        double delta = Cmax - Cmin;
        double h = hue(Cmax, delta);
        double l = (Cmax + Cmin) / 2;
        double s = (delta == 0) ? 0 : (delta / (1 - Math.abs(2 * l - 1)));
        return new HSLColor(h, s, l);
    }
    // https://www.rapidtables.com/convert/color/rgb-to-hsv.html
    public HSVColor toHSV() {
        double Cmax = GeneralMath.max(this.r, this.g, this.b);
        double Cmin = GeneralMath.min(this.r, this.g, this.b);
        double delta = Cmax - Cmin;
        double h = hue(Cmax, delta);
        double v = Cmax;
        double s = (delta == 0) ? 0 : (delta / Cmax);
        return new HSVColor(h, s, v);
    }

    // http://www.brucelindbloom.com/Eqn_RGB_to_XYZ.html
    public XYZColor toXYZ(ColorSpace colorSpace) {
        RGBColor companded = colorSpace.inverseCompand(this);
        return XYZColor.fromColumnMatrix(colorSpace.rgbToXYZ().multiply(companded.toColumnMatrix()));
    }
    @Override
    public RGBColor toRGB(ColorSpace colorSpace) {
        return this;
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
