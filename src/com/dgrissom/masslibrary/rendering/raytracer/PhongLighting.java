package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://en.wikipedia.org/wiki/Phong_reflection_model
public class PhongLighting {
    private List<PointLight> lights;
    private Point3d viewer;

    public PhongLighting() {
        this.lights = new ArrayList<>();
        this.viewer = Point3d.origin();
    }

    public List<PointLight> getLights() {
        return this.lights;
    }
    public void add(PointLight... lights) {
        this.lights.addAll(Arrays.asList(lights));
    }
    public Point3d getViewer() {
        return this.viewer;
    }
    public void setViewer(Point3d viewer) {
        this.viewer = viewer;
    }

    public RGBColor calculate(RayHit hit) {
        Material m = hit.getObject().getMaterial();
        double ks = m.getSpecularReflectionRatio();
        double kd = m.getDiffuseReflectionRatio();
        double alpha = m.getShininess();
        Vector3d n = hit.getNormal();
        Vector3d v = Vector3d.direction(hit.getPoint(), this.viewer);

        double sum = 0;
        for (PointLight light : this.lights) {
            double is = light.getSpecular();
            double id = light.getDiffuse();

            Vector3d lm = Vector3d.direction(hit.getPoint(), light.getPosition());
            // perfect reflected light ray
            // rm = 2(Lm dot N) * N - Lm
            Vector3d rm = n.multiply(2 * (lm.dotProduct(n))).subtract(lm);
            sum += kd * (lm.dotProduct(n)) * id + ks * Math.pow(rm.dotProduct(v), alpha) * is;
        }

        double ia = 1;
        return m.getAmbient().multiply(ia + sum);
    }
}
