package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;

public final class Vector3d implements XYZ {
    @Formatted
    private final double x, y, z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return this.x;
    }
    public Vector3d setX(double x) {
        return new Vector3d(x, this.y, this.z);
    }
    @Override
    public double getY() {
        return this.y;
    }
    public Vector3d setY(double y) {
        return new Vector3d(this.x, y, this.z);
    }
    @Override
    public double getZ() {
        return this.z;
    }
    public Vector3d setZ(double z) {
        return new Vector3d(this.x, this.y, z);
    }

    public Vector3d add(double x, double y, double z) {
        return new Vector3d(this.x + x, this.y + y, this.z + z);
    }
    public Vector3d add(double d) {
        return add(d, d, d);
    }
    public Vector3d add(XYZ other) {
        return add(other.getX(), other.getY(), other.getZ());
    }
    public Vector3d subtract(double x, double y, double z) {
        return new Vector3d(this.x - x, this.y - y, this.z - z);
    }
    public Vector3d subtract(double d) {
        return subtract(d, d, d);
    }
    public Vector3d subtract(XYZ other) {
        return subtract(other.getX(), other.getY(), other.getZ());
    }
    public Vector3d multiply(double x, double y, double z) {
        return new Vector3d(this.x * x, this.y * y, this.z * z);
    }
    public Vector3d multiply(double d) {
        return multiply(d, d, d);
    }
    public Vector3d multiply(XYZ other) {
        return multiply(other.getX(), other.getY(), other.getZ());
    }
    public Vector3d divide(double x, double y, double z) {
        return new Vector3d(this.x / x, this.y / y, this.z / z);
    }
    public Vector3d divide(double d) {
        return divide(d, d, d);
    }
    public Vector3d divide(XYZ other) {
        return add(other.getX(), other.getY(), other.getZ());
    }

    public double distanceSquared(Vector3d other) {
        return Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2) + Math.pow(other.getZ() - this.z, 2);
    }
    public double distance(Vector3d other) {
        return Math.sqrt(distanceSquared(other));
    }
    public double magnitudeSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }
    public Vector3d normalize() {
        return divide(magnitude());
    }
    public double dotProduct(XYZ other) {
        return this.x * other.getX() + this.y * other.getY() + this.z * other.getZ();
    }

    public Point3d toPoint() {
        return new Point3d(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector3d))
            return false;
        Vector3d other = (Vector3d) o;
        return this.x == other.getX() && this.y == other.getY() && this.z == other.getZ();
    }

    public static Vector3d from(Point3d p) {
        return new Vector3d(p.getX(), p.getY(), p.getZ());
    }
    public static Vector3d direction(Point3d p1, Point3d p2) {
        return from(p2.subtract(p1)).normalize();
    }
}
