package com.dgrissom.masslibrary.rendering.plot;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;

public class PlotTransform {

    // scale factors assume a situation where the default is x increases toward the right
    // and y values increases downwards
    // do x-values increase leftwards or rightwards
    public enum HorizontalAxisDirection {
        LEFT(-1),
        RIGHT(1);

        private final double scaleFactor;

        HorizontalAxisDirection(double scaleFactor) {
            this.scaleFactor = scaleFactor;
        }

        public double getScaleFactor() {
            return this.scaleFactor;
        }
    }
    // do y-values increase upwards or downwards
    public enum VerticalAxisDirection {
        UP(-1),
        DOWN(1);

        private final double scaleFactor;

        VerticalAxisDirection(double scaleFactor) {
            this.scaleFactor = scaleFactor;
        }

        public double getScaleFactor() {
            return this.scaleFactor;
        }
    }

    private final HorizontalAxisDirection horizontalAxisDirection;
    private final VerticalAxisDirection verticalAxisDirection;
    private final Point2d origin;
    private final double pixelsPerUnit;

    public PlotTransform(Point2d origin, HorizontalAxisDirection horizontalAxisDirection,
                         VerticalAxisDirection verticalAxisDirection, double pixelsPerUnit) {
        this.origin = origin;
        this.horizontalAxisDirection = horizontalAxisDirection;
        this.verticalAxisDirection = verticalAxisDirection;
        this.pixelsPerUnit = pixelsPerUnit;
    }

    public Point2d getOrigin() {
        return this.origin;
    }
    public HorizontalAxisDirection getHorizontalAxisDirection() {
        return this.horizontalAxisDirection;
    }
    public VerticalAxisDirection getVerticalAxisDirection() {
        return this.verticalAxisDirection;
    }
    public double getPixelsPerUnit() {
        return this.pixelsPerUnit;
    }

    public Point2d transform(Point2d point) {
        return point.multiply(this.horizontalAxisDirection.getScaleFactor(), this.verticalAxisDirection.getScaleFactor())
                .multiply(this.pixelsPerUnit)
                .add(this.origin);
    }
    public Point2d untransform(Point2d point) {
        return point.subtract(this.origin)
                .divide(this.pixelsPerUnit)
                .divide(this.horizontalAxisDirection.getScaleFactor(), this.verticalAxisDirection.getScaleFactor());
    }
}
