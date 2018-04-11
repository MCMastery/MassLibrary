package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Ray3d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.Color;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.util.ArrayList;
import java.util.List;

public class RayTracer {
    private RayTracingScene scene;
    private Point3d camera, imageCenter;
    private int imageWidth, imageHeight;

    public RayTracer() {
        this.scene = null;
        this.imageWidth = 400;
        this.imageHeight = 400;
        this.camera = Point3d.origin();
        // not camera position - this is the image plane location
        // where rays are sent from camera through image plane pixels
        this.imageCenter = new Point3d(0, 0, 1);
    }

    public RayTracingScene getScene() {
        return this.scene;
    }
    public void setScene(RayTracingScene scene) {
        this.scene = scene;
    }
    public int getImageWidth() {
        return this.imageWidth;
    }
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
    public int getImageHeight() {
        return this.imageHeight;
    }
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
    public Point3d getImageCenter() {
        return this.imageCenter;
    }
    public void setImageCenter(Point3d imageCenter) {
        this.imageCenter = imageCenter;
    }
    public Point3d getCamera() {
        return this.camera;
    }
    public void setCamera(Point3d camera) {
        this.camera = camera;
    }

    public Image render() {
        Image image = new Image(this.imageWidth, this.imageHeight, false);
        for (int x = 0; x < this.imageWidth; x++) {
            for (int y = 0; y < this.imageHeight; y++) {
                double sceneX = (x - this.imageWidth / 2.0) / (double) this.imageWidth + this.imageCenter.getX();
                double sceneY = (y - this.imageHeight / 2.0) / (double) this.imageHeight + this.imageCenter.getY();
                double sceneZ = this.imageCenter.getZ();
                Ray3d ray = Ray3d.from(this.camera, new Point3d(sceneX, sceneY, sceneZ));

                List<RayHit> hits = new ArrayList<>();
                for (RayTraceable object : this.scene)
                    hits.add(object.hit(ray));
                // object.hit will return null if they never intersect
                hits.remove(null);

                // sort hits so closest is first
                hits.sort((o1, o2) -> {
                    double distSq1 = this.camera.distanceSquared(o1.getPoint());
                    double distSq2 = this.camera.distanceSquared(o2.getPoint());
                    return Double.compare(distSq1, distSq2);
                });

                // no hits
                if (hits.size() == 0)
                    continue;

                double illumination = this.scene.getLighting().illumination(hits.get(0), this.camera);
                if (illumination < 0)
                    continue;
                Color color = RGBColor.BLUE.multiply(illumination);
                image.draw(new Point2d(x, y), color);
            }
        }
        return image;
    }
}
