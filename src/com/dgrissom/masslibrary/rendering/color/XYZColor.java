package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;
import com.dgrissom.masslibrary.math.Matrix;

// https://en.wikipedia.org/wiki/CIE_1931_color_space
public class XYZColor implements Color {
    // "tristimulus values"
    // y [0,100] and represents luminance
    @Formatted
    private final double x, y, z;

    public XYZColor(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }
    public XYZColor setX(double x) {
        return new XYZColor(x, this.y, this.z);
    }
    public double getY() {
        return this.y;
    }
    public XYZColor setY(double y) {
        return new XYZColor(this.x, y, this.z);
    }
    public double getZ() {
        return this.z;
    }
    public XYZColor setZ(double z) {
        return new XYZColor(this.x, this.y, z);
    }

    public Matrix toColumnMatrix() {
        return Matrix.column(this.x, this.y, this.z);
    }
    public static XYZColor fromColumnMatrix(Matrix m) {
        return new XYZColor(m.get(0, 0), m.get(1, 0), m.get(2, 0));
    }


    public XYZColor mix(XYZColor b) {
        return new XYZColor((this.x / 100) * (b.getX() / 100) * 100,
                (this.y / 100) * (b.getY() / 100) * 100,
                (this.z / 100) * (b.getZ() / 100) * 100);
    }

    // https://en.wikipedia.org/wiki/CIE_1931_color_space#CIE_xy_chromaticity_diagram_and_the_CIE_xyY_color_space
    public ChromaticityCoordinates toChromaticityCoordinates() {
        double x = this.x / (this.x + this.y + this.z);
        double y = this.y / (this.x + this.y + this.z);
        return new ChromaticityCoordinates(x, y);
    }
    public XYYColor toXYY() {
        return new XYYColor(toChromaticityCoordinates(), this.y);
    }
    // http://www.brucelindbloom.com/Eqn_XYZ_to_Lab.html
    public LabColor toLab(ColorSpace colorSpace) {
        XYZColor whitepoint = colorSpace.getWhitepoint().toXYZ();
        double xr = this.x / whitepoint.getX();
        double yr = this.y / whitepoint.getY();
        double zr = this.z / whitepoint.getZ();
        double fx = (xr > LabColor.EPSILON) ? Math.pow(xr, 1 / 3.0) : ((LabColor.KAPPA * xr + 16) / 116);
        double fy = (yr > LabColor.EPSILON) ? Math.pow(yr, 1 / 3.0) : ((LabColor.KAPPA * yr + 16) / 116);
        double fz = (zr > LabColor.EPSILON) ? Math.pow(zr, 1 / 3.0) : ((LabColor.KAPPA * zr + 16) / 116);

        double L = 116 * fy - 16;
        double a = 500 * (fx - fy);
        double b = 200 * (fy - fz);
        return new LabColor(L, a, b);
    }
    // http://www.brucelindbloom.com/Eqn_XYZ_to_RGB.html
    @Override
    public RGBColor toRGB(ColorSpace colorSpace) {
        RGBColor color = RGBColor.fromColumnMatrix(colorSpace.xyzToRGB().multiply(toColumnMatrix()));
        return colorSpace.compand(color);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
