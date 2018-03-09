package com.dgrissom.masslibrary.math.geom.r2;

import com.dgrissom.masslibrary.math.geom.r2.polarfunction.PolarFunction;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.io.File;
import java.io.IOException;

public class PolarPlot {
    private Image image;
    private double pixelsPerUnit;

    public PolarPlot(int width, int height, double pixelsPerUnit) {
        this.image = new Image(width, height);
        this.pixelsPerUnit = pixelsPerUnit;
        this.image.antialias(true);
    }

    public void renderBackground() {
        this.image.fill(RGBColor.WHITE);
    }
    public void renderGrid() {
        double halfImageDiagonal = this.image.bounds().diagonal().length() / 2;

        Point2d center = new Point2d(this.image.getWidth() / 2, this.image.getHeight() / 2);
        // draw angle marking lines
        for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 12) {
            LineSegment2d line = LineSegment2d.from(center, theta, halfImageDiagonal);
            this.image.draw(line, RGBColor.LIGHT_GRAY);
        }

        // draw magnitude marking lines
        for (double r = 1; r < halfImageDiagonal / this.pixelsPerUnit; r++)
            this.image.draw(new Circle2d(center, r * this.pixelsPerUnit), RGBColor.LIGHT_GRAY);

        // horizontal and vertical axes
        this.image.draw(new LineSegment2d(0, this.image.getHeight() / 2,
                this.image.getWidth(), this.image.getHeight() / 2), RGBColor.BLACK);
        this.image.draw(new LineSegment2d(this.image.getWidth() / 2, 0,
                this.image.getWidth() / 2, this.image.getHeight()), RGBColor.BLACK);
    }

    public void plot(PolarFunction function) {
        plot(function, 0, Math.PI * 2);
    }
    public void plot(PolarFunction function, double angleEnd) {
        plot(function, 0, angleEnd);
    }
    public void plot(PolarFunction function, double angleStart, double angleEnd) {
        plot(function, angleStart, angleEnd, Math.PI / Math.pow(2, 16));
    }
    public void plot(PolarFunction function, double angleStart, double angleEnd, double angleStep) {
        Point2d center = new Point2d(this.image.getWidth() / 2, this.image.getHeight() / 2);
        for (double angle = angleStart; angle < angleEnd; angle += angleStep) {
            double magnitude = function.magnitude(angle) * this.pixelsPerUnit;
            PolarCoordinates polar = new PolarCoordinates(magnitude, angle);
            Point2d point = polar.toCartesian();
            point = point.multiply(1, -1).add(center);
            this.image.draw(point, RGBColor.BLUE);
        }
    }

    public void save(File file) throws IOException {
        this.image.save(file);
    }
}
