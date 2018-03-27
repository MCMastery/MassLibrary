package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

// https://en.wikipedia.org/wiki/Colorfulness#Chroma_in_CIE_1976_L*a*b*_and_L*u*v*_color_spaces
public class LCHabColor implements Color {
    // h [0, 360]
    // c and h are really just polar coordinates
    @Formatted
    private final double l, c, h;

    public LCHabColor(double l, double c, double h) {
        this.l = l;
        this.c = c;
        this.h = h;
    }

    public double getL() {
        return this.l;
    }
    public LabColor setL(double l) {
        return new LabColor(l, this.c, this.h);
    }
    public double getC() {
        return this.c;
    }
    public LabColor setC(double c) {
        return new LabColor(this.l, c, this.h);
    }
    public double getH() {
        return this.h;
    }
    public LabColor setH(double h) {
        return new LabColor(this.l, this.c, h);
    }

    // http://www.brucelindbloom.com/Eqn_LCH_to_Lab.html
    public LabColor toLab() {
        double radians = Math.toRadians(this.h);
        double a = this.c * Math.cos(radians);
        double b = this.c * Math.sin(radians);
        return new LabColor(this.l, a, b);
    }
    @Override
    public RGBColor toRGB(ColorSpace colorSpace) {
        return toLab().toRGB(colorSpace);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
