package com.dgrissom.masslibrary.rendering.plot;

import com.dgrissom.masslibrary.math.GeneralMath;
import com.dgrissom.masslibrary.math.functions.LinearFunction;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.TextAnchor;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Collection;

// https://en.wikipedia.org/wiki/Scatter_plot
public class Scatterplot extends Plot {
    private PlotTransform transform;
    private final NodeStyle nodeStyle;
    private final String verticalAxisName, horizontalAxisName;

    public Scatterplot(int width, int height, double pixelsPerUnit, String title, String verticalAxisName,
                       String horizontalAxisName, NodeStyle nodeStyle) {
        super(width, height, title);
        this.verticalAxisName = verticalAxisName;
        this.horizontalAxisName = horizontalAxisName;
        this.nodeStyle = nodeStyle;
        this.transform = new PlotTransform(new Point2d(getMargin(), height - getMargin()),
                PlotTransform.HorizontalAxisDirection.RIGHT, PlotTransform.VerticalAxisDirection.UP, pixelsPerUnit);
        antialias(true);
    }

    public void plot(Point2d... points) {
        plot(Arrays.asList(points));
    }
    public void plot(Collection<Point2d> points) {
        Shape clipBefore = clipPlot();
        for (Point2d point : points) {
            point = this.transform.transform(point);
            this.nodeStyle.render(point, this);
        }
        getGraphics().setClip(clipBefore);
    }
    public void plot(LinearFunction function) {
        Shape clipBefore = clipPlot();
        // just an arbitrary length so we know it will be long enough...
        //todo not the best way to do this probably
        LineSegment2d line = LineSegment2d.from(function, new Point2d(0, function.getYIntercept()), 10000);
        line = line.setStart(this.transform.transform(line.getStart()));
        line = line.setEnd(this.transform.transform(line.getEnd()));
        draw(line, RGBColor.BLACK);
        getGraphics().setClip(clipBefore);
    }

    public void initRender() {
        renderBackground();
        renderAxes();
        renderTitle();

        //todo better way of doing this
        // vertical axis name
        Font originalFont = getGraphics().getFont();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(270), getWidth() / 2, getHeight() / 2);
        Font rotatedFont = originalFont.deriveFont(transform);
        getGraphics().setFont(rotatedFont);
        // + bounds.getWidth / 2 to center
        // (remember we are rotated)
        Rectangle2d bounds = textBounds(this.verticalAxisName);
        //todo this isn't centered!!!
        draw(this.verticalAxisName, new Point2d(getMargin() / 2, -getHeight() / 2 + bounds.getWidth() / 2),
                RGBColor.BLACK, null, 12);
        getGraphics().setFont(originalFont);

        // horizontal axis name
        draw(this.horizontalAxisName, new Point2d(getWidth() / 2, getHeight() - getMargin() / 2),
                RGBColor.BLACK, new TextAnchor(TextAnchor.VAlignment.CENTER, TextAnchor.HAlignment.CENTER), 12);
    }

    private void renderAxes() {
        Rectangle2d plotBounds = plotBounds();

        for (double x = plotBounds.left() + this.transform.getPixelsPerUnit(); x < plotBounds.right();
             x += this.transform.getPixelsPerUnit()) {
            double yStart = this.transform.getOrigin().getY();
            double yEnd;
            double textY;
            TextAnchor.VAlignment textVAlignment;
            // axis is on bottom of plot
            if (yStart == plotBounds.bottom()) {
                yEnd = yStart + 4;
                textY = yEnd + 2;
                textVAlignment = TextAnchor.VAlignment.TOP;
            } else if (yStart == plotBounds.top()) {
                yEnd = yStart - 4;
                textY = yEnd - 2;
                textVAlignment = TextAnchor.VAlignment.BOTTOM;
            } else {
                // axis is somewhere else... center it
                yEnd = yStart + 2;
                yStart -= 2;
                textY = yEnd + 2;
                textVAlignment = TextAnchor.VAlignment.CENTER;
            }
            draw(new LineSegment2d(x, yStart, x, yEnd), RGBColor.BLACK);

            double graphX = (x - plotBounds.left()) / this.transform.getPixelsPerUnit();
            Point2d textPosition = new Point2d(x, textY);
            String text = String.valueOf(graphX);
            if (GeneralMath.isInt(graphX))
                text = text.replace(".0", "");
            draw(text, textPosition, RGBColor.BLACK,
                    new TextAnchor(textVAlignment, TextAnchor.HAlignment.CENTER), 10);
        }

        for (double y = plotBounds.top() + this.transform.getPixelsPerUnit(); y < plotBounds.bottom();
             y += this.transform.getPixelsPerUnit()) {
            double xStart = this.transform.getOrigin().getX();
            double xEnd;
            // axis is on right side of plot
            if (xStart == plotBounds.right())
                xEnd = xStart + 4;
            else if (xStart == plotBounds.left())
                xEnd = xStart - 4;
                // axis is somewhere else... center it
            else {
                xEnd = xStart + 2;
                xStart -= 2;
            }
            draw(new LineSegment2d(xStart, y, xEnd, y), RGBColor.BLACK);
        }
    }
}
