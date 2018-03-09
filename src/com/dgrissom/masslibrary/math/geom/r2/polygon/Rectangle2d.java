package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.Formatter;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;

import java.util.Arrays;
import java.util.List;

// https://en.wikipedia.org/wiki/Rectangle
public final class Rectangle2d implements Polygon {
    @Formatted
    private final Point2d position;
    @Formatted
    private final double width, height;

    public Rectangle2d(double width, double height) {
        this(0, 0, width, height);
    }
    public Rectangle2d(double x, double y, double width, double height) {
        this(new Point2d(x, y), width, height);
    }
    public Rectangle2d(Point2d position, double width, double height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Point2d getPosition() {
        return this.position;
    }
    public Rectangle2d setPosition(Point2d position) {
        return new Rectangle2d(position, this.width, this.height);
    }
    public double getWidth() {
        return this.width;
    }
    public Rectangle2d setWidth(double width) {
        return new Rectangle2d(this.position, width, this.height);
    }
    public double getHeight() {
        return this.height;
    }
    public Rectangle2d setHeight(double height) {
        return new Rectangle2d(this.position, this.width, this.height);
    }

    public double getX() {
        return this.position.getX();
    }
    public Rectangle2d setX(double x) {
        return setPosition(this.position.setX(x));
    }
    public double getY() {
        return this.position.getY();
    }
    public Rectangle2d setY(double y) {
        return setPosition(this.position.setY(y));
    }

    public double left() {
        return this.position.getX();
    }
    public Rectangle2d setLeft(double left) {
        return setPosition(this.position.setX(left));
    }
    public double top() {
        return this.position.getY();
    }
    public Rectangle2d setTop(double top) {
        return setPosition(this.position.setY(top));
    }
    public double right() {
        return this.position.getX() + this.width;
    }
    // affects position NOT size
    // (translates so the right side is what we want)
    // same with setBottom and setBottomLeft and... etc...
    public Rectangle2d setRight(double right) {
        return setPosition(this.position.setX(right - this.width));
    }
    public double bottom() {
        return this.position.getY() + this.height;
    }
    public Rectangle2d setBottom(double bottom) {
        return setPosition(this.position.setY(bottom - this.height));
    }

    public Point2d topLeft() {
        return this.position;
    }
    public Rectangle2d setTopLeft(Point2d topLeft) {
        return setPosition(topLeft);
    }
    public Point2d topRight() {
        return this.position.add(this.width, 0);
    }
    public Rectangle2d setTopRight(Point2d topRight) {
        return setPosition(topRight.subtract(this.width, 0));
    }
    public Point2d bottomRight() {
        return this.position.add(this.width, this.height);
    }
    public Rectangle2d setBottomRight(Point2d bottomRight) {
        return setPosition(bottomRight.subtract(this.width, this.height));
    }
    public Point2d bottomLeft() {
        return this.position.add(0, this.height);
    }
    public Rectangle2d setBottomLeft(Point2d bottomLeft) {
        return setPosition(bottomLeft.subtract(0, this.height));
    }
    public Rectangle2d setCenter(Point2d center) {
        return setPosition(center.subtract(this.width / 2, this.height / 2));
    }

    public LineSegment2d diagonal() {
        return new LineSegment2d(this.position, bottomRight());
    }

    public LineSegment2d leftSide() {
        return new LineSegment2d(bottomLeft(), topLeft());
    }
    public LineSegment2d topSide() {
        return new LineSegment2d(topLeft(), topRight());
    }
    public LineSegment2d rightSide() {
        return new LineSegment2d(topRight(), bottomRight());
    }
    public LineSegment2d bottomSide() {
        return new LineSegment2d(bottomRight(), bottomLeft());
    }

    // width becomes width +  2 * amount (adds amount to all sides)
    // (think padding)
    public Rectangle2d expand(double horizontalAmount, double verticalAmount) {
        return from(centroid(), this.width + horizontalAmount * 2, this.height + verticalAmount * 2);
    }
    public Rectangle2d translate(double tx, double ty) {
        return setPosition(this.position.add(tx, ty));
    }

    public double area() {
        return this.width * this.height;
    }
    public double perimeter() {
        return 2 * (this.width + this.height);
    }

    public boolean isSquare() {
        return this.width == this.height;
    }

    // simplified
    @Override
    public Point2d centroid() {
        return this.position.add(this.width / 2, this.height / 2);
    }
    @Override
    public Rectangle2d boundingBox() {
        return this;
    }
    @Override
    public List<LineSegment2d> getSides() {
        return Arrays.asList(topSide(), rightSide(), bottomSide(), leftSide());
    }

    @Override
    public String toString() {
        return Formatter.format(this);
    }

    public static Rectangle2d from(double left, double top, double right, double bottom) {
        return new Rectangle2d(left, top, right - left, bottom - top);
    }
    public static Rectangle2d from(Point2d center, double width, double height) {
        return new Rectangle2d(width, height).setCenter(center);
    }
}
