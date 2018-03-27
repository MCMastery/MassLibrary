package com.dgrissom.masslibrary.math.geom.r3;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;
import com.dgrissom.masslibrary.math.geom.Transformable;

public final class Point3d implements XYZ, Transformable {
    @Formatted
    private final double x, y, z;

    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return this.x;
    }
    public Point3d setX(double x) {
        return new Point3d(x, this.y, this.z);
    }
    @Override
    public double getY() {
        return this.y;
    }
    public Point3d setY(double y) {
        return new Point3d(this.x, y, this.z);
    }
    @Override
    public double getZ() {
        return this.z;
    }
    public Point3d setZ(double z) {
        return new Point3d(this.x, this.y, z);
    }

    public Point3d add(double x, double y, double z) {
        return new Point3d(this.x + x, this.y + y, this.z + z);
    }
    public Point3d add(double d) {
        return add(d, d, d);
    }
    public Point3d add(XYZ other) {
        return add(other.getX(), other.getY(), other.getZ());
    }
    public Point3d subtract(double x, double y, double z) {
        return new Point3d(this.x - x, this.y - y, this.z - z);
    }
    public Point3d subtract(double d) {
        return subtract(d, d, d);
    }
    public Point3d subtract(XYZ other) {
        return subtract(other.getX(), other.getY(), other.getZ());
    }
    public Point3d multiply(double x, double y, double z) {
        return new Point3d(this.x * x, this.y * y, this.z * z);
    }
    public Point3d multiply(double d) {
        return multiply(d, d, d);
    }
    public Point3d multiply(XYZ other) {
        return multiply(other.getX(), other.getY(), other.getZ());
    }
    public Point3d divide(double x, double y, double z) {
        return new Point3d(this.x / x, this.y / y, this.z / z);
    }
    public Point3d divide(double d) {
        return divide(d, d, d);
    }
    public Point3d divide(XYZ other) {
        return add(other.getX(), other.getY(), other.getZ());
    }

    public double distanceSquared(Point3d other) {
        return Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2) + Math.pow(other.getZ() - this.z, 2);
    }
    public double distance(Point3d other) {
        return Math.sqrt(distanceSquared(other));
    }

    // you may want 4 rows (x,y,z,1) for homogeneous coordinates
    public Matrix toColumnMatrix(int rows) {
        double[] d = new double[rows];
        d[0] = this.x;
        d[1] = this.y;
        d[2] = this.z;
        for (int i = 3; i < rows; i++)
            d[i] = 1;
        return Matrix.column(d);
    }
    public static Point3d fromColumnMatrix(Matrix matrix) {
        return new Point3d(matrix.get(0, 0), matrix.get(1, 0), matrix.get(2, 0));
    }

    public Point3d multiply(Matrix matrix) {
        return fromColumnMatrix(matrix.multiply(toColumnMatrix(matrix.getRows())));
    }
    @Override
    public Point3d transform(Transform transform) {
        return multiply(transform.matrix());
    }

    public Ray3d rayTo(Point3d point) {
        return Ray3d.from(this, point);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point3d))
            return false;
        Point3d other = (Point3d) o;
        return this.x == other.getX() && this.y == other.getY() && this.z == other.getZ();
    }

    public static Point3d origin() {
        return new Point3d(0, 0, 0);
    }
}
