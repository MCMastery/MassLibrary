package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

// https://en.wikipedia.org/wiki/HSL_and_HSV
public class HSLColor extends CylindricalColorModel {
    // hue [0, 360]
    // saturation, lightness [0, 1]
    @Formatted
    private final double h, s, l;

    public HSLColor(double h, double s, double l) {
        this.h = h;
        this.s = s;
        this.l = l;
    }

    public double getH() {
        return this.h;
    }
    public HSLColor setH(double h) {
        return new HSLColor(h, this.s, this.l);
    }
    public double getS() {
        return this.s;
    }
    public HSLColor setS(double s) {
        return new HSLColor(this.h, s, this.l);
    }
    public double getV() {
        return this.l;
    }
    public HSLColor setV(double v) {
        return new HSLColor(this.h, this.s, v);
    }

    @Override
    public double hue() {
        return this.h;
    }
    @Override
    public double chroma() {
        return (1 - Math.abs(2 * this.l - 1)) * this.s;
    }

    // https://en.wikipedia.org/wiki/HSL_and_HSV#Converting_to_RGB
    @Override
    RGBColor matchLightness(RGBColor rgb) {
        double m = this.l - 0.5 * chroma();
        return rgb.add(m);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
