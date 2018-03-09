package com.dgrissom.masslibrary.rendering.plot;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.TextAnchor;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.awt.*;

public class Plot extends Image {
    private final String title;
    private final double margin;

    public Plot(int width, int height, String title) {
        super(width, height);
        this.title = title;
        this.margin = 100;
    }

    public String getTitle() {
        return this.title;
    }
    public double getMargin() {
        return this.margin;
    }

    public Rectangle2d plotBounds() {
        return bounds().expand(-getMargin(), -getMargin());
    }
    // clips the image (us) so that things drawn will be clipped plotBounds
    public Shape clipPlot() {
        Rectangle2d plotBounds = plotBounds();
        Shape clipBefore = getGraphics().getClip();
        getGraphics().setClip((int) Math.round(plotBounds.getX()), (int) Math.round(plotBounds.getY()),
                (int) Math.round(plotBounds.getWidth()), (int) Math.round(plotBounds.getHeight()));
        return clipBefore;
    }

    public void renderBackground() {
        fill(RGBColor.WHITE);
        draw(plotBounds(), RGBColor.BLACK);
    }
    public void renderTitle() {
        // title
        draw(getTitle(), new Point2d(getWidth() / 2, getMargin() / 2), RGBColor.BLACK,
                new TextAnchor(TextAnchor.VAlignment.CENTER, TextAnchor.HAlignment.CENTER), 14);
    }
}
