package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.GeneralMath;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;
import com.dgrissom.masslibrary.math.geom.Transformable;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

public final class Point2d implements XY, Transformable {
    @Formatted
    private final double x, y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }
    public Point2d setX(double x) {
        return new Point2d(x, this.y);
    }
    @Override
    public double getY() {
        return this.y;
    }
    public Point2d setY(double y) {
        return new Point2d(this.x, y);
    }

    public Point2d add(double x, double y) {
        return new Point2d(this.x + x, this.y + y);
    }
    public Point2d add(double d) {
        return add(d, d);
    }
    public Point2d add(XY other) {
        return add(other.getX(), other.getY());
    }
    public Point2d subtract(double x, double y) {
        return new Point2d(this.x - x, this.y - y);
    }
    public Point2d subtract(double d) {
        return subtract(d, d);
    }
    public Point2d subtract(XY other) {
        return subtract(other.getX(), other.getY());
    }
    public Point2d multiply(double x, double y) {
        return new Point2d(this.x * x, this.y * y);
    }
    public Point2d multiply(double d) {
        return multiply(d, d);
    }
    public Point2d multiply(XY other) {
        return multiply(other.getX(), other.getY());
    }
    public Point2d divide(double x, double y) {
        return new Point2d(this.x / x, this.y / y);
    }
    public Point2d divide(double d) {
        return divide(d, d);
    }
    public Point2d divide(XY other) {
        return add(other.getX(), other.getY());
    }

    public double distanceSquared(Point2d other) {
        return Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2);
    }
    public double distance(Point2d other) {
        return Math.sqrt(distanceSquared(other));
    }
    // angle from origin to us
    public double angle() {
        return Math.atan2(this.y, this.x);
    }
    // angle from the given origin to us
    public double angle(Point2d origin) {
        return subtract(origin).angle();
    }

    public Point2d clamp(Rectangle2d bounds) {
        return new Point2d(GeneralMath.clamp(this.x, bounds.left(), bounds.right()),
                GeneralMath.clamp(this.y, bounds.top(), bounds.bottom()));
    }

    public PolarCoordinates toPolar(Point2d pole) {
        double magnitude = distance(pole);
        double angle = angle(pole);
        return new PolarCoordinates(pole, magnitude, angle);
    }

    // you may want 3 rows (x,y,1) for homogeneous coordinates
    public Matrix toColumnMatrix(int rows) {
        double[] d = new double[rows];
        d[0] = this.x;
        d[1] = this.y;
        for (int i = 2; i < rows; i++)
            d[i] = 1;
        return Matrix.column(d);
    }
    public static Point2d fromColumnMatrix(Matrix matrix) {
        return new Point2d(matrix.get(0, 0), matrix.get(1, 0));
    }

    public Point2d multiply(Matrix matrix) {
        return fromColumnMatrix(matrix.multiply(toColumnMatrix(matrix.getRows())));
    }
    @Override
    public Point2d transform(Transform transform) {
        return multiply(transform.matrix());
    }

    public LineSegment2d lineTo(Point2d other) {
        return new LineSegment2d(this, other);
    }
    public Ray2d rayTo(Point2d other) {
        return Ray2d.from(this, other);
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point2d))
            return false;
        Point2d other = (Point2d) o;
        return this.x == other.getX() && this.y == other.getY();
    }

    public static Point2d origin() {
        return new Point2d(0, 0);
    }
}
