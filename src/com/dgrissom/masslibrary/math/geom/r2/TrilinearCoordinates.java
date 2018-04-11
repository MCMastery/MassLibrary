package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

// https://en.wikipedia.org/wiki/Trilinear_coordinates
public class TrilinearCoordinates {
    @Formatted
    private final double x, y, z;

    public TrilinearCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }
    public TrilinearCoordinates setX(double x) {
        return new TrilinearCoordinates(x, this.y, this.z);
    }
    public double getY() {
        return this.y;
    }
    public TrilinearCoordinates setY(double y) {
        return new TrilinearCoordinates(this.x, y, this.z);
    }
    public double getZ() {
        return this.z;
    }
    public TrilinearCoordinates setZ(double z) {
        return new TrilinearCoordinates(this.x, this.y, z);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
