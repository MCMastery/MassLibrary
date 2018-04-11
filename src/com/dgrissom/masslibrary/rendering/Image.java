package com.dgrissom.masslibrary.rendering;

import com.dgrissom.masslibrary.math.geom.r2.Circle2d;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.color.Color;
import com.dgrissom.masslibrary.rendering.color.ColorSpace;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private final BufferedImage image;
    private final Graphics2D g2d;
    private ColorSpace colorSpace;

    public Image(int width, int height) {
        this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }
    public Image(int width, int height, boolean alpha) {
        this(new BufferedImage(width, height, alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB));
    }
    public Image(BufferedImage image) {
        this.image = image;
        this.g2d = this.image.createGraphics();
        this.colorSpace = ColorSpace.SRGB;
        antialias(true);
    }

    public BufferedImage getImage() {
        return this.image;
    }
    public Graphics2D getGraphics() {
         return this.g2d;
    }
    public ColorSpace getColorSpace() {
        return this.colorSpace;
    }
    public void setColorSpace(ColorSpace colorSpace) {
        this.colorSpace = colorSpace;
    }
    public double getWidth() {
        return this.image.getWidth();
    }
    public double getHeight() {
        return this.image.getHeight();
    }
    public Rectangle2d bounds() {
        return new Rectangle2d(0, 0, this.image.getWidth(), this.image.getHeight());
    }

    public RGBColor getColor(int x, int y) {
        int rgb = this.image.getRGB(x, y);
        return RGBColor.fromRGB(rgb);
    }
    public void setColor(Color color) {
        RGBColor rgb = color.toRGB(this.colorSpace).scaleToRange();
        this.g2d.setColor(new java.awt.Color((float) rgb.getR(), (float) rgb.getG(),
                (float) rgb.getB(), (float) rgb.getA()));
    }

    public void antialias(boolean antialias) {
        this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                antialias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public void dispose() {
        this.g2d.dispose();
    }

    public void fill(Color color) {
        fill(bounds(), color);
    }

    // do this so transforms affect the point (instead of setRGB)
    public void draw(Point2d point, Color color) {
        draw(new LineSegment2d(point, point), color);
    }
    public void draw(LineSegment2d line, Color color) {
        setColor(color);
        this.g2d.drawLine((int) Math.round(line.getX1()), (int) Math.round(line.getY1()),
                (int) Math.round(line.getX2()), (int) Math.round(line.getY2()));
    }

    public void draw(Polygon polygon, Color color) {
        for (LineSegment2d side : polygon.sides())
            draw(side, color);
    }
    public void fill(Polygon polygon, Color color) {
        setColor(color);
        PointSet2d vertices = polygon.vertices();
        int[] xPoints = new int[vertices.size()];
        int[] yPoints = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            Point2d vertex = vertices.get(i);
            xPoints[i] = (int) Math.round(vertex.getX());
            yPoints[i] = (int) Math.round(vertex.getY());
        }
        this.g2d.fillPolygon(xPoints, yPoints, vertices.size());
    }

    public void draw(Circle2d circle, Color color) {
        setColor(color);
        this.g2d.drawOval((int) Math.round(circle.getCenter().getX() - circle.getRadius()),
                (int) Math.round(circle.getCenter().getY() - circle.getRadius()),
                (int) Math.round(circle.diameter()), (int) Math.round(circle.diameter()));
    }
    public void fill(Circle2d circle, Color color) {
        setColor(color);
        this.g2d.fillOval((int) Math.round(circle.getCenter().getX() - circle.getRadius()),
                (int) Math.round(circle.getCenter().getY() - circle.getRadius()),
                (int) Math.round(circle.diameter()), (int) Math.round(circle.diameter()));
    }

    public void draw(Image image, Point2d position) {
        this.g2d.drawImage(image.getImage(), (int) Math.round(position.getX()), (int) Math.round(position.getY()), null);
    }

    public Rectangle2d textBounds(String text) {
        java.awt.geom.Rectangle2D awtRect = this.g2d.getFontMetrics().getStringBounds(text, this.g2d);
        return new Rectangle2d(awtRect.getWidth(), awtRect.getHeight());
    }
    public void draw(String text, Point2d position, Color color, TextAnchor anchor, double fontSize) {
        setColor(color);
        this.g2d.setFont(this.g2d.getFont().deriveFont((float) fontSize));

        if (anchor != null) {
            Rectangle2d bounds = textBounds(text);
            position = anchor.position(position, bounds);
        }

        // since java assumes we want position to be at baseline, not top of text
        FontMetrics fm = this.g2d.getFontMetrics();
        double offset = fm.getAscent() - fm.getDescent() - fm.getLeading();
        this.g2d.drawString(text, (int) Math.round(position.getX()),
                (int) Math.round(position.getY() + offset));
    }


    public void save(File file) throws IOException {
        ImageIO.write(this.image, "png", file);
    }
    public static Image load(File file) throws IOException {
        return new Image(ImageIO.read(file));
    }
}
