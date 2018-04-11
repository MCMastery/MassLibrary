package com.dgrissom.masslibrary.tests;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;
import com.dgrissom.masslibrary.math.statistics.SimpleLinearRegression;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import com.dgrissom.masslibrary.rendering.plot.NodeStyle;
import com.dgrissom.masslibrary.rendering.plot.Scatterplot;

import java.io.File;
import java.io.IOException;

public class ScatterplotTest {
    public static void main(String[] args) throws IOException {
        Scatterplot plot = new Scatterplot(800, 800, 100,
                "Scatterplot Test", "Vertical", "Horizontal",
                new NodeStyle(NodeStyle.NodeType.SQUARE, 5, RGBColor.BLUE));
        plot.initRender();

        PointSet2d data = new PointSet2d(
                new Point2d(1, 1),
                new Point2d(2, 2),
                new Point2d(3, 4),
                new Point2d(2.5, 1)
        );
        plot.plot(data);
        SimpleLinearRegression slr = new SimpleLinearRegression(new PointSet2d(data));
        plot.plot(slr.calculate());
        plot.save(new File("renderings/plots/scatterplot.png"));
    }
}
