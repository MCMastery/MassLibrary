package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

public final class Vector2d implements XY {
    @Formatted
    private final double x, y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }
    public Vector2d setX(double x) {
        return new Vector2d(x, this.y);
    }
    @Override
    public double getY() {
        return this.y;
    }
    public Vector2d setY(double y) {
        return new Vector2d(this.x, y);
    }

    public Vector2d add(double x, double y) {
        return new Vector2d(this.x + x, this.y + y);
    }
    public Vector2d add(double d) {
        return add(d, d);
    }
    public Vector2d add(Vector2d other) {
        return add(other.getX(), other.getY());
    }
    public Vector2d subtract(double x, double y) {
        return new Vector2d(this.x - x, this.y - y);
    }
    public Vector2d subtract(double d) {
        return subtract(d, d);
    }
    public Vector2d subtract(Vector2d other) {
        return subtract(other.getX(), other.getY());
    }
    public Vector2d multiply(double x, double y) {
        return new Vector2d(this.x * x, this.y * y);
    }
    public Vector2d multiply(double d) {
        return multiply(d, d);
    }
    public Vector2d multiply(Vector2d other) {
        return multiply(other.getX(), other.getY());
    }
    public Vector2d divide(double x, double y) {
        return new Vector2d(this.x / x, this.y / y);
    }
    public Vector2d divide(double d) {
        return divide(d, d);
    }
    public Vector2d divide(Vector2d other) {
        return add(other.getX(), other.getY());
    }

    public double distanceSquared(Vector2d other) {
        return Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2);
    }
    public double distance(Vector2d other) {
        return Math.sqrt(distanceSquared(other));
    }
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    public Vector2d normalize() {
        return divide(magnitude());
    }
    // https://en.wikipedia.org/wiki/Dot_product
    public double dotProduct(XY other) {
        return this.x * other.getX() + this.y * other.getY();
    }
    // angle from origin to us
    public double angle() {
        return Math.atan2(this.y, this.x);
    }

    public Point2d toPoint() {
        return new Point2d(this.x, this.y);
    }
    public PolarCoordinates toPolar() {
        return new PolarCoordinates(magnitude(), angle());
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2d))
            return false;
        Vector2d other = (Vector2d) o;
        return this.x == other.getX() && this.y == other.getY();
    }

    public static Vector2d from(Point2d p) {
        return new Vector2d(p.getX(), p.getY());
    }
    public static Vector2d direction(Point2d p1, Point2d p2) {
        return from(p2.subtract(p1)).normalize();
    }
}
