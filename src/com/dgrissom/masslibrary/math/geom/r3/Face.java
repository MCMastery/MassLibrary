package com.dgrissom.masslibrary.math.geom.r3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Face {
    private final List<Point3d> vertices;

    public Face(Point3d... vertices) {
        this(Arrays.asList(vertices));
    }
    public Face(List<Point3d> vertices) {
        this.vertices = Collections.unmodifiableList(vertices);
    }

    public List<Point3d> getVertices() {
        return this.vertices;
    }

    public Point3d centroid() {
        Point3d centroid = Point3d.origin();
        if (this.vertices.size() == 0)
            return centroid;

        for (Point3d vertex : this.vertices)
            centroid = centroid.add(vertex);
        return centroid.divide(this.vertices.size());
    }

    public List<LineSegment3d> edges() {
        List<LineSegment3d> edges = new ArrayList<>();
        for (int i = 0; i < this.vertices.size(); i++) {
            // loop around so we also draw a line from end to start
            int j = (i == this.vertices.size() - 1) ? 0 : i + 1;
            edges.add(new LineSegment3d(this.vertices.get(i), this.vertices.get(j)));
        }
        return edges;
    }
}
