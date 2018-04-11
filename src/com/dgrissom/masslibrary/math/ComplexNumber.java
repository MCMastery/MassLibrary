package com.dgrissom.masslibrary.math;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import com.dgrissom.masslibrary.rendering.color.HSLColor;

// a value in the form a + bi
public class ComplexNumber {
    private final double a, b;

    public ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return this.a;
    }
    public ComplexNumber setA(double a) {
        return new ComplexNumber(a, this.b);
    }
    public double getB() {
        return this.b;
    }
    public ComplexNumber setB(double b) {
        return new ComplexNumber(this.a, b);
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.a + other.getA(), this.b + other.getB());
    }
    public ComplexNumber add(double d) {
        return new ComplexNumber(this.a + d, this.b);
    }
    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.a - other.getA(), this.b - other.getB());
    }
    public ComplexNumber subtract(double d) {
        return new ComplexNumber(this.a - d, this.b);
    }
    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(this.a * other.getA() - this.b * other.getB(), this.a * other.getA() + this.b * other.getB());
    }

    // returns (a,b)
    public Point2d toCartesian() {
        return new Point2d(this.a, this.b);
    }
    public Vector2d toVector2d() {
        return new Vector2d(this.a, this.b);
    }

    // https://en.wikipedia.org/wiki/Argument_(complex_analysis)
    public double arg() {
        return toVector2d().angle();
    }
    public double abs() {
        return toVector2d().magnitude();
    }

    // http://www.milefoot.com/math/complex/functionsofi.htm
    public ComplexNumber sin() {
        return new ComplexNumber(Math.sin(this.a) * Math.cosh(this.b), Math.cos(this.a) * Math.sinh(this.b));
    }

    // https://en.wikipedia.org/wiki/Domain_coloring
    public HSLColor toHSL() {
        double h = Math.toDegrees(arg());
        if (h < 0)
            h += 360;
        double s = 1;
        double l = 1 - Math.pow(2, -abs());
        return new HSLColor(h, s, l);
    }

    @Override
    public String toString() {
        return "ComplexNumber[" + this.a + " + " + this.b + "i]";
    }
}
