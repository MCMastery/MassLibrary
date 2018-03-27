package com.dgrissom.masslibrary;

import com.dgrissom.masslibrary.math.geom.r2.Circle2d;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.TextAnchor;
import com.dgrissom.masslibrary.rendering.color.*;

import java.io.File;
import java.io.IOException;

public final class ColorTemperatures {
    private static final double START_CCT = 2500, END_CCT = 8500;
    private static final double BOTTOM_PADDING = 20;
    private static final double TICK_LENGTH = 5;

    private ColorTemperatures() {}

    public static void main(String[] args) throws IOException {
        Image image = new Image(600, 200, false);
        image.antialias(true);
        for (double x = 0; x < image.getWidth(); x++) {
            double cct = cct(image, x);
            Color color = new XYYColor(ChromaticityCoordinates.from(cct), 1);
            image.draw(new LineSegment2d(x, 0, x, image.getHeight()), color);
        }

        // grid
        double step = 500;
        for (double cct = START_CCT + step; cct < END_CCT; cct += step) {
            double x = x(image, cct);
            image.draw(new LineSegment2d(x, image.getHeight() - TICK_LENGTH, x, image.getHeight()), RGBColor.BLACK);
            image.draw(new LineSegment2d(x, 0, x, image.getHeight()), RGBColor.BLACK.setA(0.1));
            if (cct % 1000 == 0) {
                image.draw(String.valueOf((int) cct), new Point2d(x, image.getHeight() - TICK_LENGTH + 4), RGBColor.BLACK,
                        new TextAnchor(TextAnchor.VAlignment.BOTTOM, TextAnchor.HAlignment.CENTER), 9);
            }
        }

        drawLabels(image);

        image.save(new File("renderings/color/temperatures.png"));
    }

    private static double cct(Image image, double x) {
        double fraction = x / image.getWidth();
        return fraction * (END_CCT - START_CCT) + START_CCT;
    }
    private static double x(Image image, double cct) {
        double fraction = (cct - START_CCT) / (END_CCT - START_CCT);
        return fraction * image.getWidth();
    }

    private static void drawLabels(Image image) {
        int rows = 7;
        double delta = (image.getHeight() - BOTTOM_PADDING) / (rows + 1);

        drawAndLabel(image, Whitepoint.A, delta * 1, "A");
        drawAndLabel(image, Whitepoint.B, delta * 1, "B");
        drawAndLabel(image, Whitepoint.C, delta * 1, "C");

        drawAndLabel(image, Whitepoint.D50, delta * 2, "D50");
        drawAndLabel(image, Whitepoint.D55, delta * 2, "D55");
        drawAndLabel(image, Whitepoint.D65, delta * 2, "D65");
        drawAndLabel(image, Whitepoint.D75, delta * 2, "D75");

        drawAndLabel(image, Whitepoint.E, delta * 3, "E");

        drawAndLabel(image, Whitepoint.F1, delta * 4, "F1");
        drawAndLabel(image, Whitepoint.F2, delta * 4, "F2");
        drawAndLabel(image, Whitepoint.F3, delta * 4, "F3");
        drawAndLabel(image, Whitepoint.F4, delta * 5, "F4");
        drawAndLabel(image, Whitepoint.F5, delta * 5, "F5");
        drawAndLabel(image, Whitepoint.F6, delta * 5, "F6");
        drawAndLabel(image, Whitepoint.F7, delta * 6, "F7");
        drawAndLabel(image, Whitepoint.F8, delta * 6, "F8");
        drawAndLabel(image, Whitepoint.F9, delta * 6, "F9");
        drawAndLabel(image, Whitepoint.F10, delta * 7, "F10");
        drawAndLabel(image, Whitepoint.F11, delta * 7, "F11");
        drawAndLabel(image, Whitepoint.F12, delta * 7, "F12");
    }
    private static void drawAndLabel(Image image, XYYColor color, double y, String label) {
        double x = x(image, color.getCoordinates().cct());
        image.fill(new Circle2d(x, y, 4), color);
        image.draw(new Circle2d(x, y, 4), RGBColor.BLACK.setA(0.5));
        image.draw(label, new Point2d(x + 8, y + 2), RGBColor.BLACK,
                new TextAnchor(TextAnchor.VAlignment.CENTER, TextAnchor.HAlignment.LEFT), 11);
    }
}
