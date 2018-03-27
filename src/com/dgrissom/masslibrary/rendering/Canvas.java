package com.dgrissom.masslibrary.rendering;

import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.rendering.color.Color;
import com.dgrissom.masslibrary.rendering.color.ColorSpace;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class Canvas {
    private final javafx.scene.canvas.Canvas canvas;
    private final GraphicsContext gc;
    private final ColorSpace colorSpace;

    public Canvas(javafx.scene.canvas.Canvas canvas) {
        this.canvas = canvas;
        this.gc = this.canvas.getGraphicsContext2D();
        this.colorSpace = ColorSpace.SRGB;
    }

    public javafx.scene.canvas.Canvas getCanvas() {
        return this.canvas;
    }
    public GraphicsContext getGc() {
        return this.gc;
    }

    public void setFill(Color fill) {
        RGBColor rgb = fill.toRGB(this.colorSpace);
        this.gc.setFill(new javafx.scene.paint.Color(rgb.getR(), rgb.getG(), rgb.getB(), rgb.getA()));
    }
    public void setStroke(Color stroke) {
        RGBColor rgb = stroke.toRGB(this.colorSpace);
        this.gc.setStroke(new javafx.scene.paint.Color(rgb.getR(), rgb.getG(), rgb.getB(), rgb.getA()));
    }

    public void stroke(LineSegment2d line, Color color) {
        setStroke(color);
        this.gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void stroke(Polygon polygon, Color color) {
        for (LineSegment2d side : polygon.getSides())
            stroke(side, color);
    }
    public void fill(Polygon polygon, Color color) {
        setFill(color);
        List<Point2d> vertices = polygon.vertices();
        double[] xPoints = new double[vertices.size()];
        double[] yPoints = new double[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            Point2d vertex = vertices.get(i);
            xPoints[i] = vertex.getX();
            yPoints[i] = vertex.getY();
        }
        this.gc.fillPolygon(xPoints, yPoints, vertices.size());
    }
}
