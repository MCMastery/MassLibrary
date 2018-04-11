package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;

// https://en.wikipedia.org/wiki/RGB_color_space
public abstract class ColorSpace {
    // r,g,b comes from http://www.brucelindbloom.com/index.html?WorkingSpaceInfo.html
    // because they are more precise
    // https://en.wikipedia.org/wiki/SRGB
    public static final ColorSpace SRGB = new ColorSpace(Whitepoint.D65,
            new XYYColor(0.6400, 0.3300, 0.212656),
            new XYYColor(0.3000, 0.6000, 0.715158),
            new XYYColor(0.1500, 0.0600, 0.072186)) {
        @Override
        public double compand(double v) {
            // https://en.wikipedia.org/wiki/SRGB#Specification_of_the_transformation
            if (v <= 0.0031308)
                return 12.92 * v;
            return 1.055 * Math.pow(v, 1 / 2.4) - 0.055;
        }
        @Override
        public double inverseCompand(double v) {
            // http://www.brucelindbloom.com/Eqn_RGB_to_XYZ.html
            if (v <= 0.04045)
                return v / 12.92;
            return Math.pow((v + 0.055) / 1.055, 2.4);
        }
    };
    public static final SimpleColorSpace WIDE_GAMUT = new SimpleColorSpace(Whitepoint.D50,
            new XYYColor(0.7350, 0.2650, 0.258187),
            new XYYColor(0.1150, 0.8260, 0.724938),
            new XYYColor(0.1570, 0.0180, 0.0180), 2.2);
    public static final SimpleColorSpace PRO_PHOTO = new SimpleColorSpace(Whitepoint.D50,
            new XYYColor(0.7347, 0.2653, 0.288040),
            new XYYColor(0.1596, 0.8404, 0.711874),
            new XYYColor(0.0366, 0.0001, 0.000086), 1.8);

    private final XYYColor whitepoint, r, g, b;

    public ColorSpace(XYYColor whitepoint, XYYColor r, XYYColor g, XYYColor b) {
        this.whitepoint = whitepoint;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public XYYColor getWhitepoint() {
        return this.whitepoint;
    }
    public XYYColor getR() {
        return this.r;
    }
    public XYYColor getG() {
        return this.g;
    }
    public XYYColor getB() {
        return this.b;
    }

    public RGBColor compand(RGBColor color) {
        return new RGBColor(compand(color.getR()), compand(color.getG()), compand(color.getB()));
    }
    public RGBColor inverseCompand(RGBColor color) {
        return new RGBColor(inverseCompand(color.getR()), inverseCompand(color.getG()), inverseCompand(color.getB()));
    }
    // http://www.brucelindbloom.com/Eqn_XYZ_to_RGB.html
    public abstract double compand(double v);
    // http://www.brucelindbloom.com/Eqn_RGB_to_XYZ.html
    public abstract double inverseCompand(double v);

    // returns the rgb to xyz matrix
    // http://www.brucelindbloom.com/Eqn_RGB_XYZ_Matrix.html
    public Matrix rgbToXYZ() {
        double Xr = this.r.x() / this.r.y();
        double Yr = 1;
        double Zr = (1 - this.r.x() - this.r.y()) / this.r.y();

        double Xg = this.g.x() / this.g.y();
        double Yg = 1;
        double Zg = (1 - this.g.x() - this.g.y()) / this.g.y();

        double Xb = this.b.x() / this.b.y();
        double Yb = 1;
        double Zb = (1 - this.b.x() - this.b.y()) / this.b.y();

        Matrix m1 = Matrix.square(
                Xr, Xg, Xb,
                Yr, Yg, Yb,
                Zr, Zg, Zb
        );
        Matrix whitepoint = this.whitepoint.toXYZ().toColumnMatrix();
        Matrix s = m1.inverse().multiply(whitepoint);
        double Sr = s.get(0, 0), Sg = s.get(1, 0), Sb = s.get(2, 0);

        return Matrix.square(
                Sr * Xr, Sg * Xg, Sb * Xb,
                Sr * Yr, Sg * Yg, Sb * Yb,
                Sr * Zr, Sg * Zg, Sb * Zb
        );
    }
    public Matrix xyzToRGB() {
        return rgbToXYZ().inverse();
    }

    public Polygon gamut() {
        return new PointSet2d(
                new Point2d(this.r.x(), this.r.y()),
                new Point2d(this.g.x(), this.g.y()),
                new Point2d(this.b.x(), this.b.y())
        ).toPolygon();
    }
}
