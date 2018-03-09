package com.dgrissom.masslibrary.rendering;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

public class TextAnchor {
    public enum VAlignment {
        TOP {
            @Override
            public double positionY(double y, double boundsHeight) {
                return y;
            }
        },
        CENTER {
            @Override
            public double positionY(double y, double boundsHeight) {
                return y - boundsHeight / 2;
            }
        },
        BOTTOM {
            @Override
            public double positionY(double y, double boundsHeight) {
                return y - boundsHeight;
            }
        };

        public abstract double positionY(double y, double boundsHeight);
    }
    public enum HAlignment {
        LEFT {
            @Override
            public double positionX(double x, double boundsWidth) {
                return x;
            }
        },
        CENTER {
            @Override
            public double positionX(double x, double boundsWidth) {
                return x - boundsWidth / 2;
            }
        },
        RIGHT {
            @Override
            public double positionX(double x, double boundsWidth) {
                return x - boundsWidth;
            }
        };

        public abstract double positionX(double x, double boundsWidth);
    }

    private final VAlignment vAlignment;
    private final HAlignment hAlignment;

    public TextAnchor(VAlignment vAlignment, HAlignment hAlignment) {
        this.vAlignment = vAlignment;
        this.hAlignment = hAlignment;
    }

    public VAlignment getVAlignment() {
        return this.vAlignment;
    }
    public HAlignment getHAlignment() {
        return this.hAlignment;
    }

    public Point2d position(Point2d point, Rectangle2d bounds) {
        return new Point2d(this.hAlignment.positionX(point.getX(), bounds.getWidth()),
                this.vAlignment.positionY(point.getY(), bounds.getHeight()));
    }
}
