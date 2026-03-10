package matrixx;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a mathematical matrix and provides standard matrix operations.
 */
public class Matrix {
    private final double[][] data;
    private final int rows;
    private final int cols;

    /**
     * Constructs a zero matrix of the specified dimensions.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @throws IllegalArgumentException if rows or cols are less than or equal to 0
     */
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be strictly positive.");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    /**
     * Constructs a matrix from a 2D array.
     * The array is deep-copied to ensure immutability of the matrix structure.
     *
     * @param data a 2D array of doubles
     * @throws IllegalArgumentException if data is null, empty, or has rugged rows
     */
    public Matrix(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Matrix data cannot be null or empty.");
        }
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            if (data[i] == null) {
                throw new IllegalArgumentException("Matrix row cannot be null.");
            }
            if (data[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length (rectangular matrix).");
            }
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int row, int col) {
        checkBounds(row, col);
        return data[row][col];
    }

    public void set(int row, int col, double val) {
        checkBounds(row, col);
        data[row][col] = val;
    }

    private void checkBounds(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException(
                String.format("Index out of bounds: row=%d, col=%d. Matrix size: %dx%d.", row, col, rows, cols)
            );
        }
    }

    public Matrix add(Matrix matrix) {
        Objects.requireNonNull(matrix, "Matrix to add cannot be null.");
        if (this.rows != matrix.rows || this.cols != matrix.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions to be added.");
        }
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data[i][j] = this.data[i][j] + matrix.data[i][j];
            }
        }
        return result;
    }

    public Matrix subtract(Matrix matrix) {
        Objects.requireNonNull(matrix, "Matrix to subtract cannot be null.");
        if (this.rows != matrix.rows || this.cols != matrix.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions to be subtracted.");
        }
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data[i][j] = this.data[i][j] - matrix.data[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(Matrix matrix) {
        Objects.requireNonNull(matrix, "Matrix to multiply cannot be null.");
        if (this.cols != matrix.rows) {
            throw new IllegalArgumentException("Columns of the first matrix must equal rows of the second matrix.");
        }
        Matrix result = new Matrix(this.rows, matrix.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                double sum = 0;
                for (int k = 0; k < this.cols; k++) {
                    sum += this.data[i][k] * matrix.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public Matrix multiply(double scalar) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data[i][j] = this.data[i][j] * scalar;
            }
        }
        return result;
    }

    public static Matrix identity(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Identity matrix size must be strictly positive.");
        }
        Matrix identity = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            identity.data[i][i] = 1.0;
        }
        return identity;
    }

    public static Matrix zero(int rows, int cols) {
        return new Matrix(rows, cols);
    }

    public boolean isSquare() {
        return this.rows == this.cols;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    public Matrix power(int n) {
        if (!isSquare()) {
            throw new IllegalArgumentException("Only square matrices can be raised to a power.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Negative powers (inverses) are not yet supported in this method.");
        }
        if (n == 0) {
            return Matrix.identity(this.rows);
        }
        if (n == 1) {
            return this.multiply(1.0); // return a copy
        }
        Matrix res = Matrix.identity(this.rows);
        Matrix base = this;
        for (int i = 0; i < n; i++) {
            res = res.multiply(base);
        }
        return res;
    }
    
    /**
     * Creates a sub-matrix by removing the specified row and column.
     * Useful for computing minors, cofactors, and determinants.
     *
     * @param excludeRow the row to remove (0-based)
     * @param excludeCol the column to remove (0-based)
     * @return a new Matrix of size (n-1)x(m-1)
     */
    public Matrix getSubMatrix(int excludeRow, int excludeCol) {
        checkBounds(excludeRow, excludeCol);
        if (this.rows <= 1 || this.cols <= 1) {
            throw new IllegalStateException("Cannot create submatrix of a 1x1 matrix.");
        }
        
        Matrix sub = new Matrix(this.rows - 1, this.cols - 1);
        int subRow = 0;
        for (int i = 0; i < this.rows; i++) {
            if (i == excludeRow) continue;
            int subCol = 0;
            for (int j = 0; j < this.cols; j++) {
                if (j == excludeCol) continue;
                sub.data[subRow][subCol] = this.data[i][j];
                subCol++;
            }
            subRow++;
        }
        return sub;
    }

    /**
     * Computes the determinant of the matrix.
     *
     * @return the determinant value
     * @throws IllegalStateException if the matrix is not square
     */
    public double determinant() {
        if (!isSquare()) {
            throw new IllegalStateException("Determinant can only be calculated for square matrices.");
        }
        
        // Base cases
        int n = this.rows;
        if (n == 1) {
            return this.data[0][0];
        }
        if (n == 2) {
            return (this.data[0][0] * this.data[1][1]) - (this.data[0][1] * this.data[1][0]);
        }
        
        // Recursive step using Laplace expansion on the first row
        double det = 0;
        for (int j = 0; j < n; j++) {
            double sign = (j % 2 == 0) ? 1.0 : -1.0;
            Matrix minor = getSubMatrix(0, j);
            det += sign * this.data[0][j] * minor.determinant();
        }
        return det;
    }

    /**
     * Calculates the Cofactor Matrix.
     */
    public Matrix getCofactorMatrix() {
        if (!isSquare()) {
            throw new IllegalStateException("Cofactor matrix can only be calculated for square matrices.");
        }
        
        int n = this.rows;
        Matrix cofactor = new Matrix(n, n);
        
        if (n == 1) {
            cofactor.data[0][0] = 1; // Conventional fallback
            return cofactor;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Matrix minor = getSubMatrix(i, j);
                double sign = ((i + j) % 2 == 0) ? 1.0 : -1.0;
                cofactor.data[i][j] = sign * minor.determinant();
            }
        }
        return cofactor;
    }

    /**
     * Calculates the Adjugate (Adjoint) Matrix, which is the transpose of the Cofactor Matrix.
     */
    public Matrix adjugate() {
        return getCofactorMatrix().transpose();
    }

    /**
     * Calculates the Inverse of the matrix.
     *
     * @return a new Matrix representing the inverse
     * @throws IllegalStateException if the matrix is not square or is singular (determinant is 0)
     */
    public Matrix inverse() {
        if (!isSquare()) {
            throw new IllegalStateException("Inverse can only be calculated for square matrices.");
        }
        
        double det = determinant();
        if (Math.abs(det) < 1e-9) {
            throw new IllegalStateException("Matrix is singular (determinant is 0) and cannot be inverted.");
        }
        
        if (this.rows == 1) {
            Matrix inv = new Matrix(1, 1);
            inv.data[0][0] = 1.0 / this.data[0][0];
            return inv;
        }
        
        Matrix adj = adjugate();
        return adj.multiply(1.0 / det);
    }

    public boolean isEquals(Matrix matrix) {
        if (matrix == null) return false;
        return this.equals(matrix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        
        if (rows != matrix.rows || cols != matrix.cols) return false;
        
        double epsilon = 1e-9;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.abs(data[i][j] - matrix.data[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols);
        for (int i = 0; i < rows; i++) {
            result = 31 * result + Arrays.hashCode(data[i]);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%8.2f", data[i][j]));
                if (j < cols - 1) sb.append(", ");
            }
            sb.append("]");
            if (i < rows - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }
}
