package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

// https://en.wikipedia.org/wiki/Polar_coordinate_system
public final class PolarCoordinates {
    // the origin
    @Formatted
    private final Point2d pole;
    @Formatted
    private final double magnitude, angle;

    public PolarCoordinates(double magnitude, double angle) {
        this(Point2d.origin(), magnitude, angle);
    }
    public PolarCoordinates(Point2d pole, double magnitude, double angle) {
        this.pole = pole;
        this.magnitude = magnitude;
        this.angle = angle;
    }

    public Point2d getPole() {
        return this.pole;
    }
    public PolarCoordinates setPole(Point2d pole) {
        return new PolarCoordinates(pole, this.magnitude, this.angle);
    }
    public double getMagnitude() {
        return this.magnitude;
    }
    public PolarCoordinates setMagnitude(double magnitude) {
        return new PolarCoordinates(this.pole, magnitude, this.angle);
    }
    public double getAngle() {
        return this.angle;
    }
    public PolarCoordinates setAngle(double angle) {
        return new PolarCoordinates(this.pole, this.magnitude, angle);
    }

    public PolarCoordinates extend(double amount) {
        return new PolarCoordinates(this.pole, this.magnitude + amount, this.angle);
    }
    public PolarCoordinates rotate(double amount) {
        return new PolarCoordinates(this.pole, this.magnitude, this.angle + amount);
    }

    public Point2d toCartesian() {
        double x = this.magnitude * Math.cos(this.angle) + this.pole.getX();
        double y = this.magnitude * Math.sin(this.angle) + this.pole.getY();
        return new Point2d(x, y);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
}
