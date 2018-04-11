package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.rendering.Image;

import java.io.File;
import java.io.IOException;

public class RayTracerTesting {
    private RayTracerTesting() {}

    public static void main(String[] args) throws IOException {
        RayTracingScene scene = new RayTracingScene();
        Material material = new Material(2, 1, 0, 2);
        scene.add(new SphereObject(new Point3d(0, 0, 1), 0.5, material));

        scene.getLighting().add(new PointLight(new Point3d(0.25, -0.25, 0), 5, 1));

        RayTracer rayTracer = new RayTracer();
        rayTracer.setScene(scene);
        rayTracer.setCamera(Point3d.origin());
        rayTracer.setImageCenter(new Point3d(0, 0, 0.25));
        rayTracer.setImageWidth(800);
        rayTracer.setImageHeight(800);

        Image image = rayTracer.render();
        image.save(new File("renderings/raytracer/b1.png"));
    }
}
