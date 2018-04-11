package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

// xyY
// https://en.wikipedia.org/wiki/CIE_1931_color_space#CIE_xy_chromaticity_diagram_and_the_CIE_xyY_color_space
// defined by chromaticity coordinates (x,y) and luminance (Y)
public class XYYColor implements Color {
    @Formatted
    private final ChromaticityCoordinates coordinates;
    // luminance [0, 1]
    @Formatted
    private final double luminance;

    public XYYColor(double x, double y) {
        this(x, y, 1);
    }
    public XYYColor(double x, double y, double Y) {
        this(new ChromaticityCoordinates(x, y), Y);
    }
    public XYYColor(ChromaticityCoordinates coordinates, double luminance) {
        this.coordinates = coordinates;
        this.luminance = luminance;
    }

    public ChromaticityCoordinates getCoordinates() {
        return this.coordinates;
    }
    public XYYColor setCoordinates(ChromaticityCoordinates coordinates) {
        return new XYYColor(coordinates, this.luminance);
    }
    public double x() {
        return this.coordinates.getX();
    }
    public XYYColor setX(double x) {
        return setCoordinates(this.coordinates.setX(x));
    }
    public double y() {
        return this.coordinates.getY();
    }
    public XYYColor setY(double y) {
        return setCoordinates(this.coordinates.setY(y));
    }
    public double getLuminance() {
        return this.luminance;
    }
    public XYYColor setLuminance(double luminance) {
        return new XYYColor(this.coordinates, luminance);
    }

    // https://en.wikipedia.org/wiki/CIE_1931_color_space#CIE_xy_chromaticity_diagram_and_the_CIE_xyY_color_space
    // x,y,Y -> X,Y,Z
    public XYZColor toXYZ() {
        double Y = this.luminance * 100;
        double X = (Y / this.coordinates.getY()) * this.coordinates.getX();
        double Z = (Y / this.coordinates.getY()) * (1 - this.coordinates.getX() - this.coordinates.getY());
        return new XYZColor(X, Y, Z);
    }
    @Override
    public RGBColor toRGB(ColorSpace colorSpace) {
        return toXYZ().toRGB(colorSpace);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }

    // https://en.wikipedia.org/wiki/CIE_1931_color_space#Mixing_colors_specified_with_the_CIE_xy_chromaticity_diagram
    public static XYYColor mix(XYZColor... colors) {
        double xNum = 0, xDen = 0, yNum = 0, yDen = 0;
        for (XYZColor color : colors) {
            ChromaticityCoordinates cc = color.toChromaticityCoordinates();
            xNum += (cc.getX() / cc.getY()) * color.getY();
            xDen += color.getY() / cc.getY();
            yNum += color.getY();
            yDen += color.getY() / cc.getY();
        }
        double x = xNum / xDen;
        double y = yNum / yDen;
        double luminance = 100;
        return new XYYColor(new ChromaticityCoordinates(x, y), luminance);
    }
}
