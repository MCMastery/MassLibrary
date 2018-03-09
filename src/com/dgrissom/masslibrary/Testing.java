package com.dgrissom.masslibrary;

import com.dgrissom.masslibrary.math.geom.r2.polygon.Polygon;
import com.dgrissom.masslibrary.math.geom.r2.polygon.PolygonBooleanOperations;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.math.geom.r2.transform.Rotate2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.io.File;
import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException {
        Image img = new Image(800, 800);
        img.fill(RGBColor.WHITE);
        Rectangle2d r = new Rectangle2d(0, 0, 2500, 100);
        img.fill(r, RGBColor.BLUE);
        Polygon rotated = r.transform(Rotate2d.fromDegrees(-45));
        img.fill(rotated, RGBColor.BLACK);
        Polygon and = PolygonBooleanOperations.and(r, rotated);
        img.fill(and, RGBColor.WHITE);
        img.save(new File("renderings/transformations/rotate.png"));
    }
}
