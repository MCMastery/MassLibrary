package com.dgrissom.masslibrary.rendering.plot;

import com.dgrissom.masslibrary.math.geom.r2.Circle2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

public class NodeStyle {
    public enum NodeType {
        CIRCLE {
            @Override
            public void render(Point2d point, double size, RGBColor color, Image image) {
                image.fill(new Circle2d(point, size / 2), color);
            }
        },
        SQUARE {
            @Override
            public void render(Point2d point, double size, RGBColor color, Image image) {
                image.fill(Rectangle2d.from(point, size, size), color);
            }
        };

        public abstract void render(Point2d point, double size, RGBColor color, Image image);
    }

    private final NodeType type;
    private final double size;
    private final RGBColor color;

    public NodeStyle(NodeType type, double size, RGBColor color) {
        this.type = type;
        this.size = size;
        this.color = color;
    }

    public NodeType getType() {
        return this.type;
    }
    public double getSize() {
        return this.size;
    }
    public RGBColor getColor() {
        return this.color;
    }

    public void render(Point2d point, Image image) {
        this.type.render(point, this.size, this.color, image);
    }
}
