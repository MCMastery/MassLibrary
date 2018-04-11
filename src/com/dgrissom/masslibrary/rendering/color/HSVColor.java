package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

// aka HSB
// https://en.wikipedia.org/wiki/HSL_and_HSV
public class HSVColor extends CylindricalColorModel {
    // hue [0, 360]
    // saturation, value [0, 1]
    @Formatted
    private final double h, s, v;

    public HSVColor(double h, double s, double v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }

    public double getH() {
        return this.h;
    }
    public HSVColor setH(double h) {
        return new HSVColor(h, this.s, this.v);
    }
    public double getS() {
        return this.s;
    }
    public HSVColor setS(double s) {
        return new HSVColor(this.h, s, this.v);
    }
    public double getV() {
        return this.v;
    }
    public HSVColor setV(double v) {
        return new HSVColor(this.h, this.s, v);
    }

    @Override
    public double hue() {
        return this.h;
    }
    @Override
    public double chroma() {
        return this.v * this.s;
    }
    // https://en.wikipedia.org/wiki/HSL_and_HSV#Converting_to_RGB
    @Override
    RGBColor matchLightness(RGBColor rgb) {
        double m = this.v - chroma();
        return rgb.add(m);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
