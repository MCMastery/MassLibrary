package com.dgrissom.masslibrary.math;

import java.util.Arrays;

// https://en.wikipedia.org/wiki/Matrix_(mathematics)
public class Matrix {
    private final double[] m;
    private final int rows, columns;

    public Matrix(int rows, int columns) {
        this(new double[rows * columns], rows, columns);
        fill(0);
    }
    public Matrix(double[] m, int rows, int columns) {
        this.m = m;
        this.rows = rows;
        this.columns = columns;
    }

    public double[] getM() {
        return this.m;
    }
    public int getRows() {
        return this.rows;
    }
    public int getColumns() {
        return this.columns;
    }

    private void fill(double value) {
        for (int i = 0; i < this.m.length; i++)
            this.m[i] = value;
    }
    private int index(int row, int column) {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns)
            throw new IndexOutOfBoundsException();
        return row * this.columns + column;
    }
    public double get(int row, int column) {
        return this.m[index(row, column)];
    }
    public void set(int row, int column, double value) {
        this.m[index(row, column)] = value;
    }
    public Matrix row(int row) {
        double[] r = new double[this.columns];
        for (int col = 0; col < this.columns; col++)
            r[col] = get(row, col);
        return Matrix.row(r);
    }
    public Matrix column(int column) {
        double[] c = new double[this.rows];
        for (int row = 0; row < this.rows; row++)
            c[row] = get(row, column);
        return Matrix.column(c);
    }

    public Matrix add(Matrix other) {
        if (this.rows != other.getRows() || this.columns != other.getColumns())
            throw new IllegalArgumentException("both matrices must be same size");
        Matrix added = new Matrix(this.rows, this.columns);
        for (int m = 0; m < this.rows; m++) {
            for (int n = 0; n < this.columns; n++) {
                int index = index(m, n);
                added.getM()[index] = this.m[index] + other.getM()[index];
            }
        }
        return added;
    }
    // https://en.wikipedia.org/wiki/Matrix_multiplication
    // for a n x m and an m x p matrix
    // (our rows = their columns)
    public Matrix multiply(Matrix other) {
        if (this.columns != other.getRows())
            throw new IllegalArgumentException("m1 must be (n x m) and m2 must be (m x p)");
        // result is an n x p matrix
        Matrix multiplied = new Matrix(this.rows, other.getColumns());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < this.columns; k++)
                    sum += get(i, k) * other.get(k, j);
                multiplied.set(i, j, sum);
            }
        }
        return multiplied;
    }
    public Matrix multiply(double scalar) {
        Matrix multiplied = new Matrix(this.rows, this.columns);
        for (int m = 0; m < this.rows; m++) {
            for (int n = 0; n < this.columns; n++) {
                int index = index(m, n);
                multiplied.getM()[index] = this.m[index] * scalar;
            }
        }
        return multiplied;
    }

    public Matrix transpose() {
        Matrix transposition = new Matrix(this.columns, this.rows);
        for (int m = 0; m < this.rows; m++)
            for (int n = 0; n < this.columns; n++)
                transposition.set(n, m, get(m, n));
        return transposition;
    }
    // https://en.wikipedia.org/wiki/Matrix_(mathematics)#Submatrix
    public Matrix submatrix(int rowToRemove, int columnToRemove) {
        if (rowToRemove < 0 || rowToRemove >= this.rows || columnToRemove < 0 || columnToRemove >= this.columns)
            throw new IndexOutOfBoundsException();
        Matrix sub = new Matrix(this.rows - 1, this.columns - 1);
        for (int m = 0; m < this.rows; m++) {
            // have we already skipped the row / column we are supposed to remove?
            // then the index will change
            if (m == rowToRemove)
                continue;
            int row = (m >= rowToRemove) ? m - 1 : m;
            if (row > sub.getRows())
                break;

            for (int n = 0; n < this.columns; n++) {
                if (n == columnToRemove)
                    continue;
                int column = (n >= columnToRemove) ? n - 1 : n;
                if (column > sub.getColumns())
                    break;
                sub.set(row, column, get(m, n));
            }
        }
        return sub;
    }

    // https://en.wikipedia.org/wiki/Minor_(linear_algebra)
    public double minor(int rowToRemove, int columnToRemove) {
        return submatrix(rowToRemove, columnToRemove).determinant();
    }
    public double cofactor(int rowToRemove, int columnToRemove) {
        return Math.pow(-1, rowToRemove + 1 + columnToRemove + 1) * minor(rowToRemove, columnToRemove);
    }
    // https://en.wikipedia.org/wiki/Minor_(linear_algebra)#Inverse_of_a_matrix
    public Matrix cofactorMatrix() {
        Matrix comatrix = new Matrix(this.rows, this.columns);
        for (int m = 0; m < this.rows; m++)
            for (int n = 0; n < this.columns; n++)
                comatrix.set(m, n, cofactor(m, n));
        return comatrix;
    }

    // https://en.wikipedia.org/wiki/Minor_(linear_algebra)#Inverse_of_a_matrix
    // inverse = 1/det(A)*C^T
    public Matrix inverse() {
        return cofactorMatrix().transpose().multiply(1 / determinant());
    }

    // https://en.wikipedia.org/wiki/Determinant
    public double determinant() {
        if (this.rows != this.columns)
            throw new IllegalArgumentException("matrix must be square");
        // ad - bc
        // cols must equal 2 if rows = 2
        if (this.rows == 2)
            return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);

        double determinant = 0;
        for (int n = 0; n < this.columns; n++)
            determinant += get(0, n) * cofactor(0, n);
        return determinant;
    }

    public void log() {
        for (int row = 0; row < this.rows; row++)
            System.out.println(Arrays.toString(row(row).getM()));
    }

    // make sure m.length is a perfect square!
    public static Matrix square(double... m) {
            int size = (int) Math.sqrt(m.length);
        return new Matrix(m, size, size);
    }
    public static Matrix row(double... m) {
        return new Matrix(m, 1, m.length);
    }
    public static Matrix column(double... m) {
        return new Matrix(m, m.length, 1);
    }
    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        for (int i = 0; i < size; i++)
            matrix.set(i, i, 1);
        return matrix;
    }
}
