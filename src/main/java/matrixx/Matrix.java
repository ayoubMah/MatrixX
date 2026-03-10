package matrixx;

public class Matrix {
    private double[][] data;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, double val) {
        data[row][col] = val;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%8.2f ", this.data[i][j]);
            }
            System.out.println();
        }
    }

    public Matrix add(Matrix matrix) {
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
        Matrix identity = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            identity.data[i][i] = 1.0;
        }
        return identity;
    }

    public static Matrix zero(int rows, int cols) {
        return new Matrix(rows, cols);
    }

    public boolean isEquals(Matrix matrix) {
        if (this.rows != matrix.rows || this.cols != matrix.cols) {
            return false;
        }
        double epsilon = 1e-9;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (Math.abs(this.data[i][j] - matrix.data[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
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

    public boolean isSquare() {
        return this.rows == this.cols;
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
}
