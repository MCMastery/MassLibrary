package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RayTracer {
    private List<RayTraceable> objects;
    private PhongLighting lighting;
    private int width, height, supersampleSize;
    private double imageZ;

    public RayTracer() {
        this.objects = new ArrayList<>();
        this.lighting = new PhongLighting();
        this.width = 800;
        this.height = 800;
        this.supersampleSize = 1;
        this.imageZ = 1;
    }

    public List<RayTraceable> getObjects() {
        return this.objects;
    }
    public void add(RayTraceable... objects) {
        this.objects.addAll(Arrays.asList(objects));
    }
    public PhongLighting getLighting() {
        return this.lighting;
    }
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getSupersampleSize() {
        return this.supersampleSize;
    }
    public void setSupersampleSize(int supersampleSize) {
        this.supersampleSize = supersampleSize;
    }
    public double getImageZ() {
        return this.imageZ;
    }
    public void setImageZ(double imageZ) {
        this.imageZ = imageZ;
    }

    private Image supersample(Image image) {
        Image supersampled = new Image(this.width, this.height);
        for (int x = 0; x < supersampled.getWidth(); x++) {
            for (int y = 0; y < supersampled.getHeight(); y++) {
                RGBColor avg = RGBColor.BLACK;
                for (int subX = 0; subX < this.supersampleSize; subX++) {
                    for (int subY = 0; subY < this.supersampleSize; subY++) {
                        int imageX = x * this.supersampleSize + subX;
                        int imageY = y * this.supersampleSize + subY;
                        avg = avg.add(image.getColor(imageX, imageY));
                    }
                }
                avg = avg.divide(this.supersampleSize * this.supersampleSize);
                supersampled.draw(new Point2d(x, y), avg);
            }
        }
        return supersampled;
    }

    public Image render() {
        Image image = new Image(this.width * this.supersampleSize, this.height * this.supersampleSize);
        image.fill(RGBColor.BLACK);

        Point3d camera = this.lighting.getViewer();
        double imageZ = 1;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                // ray starting from camera going to the pixel
                Point3d rayToward = new Point3d(x, y, 0)
                        .subtract(image.getWidth() / 2.0, image.getHeight() / 2.0, 1)
                        .divide(image.getWidth(), image.getHeight(), 1)
                        .setZ(imageZ);
                rayToward = camera.add(rayToward);

                Ray3d ray = camera.rayTo(rayToward);
                // get closest hit
                RayHit hit = null;
                double hitDistanceSq = 0;
                for (RayTraceable object : this.objects) {
                    RayHit rh = object.hit(ray);
                    if (rh == null)
                        continue;

                    double distSq = camera.distanceSquared(rh.getPoint());
                    if (hit == null || distSq < hitDistanceSq) {
                        hit = rh;
                        hitDistanceSq = distSq;
                    }
                }

                // ray doesn't hit anything! don't color anything
                if (hit == null)
                    continue;

                RGBColor color = this.lighting.calculate(hit);
                image.draw(new Point2d(x, y), color);
            }
        }

        if (this.supersampleSize == 1)
            return image;

        // supersample
        return supersample(image);
    }
}
