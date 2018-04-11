package com.dgrissom.masslibrary.rendering;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;
import com.dgrissom.masslibrary.math.geom.Transformable;
import com.dgrissom.masslibrary.math.geom.r2.Circle2d;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.rendering.color.Color;
import com.dgrissom.masslibrary.rendering.color.ColorSpace;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

import java.awt.image.BufferedImage;

public class Canvas implements Transformable {
    private final javafx.scene.canvas.Canvas canvas;
    private final GraphicsContext gc;
    private ColorSpace colorSpace;

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
    public ColorSpace getColorSpace() {
        return this.colorSpace;
    }
    public void setColorSpace(ColorSpace colorSpace) {
        this.colorSpace = colorSpace;
    }

    public void setFill(Color fill) {
        RGBColor rgb = fill.toRGB(this.colorSpace);
        this.gc.setFill(new javafx.scene.paint.Color(rgb.getR(), rgb.getG(), rgb.getB(), rgb.getA()));
    }
    public void setStroke(Color stroke) {
        RGBColor rgb = stroke.toRGB(this.colorSpace);
        this.gc.setStroke(new javafx.scene.paint.Color(rgb.getR(), rgb.getG(), rgb.getB(), rgb.getA()));
    }

    // do this so transforms affect the point (instead of setRGB)
    public void draw(Point2d point, Color color) {
        stroke(new LineSegment2d(point, point), color);
    }
    public void stroke(LineSegment2d line, Color color) {
        setStroke(color);
        this.gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void stroke(Polygon polygon, Color color) {
        for (LineSegment2d side : polygon.sides())
            stroke(side, color);
    }
    public void fill(Polygon polygon, Color color) {
        setFill(color);
        PointSet2d vertices = polygon.vertices();
        double[] xPoints = new double[vertices.size()];
        double[] yPoints = new double[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            Point2d vertex = vertices.get(i);
            xPoints[i] = vertex.getX();
            yPoints[i] = vertex.getY();
        }
        this.gc.fillPolygon(xPoints, yPoints, vertices.size());
    }

    public void stroke(Circle2d circle, Color color) {
        setStroke(color);
        this.gc.strokeOval(circle.getCenter().getX() - circle.getRadius(),
                circle.getCenter().getY() - circle.getRadius(),
                circle.diameter(), circle.diameter());
    }
    public void fill(Circle2d circle, Color color) {
        setFill(color);
        this.gc.fillOval(circle.getCenter().getX() - circle.getRadius(),
                circle.getCenter().getY() - circle.getRadius(),
                circle.diameter(), Math.round(circle.diameter()));
    }
    
    public void draw(com.dgrissom.masslibrary.rendering.Image image, Point2d position) {
        draw(image.getImage(), position);
    }
    public void draw(BufferedImage image, Point2d position) {
        draw(SwingFXUtils.toFXImage(image, null), position);
    }
    public void draw(Image image, Point2d position) {
        this.gc.drawImage(image, position.getX(), position.getY());
    }

    @Override
    public Transformable transform(Transform transform) {
        Matrix m = transform.matrix();
        double tx = 0, ty = 0;
        if (m.getColumns() >= 3) {
            tx = m.get(0, 2);
            ty = m.get(1, 2);
        }
        // get first 2 rows and create affine transform
        Affine affine = Affine.affine(
                m.get(0, 0), m.get(1, 0),
                m.get(0, 1), m.get(1, 1),
                tx, ty
        );
        this.gc.transform(affine);
        return this;
    }
}
