package com.dgrissom.masslibrary.math.geom;

import com.dgrissom.masslibrary.math.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompositeTransform implements Transform {
    private final List<Transform> transforms;

    public CompositeTransform(Transform... transforms) {
        this(Arrays.asList(transforms));
    }
    public CompositeTransform(List<Transform> transforms) {
        this.transforms = Collections.unmodifiableList(transforms);
    }

    public List<Transform> getTransforms() {
        return this.transforms;
    }
    public CompositeTransform add(Transform transform) {
        List<Transform> newTransforms = new ArrayList<>(this.transforms);
        newTransforms.add(transform);
        return new CompositeTransform(newTransforms);
    }
    public CompositeTransform remove(Transform transform) {
        List<Transform> newTransforms = new ArrayList<>(this.transforms);
        newTransforms.remove(transform);
        return new CompositeTransform(newTransforms);
    }

    @Override
    public Matrix matrix() {
        return matrix(this.transforms);
    }

    static Matrix matrix(List<? extends Transform> transforms) {
        Matrix[] matrices = new Matrix[transforms.size()];
        int size = -1;
        for (int i = 0; i < matrices.length; i++) {
            matrices[i] = transforms.get(i).matrix();
            if (size < 0)
                size = matrices[i].getRows();

            // also catches where a matrix isn't square
            if (matrices[i].getRows() != size || matrices[i].getColumns() != size)
                throw new IllegalArgumentException("matrix is either not square not not the same size as others");
        }

        Matrix matrix = Matrix.identity(size);
        // multiply in reverse for correct order
        for (int i = matrices.length - 1; i >= 0; i--)
            matrix = matrix.multiply(matrices[i]);
        return matrix;
    }
}
