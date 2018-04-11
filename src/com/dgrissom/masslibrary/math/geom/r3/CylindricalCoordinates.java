package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PolarCoordinates;

// https://en.wikipedia.org/wiki/Cylindrical_coordinate_system
public final class CylindricalCoordinates {
    // (ρ, φ, z)
    @Formatted
    private final double radius, azimuth, height;

    public CylindricalCoordinates(double radius, double azimuth, double height) {
        this.radius = radius;
        this.azimuth = azimuth;
        this.height = height;
    }

    public double getRadius() {
        return this.radius;
    }
    public CylindricalCoordinates setRadius(double radius) {
        return new CylindricalCoordinates(radius, this.azimuth, this.height);
    }
    public double getAzimuth() {
        return this.azimuth;
    }
    public CylindricalCoordinates setAzimuth(double azimuth) {
        return new CylindricalCoordinates(this.radius, azimuth, this.height);
    }
    public double getHeight() {
        return this.height;
    }
    public CylindricalCoordinates setHeight(double height) {
        return new CylindricalCoordinates(this.radius, this.azimuth, height);
    }

    public PolarCoordinates polarCoordinates() {
        return new PolarCoordinates(this.radius, this.azimuth);
    }

    // https://en.wikipedia.org/wiki/Cylindrical_coordinate_system#Cartesian_coordinates
    public Point3d toCartesian() {
        Point2d p2d = polarCoordinates().toCartesian();
        return new Point3d(p2d.getX(), p2d.getY(), this.height);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
