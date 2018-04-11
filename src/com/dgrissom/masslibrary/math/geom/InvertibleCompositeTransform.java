package com.dgrissom.masslibrary.math.geom;

import com.dgrissom.masslibrary.math.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InvertibleCompositeTransform implements InvertibleTransform {
    private final List<InvertibleTransform> transforms;

    public InvertibleCompositeTransform(InvertibleTransform... transforms) {
        this(Arrays.asList(transforms));
    }
    public InvertibleCompositeTransform(List<InvertibleTransform> transforms) {
        this.transforms = Collections.unmodifiableList(transforms);
    }

    public List<InvertibleTransform> getTransforms() {
        return this.transforms;
    }
    public InvertibleCompositeTransform add(InvertibleTransform transform) {
        List<InvertibleTransform> newTransforms = new ArrayList<>(this.transforms);
        newTransforms.add(transform);
        return new InvertibleCompositeTransform(newTransforms);
    }
    public InvertibleCompositeTransform remove(InvertibleTransform transform) {
        List<InvertibleTransform> newTransforms = new ArrayList<>(this.transforms);
        newTransforms.remove(transform);
        return new InvertibleCompositeTransform(newTransforms);
    }

    @Override
    public Matrix matrix() {
        return CompositeTransform.matrix(this.transforms);
    }
    @Override
    public InvertibleCompositeTransform inverse() {
        List<InvertibleTransform> reversed = new ArrayList<>(this.transforms);
        Collections.reverse(reversed);
        for (int i = 0; i < reversed.size(); i++)
            reversed.set(i, reversed.get(i).inverse());
        return new InvertibleCompositeTransform(reversed);
    }
}
