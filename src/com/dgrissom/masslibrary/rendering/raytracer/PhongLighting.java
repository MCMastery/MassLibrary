package com.dgrissom.masslibrary.rendering.raytracer;

import com.dgrissom.masslibrary.math.geom.r3.Point3d;
import com.dgrissom.masslibrary.math.geom.r3.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://en.wikipedia.org/wiki/Phong_reflection_model
public class PhongLighting {
    private List<PointLight> lights;

    public PhongLighting() {
        this.lights = new ArrayList<>();
    }

    public List<PointLight> getLights() {
        return this.lights;
    }
    public void add(PointLight... lights) {
        this.lights.addAll(Arrays.asList(lights));
    }
    public void remove(PointLight... lights) {
        this.lights.removeAll(Arrays.asList(lights));
    }

    // point, viewer (camera)
    public double illumination(RayHit hit, Point3d camera) {
        Material m = hit.getObject().getMaterial();
        double ks = m.getSpecular();
        double kd = m.getDiffuse();
        double ka = m.getAmbient();
        double alpha = m.getShininess();

        Vector3d n = hit.normal();
        Vector3d v = Vector3d.direction(hit.getPoint(), camera);

        double sum = 0;
        for (PointLight light : this.lights) {
            Vector3d l = Vector3d.direction(hit.getPoint(), light.getPosition());
            double dotProd = l.dotProduct(n);
            // Rm = 2(L dot N)N - L
            Vector3d r = n.multiply(2 * dotProd).subtract(l);

            double id = light.getDiffuse();
            double is = light.getSpecular();

            sum += (kd * dotProd * id + ks * Math.pow(r.dotProduct(v), alpha) * is);
        }

        // ambient lighting
        double ia = 0.5;

        return ka * ia + sum;
    }
}
