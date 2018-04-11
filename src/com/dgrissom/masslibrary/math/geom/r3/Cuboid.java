package com.dgrissom.masslibrary.math.geom.r3;

import java.util.Arrays;
import java.util.List;

public class Cuboid implements Polyhedron {
    private final Point3d center;
    private final double width, height, length;

    public Cuboid(Point3d center, double width, double height, double length) {
        this.center = center;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public List<Face> faces() {
        Face top = new Face(this.center.add(-this.width / 2, this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, this.height / 2, -this.length / 2),
                          this.center.add(-this.width / 2, this.height / 2, -this.length / 2));
        Face bottom = new Face(this.center.add(-this.width / 2, -this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, -this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, -this.height / 2, -this.length / 2),
                          this.center.add(-this.width / 2, -this.height / 2, -this.length / 2));

        Face back = new Face(this.center.add(-this.width / 2, this.height / 2, -this.length / 2),
                          this.center.add(this.width / 2, this.height / 2, -this.length / 2),
                          this.center.add(this.width / 2, -this.height / 2, -this.length / 2),
                          this.center.add(-this.width / 2, -this.height / 2, -this.length / 2));
        Face front = new Face(this.center.add(-this.width / 2, this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, -this.height / 2, this.length / 2),
                          this.center.add(-this.width / 2, -this.height / 2, this.length / 2));

        Face left = new Face(this.center.add(-this.width / 2, this.height / 2, -this.length / 2),
                          this.center.add(-this.width / 2, this.height / 2, this.length / 2),
                          this.center.add(-this.width / 2, -this.height / 2, this.length / 2),
                          this.center.add(-this.width / 2, -this.height / 2, -this.length / 2));
        Face right = new Face(this.center.add(this.width / 2, this.height / 2, -this.length / 2),
                          this.center.add(this.width / 2, this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, -this.height / 2, this.length / 2),
                          this.center.add(this.width / 2, -this.height / 2, -this.length / 2));

        return Arrays.asList(top, bottom, back, front, left, right);
    }

    public static Cuboid cube(Point3d center, double sideLength) {
        return new Cuboid(center, sideLength, sideLength, sideLength);
    }
}
