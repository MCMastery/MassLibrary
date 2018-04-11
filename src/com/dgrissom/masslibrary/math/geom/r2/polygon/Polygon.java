package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;
import com.dgrissom.masslibrary.math.geom.Transformable;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.PointSet2d;

import java.util.List;

public interface Polygon extends Transformable {
    List<LineSegment2d> sides();

    default int vertexCount() {
        return sides().size();
    }

    // remember there may be duplicates since two different lines may start at the same place
    default PointSet2d vertices() {
        PointSet2d vertices = new PointSet2d();
        for (LineSegment2d side : sides())
            if (!vertices.contains(side.getStart()))
                vertices.add(side.getStart());
        return vertices;
    }

    // works for simple polygons
    default double area() {
        // shoelace formula
        // https://en.wikipedia.org/wiki/Shoelace_formula
        PointSet2d vertices = vertices();
        double sum = 0;
        for (int i = 0; i < vertexCount() - 1; i++) {
            Point2d v1 = vertices.get(i);
            Point2d v2 = vertices.get(i + 1);
            Matrix m = Matrix.square(
                    v1.getX(), v2.getX(),
                    v1.getY(), v2.getY()
            );
            sum += m.determinant();
        }
        return 0.5 * Math.abs(sum);
    }

    @Override
    default Polygon transform(Transform transform) {
        List<LineSegment2d> sides = sides();
        for (int i = 0; i < sides.size(); i++)
            sides.set(i, sides.get(i).transform(transform));
        return from(sides);
    }

    static Polygon from(List<LineSegment2d> sides) {
        return () -> sides;
    }
}
