package com.dgrissom.masslibrary.math.geom.r2.polygon;

import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.Transform;
import com.dgrissom.masslibrary.math.geom.Transformable;
import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;

import java.util.ArrayList;
import java.util.List;

public interface Polygon extends Transformable {
    List<LineSegment2d> getSides();

    default Rectangle2d boundingBox() {
        double left = 0, top = 0, right = 0, bottom = 0;
        List<LineSegment2d> sides = getSides();
        for (int i = 0; i < sides.size(); i++) {
            Point2d vertex = sides.get(i).getStart();
            if (i == 0 || vertex.getX() < left)
                left = vertex.getX();
            if (i == 0 || vertex.getY() < top)
                top = vertex.getY();
            if (i == 0 || vertex.getX() > right)
                right = vertex.getX();
            if (i == 0 || vertex.getY() > bottom)
                bottom = vertex.getY();
        }
        return Rectangle2d.from(left, top, right, bottom);
    }

    default int vertexCount() {
        return getSides().size();
    }

    // remember there may be duplicates since two different lines may start at the same place
    default List<Point2d> vertices() {
        List<Point2d> vertices = new ArrayList<>();
        for (LineSegment2d side : getSides())
            if (!vertices.contains(side.getStart()))
                vertices.add(side.getStart());
        return vertices;
    }
    // average of all points
    default Point2d centroid() {
        Point2d avg = new Point2d(0, 0);
        List<Point2d> vertices = vertices();
        for (Point2d vertex : vertices)
            avg = avg.add(vertex);
        return avg.divide(vertices.size());
    }

    // only works for simple polygons
    default double area() {
        // shoelace formula
        // https://en.wikipedia.org/wiki/Shoelace_formula
        List<Point2d> vertices = vertices();
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
        List<Point2d> vertices = vertices();
        for (int i = 0; i < vertices.size(); i++)
            vertices.set(i, vertices.get(i).transform(transform));
        return fromVertices(vertices);
    }

    static Polygon from(List<LineSegment2d> sides) {
        return () -> sides;
    }
    static Polygon fromVertices(List<Point2d> vertices) {
        return from(sides(vertices));
    }

    // returns sides from vertices
    // same order as vertices list is given in
    static List<LineSegment2d> sides(List<Point2d> vertices) {
        List<LineSegment2d> sides = new ArrayList<>();
        Point2d last = null;
        // loop around to first
        for (int i = 0; i <= vertices.size(); i++) {
            // loop around to first
            int index = i;
            if (index == vertices.size())
                index = 0;

            Point2d vertex = vertices.get(index);
            if (last != null)
                sides.add(new LineSegment2d(last, vertex));
            last = vertex;
        }
        return sides;
    }
}
