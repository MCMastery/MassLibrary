package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import com.dgrissom.masslibrary.rendering.raytracer.objects.PlaneObject;
import com.dgrissom.masslibrary.rendering.raytracer.objects.SphereObject;

import java.io.File;
import java.io.IOException;

public class RayTracerTesting {
    private RayTracerTesting() {}

    public static void main(String[] args) throws IOException {
        RayTracer rayTracer = new RayTracer();
        rayTracer.setSupersampleSize(4);

        Material m1 = new Material(new RGBColor(0.1, 0.1, 0.1), 0.5, 0.5, 0.5);
        PlaneObject plane = new PlaneObject(new Point3d(0, -0.1, 0), new Vector3d(0, -1, 0), m1);
        rayTracer.add(plane);

        Material m2 = new Material(new RGBColor(0.05, 0.1, 0.2), 0.5, 0.5, 0.5);
        SphereObject sphere = new SphereObject(new Point3d(-1, 0, 3), 0.5, m2);
        rayTracer.add(sphere);

        PointLight light = new PointLight(new Point3d(1, -1, 0), 5, 2);
        rayTracer.getLighting().add(light);

        Image image = rayTracer.render();
        image.save(new File("renderings/raytracer/3.png"));
    }
}
