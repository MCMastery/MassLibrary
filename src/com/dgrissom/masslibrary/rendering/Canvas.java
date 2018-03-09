package com.dgrissom.masslibrary.rendering;

import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Canvas {
    private final javafx.scene.canvas.Canvas canvas;
    private final GraphicsContext gc;

    public Canvas(javafx.scene.canvas.Canvas canvas) {
        this.canvas = canvas;
        this.gc = this.canvas.getGraphicsContext2D();
    }

    public javafx.scene.canvas.Canvas getCanvas() {
        return this.canvas;
    }
    public GraphicsContext getGc() {
        return this.gc;
    }

    public void setFill(RGBColor fill) {
        this.gc.setFill(new Color(fill.getRed(), fill.getGreen(), fill.getBlue(), fill.getOpacity()));
    }
    public void setStroke(RGBColor stroke) {
        this.gc.setStroke(new Color(stroke.getRed(), stroke.getGreen(), stroke.getBlue(), stroke.getOpacity()));
    }

    public void stroke(LineSegment2d line, RGBColor color) {
        setStroke(color);
        this.gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void stroke(Polygon polygon, RGBColor color) {
        for (LineSegment2d side : polygon.getSides())
            stroke(side, color);
    }
    public void fill(Polygon polygon, RGBColor color) {
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
