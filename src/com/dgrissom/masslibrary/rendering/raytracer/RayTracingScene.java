package com.dgrissom.masslibrary.rendering.raytracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RayTracingScene implements Iterable<RayTraceable> {
    private List<RayTraceable> objects;
    private PhongLighting lighting;

    public RayTracingScene() {
        this.objects = new ArrayList<>();
        this.lighting = new PhongLighting();
    }

    public List<RayTraceable> getObjects() {
        return this.objects;
    }
    public void add(RayTraceable... objects) {
        this.objects.addAll(Arrays.asList(objects));
    }
    public void remove(RayTraceable... objects) {
        this.objects.removeAll(Arrays.asList(objects));
    }
    public PhongLighting getLighting() {
        return this.lighting;
    }

    @Override
    public Iterator<RayTraceable> iterator() {
        return this.objects.iterator();
    }
}
