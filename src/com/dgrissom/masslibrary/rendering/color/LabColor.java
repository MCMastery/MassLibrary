package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

public class LabColor implements Color {
    // used in conversions
    public static final double EPSILON = 216 / 24389.0;
    public static final double KAPPA = 24389 / 27.0;
    // lightness [0, 100]
    // a [-128, 128]
    // b [-128, 128]
    @Formatted
    private final double l, a, b;

    public LabColor(double l, double a, double b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }

    public double getL() {
        return this.l;
    }
    public LabColor setL(double l) {
        return new LabColor(l, this.a, this.b);
    }
    public double getA() {
        return this.a;
    }
    public LabColor setA(double a) {
        return new LabColor(this.l, a, this.b);
    }
    public double getB() {
        return this.b;
    }
    public LabColor setB(double b) {
        return new LabColor(this.l, this.a, b);
    }

    public double deltaE76(LabColor other) {
        return Math.sqrt(Math.pow(other.getL() - this.l, 2) + Math.pow(other.getA() - this.a, 2)
                + Math.pow(other.getB() - this.b, 2));
    }

    // https://en.wikipedia.org/wiki/Color_difference#CIE94
    // uses graphics arts parameters
    public double deltaE94(LabColor other) {
        return deltaE94(other, 1, 0.045, 0.015);
    }
    // graphics arts: {kL: 1, K1: 0.045, K2: 0.015}
    // textiles: {kL: 2, K1: 0.048, K2: 0.014}
    public double deltaE94(LabColor other, double kL, double K1, double K2) {
        LCHabColor a = toLCHab(), b = other.toLCHab();
        double deltaL = this.l - other.getL();
        double deltaCab = a.getC() - b.getC();
        double deltaA = this.a - other.getA();
        double deltaB = this.b - other.getB();
        double deltaHab = Math.sqrt(deltaA * deltaA + deltaB * deltaB - deltaCab * deltaCab);
        double SL = 1;
        double SC = 1 + K1 * a.getC();
        double SH = 1 + K2 * b.getC();
        // "are usually both unity"
        double kC = 1;
        double kH = 1;
        return Math.sqrt(Math.pow(deltaL / (kL * SL), 2) + Math.pow(deltaCab / (kC * SC), 2) + Math.pow(deltaHab / (kH * SH), 2));
    }

    // generally considered most accurate however is not the most common, saturation differences
    // http://www.brucelindbloom.com/Eqn_DeltaE_CIE2000.html
    //todo yeah this is not working... what is wrong??
    @Deprecated
    public double deltaE2000(LabColor other) {
        double LPrime = (this.l + other.getL()) / 2;
        double C1 = Math.sqrt(this.a * this.a + this.b * this.b);
        double C2 = Math.sqrt(other.getA() * other.getA() + other.getB() * other.getB());
        double CMean = (C1 + C2) / 2;
        double CMean7 = Math.pow(CMean, 7);
        double G = 0.5 * (1 - Math.sqrt(CMean7 / (CMean7 + Math.pow(25, 7))));

        double aPrime1 = this.a * (1 + G);
        double aPrime2 = other.getA() * (1 + G);

        double CPrime1 = Math.sqrt(aPrime1 * aPrime1 + this.b * this.b);
        double CPrime2 = Math.sqrt(aPrime2 * aPrime2 + other.getB() * other.getB());
        double CPrimeMean = (CPrime1 + CPrime2) / 2;

        double hPrime1 = Math.toDegrees(Math.atan2(this.b, aPrime1));
        double hPrime2 = Math.toDegrees(Math.atan2(other.getB(), aPrime2));
        double HPrimeMean = (Math.abs(hPrime1 - hPrime2) > 180) ? ((hPrime1 + hPrime2 + 360) / 2) : (hPrime1 + hPrime2) / 2;

        double T = 1 - 0.17 * Math.toDegrees(Math.cos(Math.toRadians(HPrimeMean - 30)))
                + 0.24 * Math.toDegrees(Math.cos(Math.toRadians(2 * HPrimeMean)))
                + 0.32 * Math.toDegrees(Math.cos(Math.toRadians(3 * HPrimeMean + 6)))
                - 0.20 * Math.toDegrees(Math.cos(Math.toRadians(4 * HPrimeMean - 63)));

        double deltahPrime;
        double delta = Math.abs(hPrime2 - hPrime1);
        if (delta <= 180)
            deltahPrime = hPrime2 - hPrime1;
        else if (delta > 180 && hPrime2 < hPrime1)
            deltahPrime = hPrime2 - hPrime1 + 360;
        else
            deltahPrime = hPrime2 - hPrime1 - 360;

        double deltaLPrime = other.getL() - this.l;
        double deltaCPrime = CPrime2 - CPrime1;
        double deltaHPrime = 2 * Math.sqrt(CPrime1 * CPrime2) * Math.toDegrees(Math.sin(Math.toRadians(deltahPrime / 2)));

        double SL = 1 + (0.015 * Math.pow(LPrime - 50, 2)) / (Math.sqrt(20 + Math.pow(LPrime - 50, 20)));
        double SC = 1 + 0.045 * CPrimeMean;
        double SH = 1 + 0.015 * CPrimeMean * T;

        double deltaTheta = 30 * Math.exp(-Math.pow((HPrimeMean - 275) / 25, 2));

        double CPrimeMean7 = Math.pow(CPrimeMean, 7);
        double RC = 2 * Math.sqrt(CPrimeMean7 / (CPrimeMean7 + Math.pow(25, 7)));
        double RT = -RC * Math.toDegrees(Math.sin(Math.toRadians(2 * deltaTheta)));
        double KL = 1;
        double KC = 1;
        double KH = 1;

        return Math.sqrt(Math.pow(deltaLPrime / (KL * SL), 2)
                + Math.pow(deltaCPrime / (KC * SC), 2)
                + Math.pow(deltaHPrime / (KH * SH), 2)
                + RT * (deltaCPrime / (KC * SC))
                * (deltaHPrime / (KH * SH))
        );

//        LCHabColor a = toLCHab(), b = other.toLCHab();
//        double LPrime = (this.l + other.getL()) / 2;
//        double CAvg = (a.getC() + b.getC()) / 2;
//        double CAvg7 = Math.pow(CAvg, 7);
//        double G = 0.5 * (1 - Math.sqrt(CAvg7 / (CAvg7 + Math.pow(25, 7))));
//        double a1Prime = this.a * (1 + G);
//        double a2Prime = other.getA() * (1 + G);
//
//        LCHabColor a2 = setA(a1Prime).toLCHab(), b2 = other.setA(a2Prime).toLCHab();
//        double CAvgPrime = (a2.getC() + b2.getC()) / 2;
//        double HAvgPrime = (Math.abs(a2.getH() - b2.getH()) > 180) ? (a2.getH() + b2.getH() + 360) / 2
//                : (a2.getH() + b2.getH()) / 2;
//        double T = 1 - 0.17 * Math.cos(Math.toRadians(HAvgPrime - 30))
//                + 0.24 * Math.cos(Math.toRadians(2 * HAvgPrime))
//                + 0.32 * Math.cos(Math.toRadians(3 * HAvgPrime + 6))
//                - 0.20 * Math.cos(Math.toRadians(4 * HAvgPrime - 63));
//
//        double delta = Math.abs(b2.getH() - a2.getH());
//        double deltahPrime;
//        if (delta <= 180)
//            deltahPrime = b2.getH() - a2.getH();
//        else if (delta > 180 && b2.getH() <= a2.getH())
//            deltahPrime = b2.getH() - a2.getH() + 360;
//        else
//            deltahPrime = b2.getH() - a2.getH() - 360;
//
//        double deltaLPrime = other.getL() - this.l;
//        double deltaCPrime = b2.getC() - a2.getC();
//        double deltaHPrime = 2 * Math.sqrt(a2.getC() * b2.getC()) * Math.sin(Math.toRadians(deltahPrime / 2));
//        double SL = (1 + (0.015 * Math.pow(LPrime - 50, 2)) / Math.sqrt(20 + Math.pow(LPrime - 50, 2)));
//        double SC = 1 + 0.045 * CAvgPrime;
//        double SH = 1 + 0.015 * CAvgPrime * T;
//
//        double deltaTheta = 30 * Math.toDegrees(Math.exp(Math.toRadians(-Math.pow((HAvgPrime - 275) / 25, 2))));
//        double CAvgPrime7 = Math.pow(CAvgPrime, 7);
//        double RC = 2 * Math.sqrt(CAvgPrime7 / (CAvgPrime7 + Math.pow(25, 7)));
//        double RT = -RC * Math.sin(Math.toRadians(2 * deltaTheta));
//        double KL = 1;
//        double KC = 1;
//        double KH = 1;
//        return Math.sqrt(Math.pow(deltaLPrime / (KL * SL), 2) + Math.pow(deltaCPrime / (KC * SC), 2)
//                + Math.pow(deltaHPrime / (KH * SH), 2) + RT * (deltaCPrime / (KC * SC)) * (deltaHPrime / (KH * SH)));
    }

    // http://www.brucelindbloom.com/Eqn_Lab_to_LCH.html
    public LCHabColor toLCHab() {
        double c = Math.sqrt(this.a * this.a + this.b * this.b);
        double h = Math.toDegrees(Math.atan2(this.b, this.a));
        return new LCHabColor(this.l, c, h);
    }
    // https://en.wikipedia.org/wiki/Lab_color_space#CIELAB-CIEXYZ_conversions
    // we use bruce lindbloom's
    // http://www.brucelindbloom.com/index.html?Eqn_Lab_to_XYZ.html
    public XYZColor toXYZ(ColorSpace colorSpace) {
        double fy = (this.l + 16) / 116;
        double fz = fy - this.b / 200;
        double fx = this.a / 500 + fy;
        double x = (fx * fx * fx > EPSILON) ? (fx * fx * fx) : ((116 * fx - 16) / KAPPA);
        double y = (this.l > KAPPA * EPSILON) ? (fy * fy * fy) : (this.l / KAPPA);
        double z = (fz * fz * fz > EPSILON) ? (fz * fz * fz) : ((116 * fz - 16) / KAPPA);

        XYZColor whitepoint = colorSpace.getWhitepoint().toXYZ();
        return new XYZColor(x * whitepoint.getX(), y * whitepoint.getY(), z * whitepoint.getZ());
    }
    @Override
    public RGBColor toRGB(ColorSpace colorSpace) {
        return toXYZ(colorSpace).toRGB(colorSpace);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
