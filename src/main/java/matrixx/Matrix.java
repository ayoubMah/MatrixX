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

    /**
     * Creates a matrix of the specified size, filled with the given constant value.
     */
    public static Matrix constant(int rows, int cols, double value) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.data[i][j] = value;
            }
        }
        return m;
    }

    /**
     * Creates a matrix of the specified size, filled with ones.
     */
    public static Matrix ones(int rows, int cols) {
        return constant(rows, cols, 1.0);
    }

    /**
     * Creates a matrix of the specified size, filled with random values between 0.0 and 1.0.
     */
    public static Matrix random(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.data[i][j] = Math.random();
            }
        }
        return m;
    }

    /**
     * Parses a MATLAB-style matrix string, e.g., "[1 2.5; 3 4]".
     * Rows are separated by ';' and elements are separated by spaces or commas.
     *
     * @param matlabStr the string representing the matrix
     * @return a new Matrix object corresponding to the string
     * @throws IllegalArgumentException if the format is invalid or rows are of uneven lengths
     */
    public static Matrix fromMatlab(String matlabStr) {
        if (matlabStr == null || matlabStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be empty.");
        }
        
        String cleanStr = matlabStr.trim();
        if (cleanStr.startsWith("[")) cleanStr = cleanStr.substring(1);
        if (cleanStr.endsWith("]")) cleanStr = cleanStr.substring(0, cleanStr.length() - 1);
        
        String[] rowStrs = cleanStr.split(";");
        if (rowStrs.length == 0) {
            throw new IllegalArgumentException("No rows found in matrix string.");
        }

        double[][] parsedData = new double[rowStrs.length][];
        
        for (int i = 0; i < rowStrs.length; i++) {
            String[] elements = rowStrs[i].trim().split("[,\\s]+");
            parsedData[i] = new double[elements.length];
            for (int j = 0; j < elements.length; j++) {
                try {
                    parsedData[i][j] = Double.parseDouble(elements[j]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format at row " + i + ": " + elements[j], e);
                }
            }
        }
        
        return new Matrix(parsedData); // Re-uses constructor validation for rectangularity
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
     * Computes the LU decomposition of this matrix.
     * The matrix must be square. This uses Doolittle's algorithm (L has 1s on the diagonal).
     * Note: This does not implement partial pivoting, so it may fail with ArithmeticException if a zero pivot is encountered.
     *
     * @return an LUPair containing the Lower (L) and Upper (U) matrices
     */
    public LUPair lu() {
        if (!isSquare()) {
            throw new IllegalStateException("LU decomposition requires a square matrix.");
        }
        int n = rows;
        Matrix L = Matrix.identity(n);
        Matrix U = Matrix.zero(n, n);
        
        for (int i = 0; i < n; i++) {
            // Upper Triangular
            for (int k = i; k < n; k++) {
                double sum = 0;
                for (int j = 0; j < i; j++) {
                    sum += L.get(i, j) * U.get(j, k);
                }
                U.set(i, k, this.data[i][k] - sum);
            }
            // Lower Triangular
            for (int k = i + 1; k < n; k++) {
                double sum = 0;
                for (int j = 0; j < i; j++) {
                    sum += L.get(k, j) * U.get(j, i);
                }
                if (Math.abs(U.get(i, i)) < 1e-9) {
                    throw new ArithmeticException("LU Decomposition failed: Zero pivot encountered.");
                }
                L.set(k, i, (this.data[k][i] - sum) / U.get(i, i));
            }
        }
        return new LUPair(L, U);
    }

    /**
     * Computes the Reduced Row Echelon Form (RREF) using Gauss-Jordan elimination.
     *
     * @return a new Matrix representing the RREF of this matrix
     */
    public Matrix rref() {
        Matrix result = new Matrix(this.data); // Copy to mutate
        int lead = 0;
        int rowCount = result.rows;
        int colCount = result.cols;
        
        for (int r = 0; r < rowCount; r++) {
            if (colCount <= lead) {
                return result;
            }
            int i = r;
            while (Math.abs(result.data[i][lead]) < 1e-9) {
                i++;
                if (rowCount == i) {
                    i = r;
                    lead++;
                    if (colCount == lead) {
                        return result;
                    }
                }
            }
            
            // Swap rows i and r
            double[] temp = result.data[i];
            result.data[i] = result.data[r];
            result.data[r] = temp;
            
            // Divide row r by matrix[r][lead]
            double lv = result.data[r][lead];
            for (int j = 0; j < colCount; j++) {
                result.data[r][j] /= lv;
            }
            
            // Subtract row r from all other rows
            for (int j = 0; j < rowCount; j++) {
                if (j != r) {
                    double lv2 = result.data[j][lead];
                    for (int k = 0; k < colCount; k++) {
                        result.data[j][k] -= lv2 * result.data[r][k];
                    }
                }
            }
            lead++;
        }
        
        // Clean up -0.0
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (Math.abs(result.data[i][j]) < 1e-9) {
                    result.data[i][j] = 0.0;
                }
            }
        }
        return result;
    }

    /**
     * Computes the Cholesky Decomposition of a symmetric, positive-definite matrix.
     * Returns a lower triangular matrix L such that A = L * L^T.
     *
     * @return the lower triangular matrix L
     * @throws IllegalStateException    if the matrix is not square, not symmetric, or not positive definite
     */
    public Matrix cholesky() {
        if (!isSquare() || !isSymmetric()) {
            throw new IllegalStateException("Cholesky decomposition requires a square, symmetric matrix.");
        }
        int n = this.rows;
        Matrix L = Matrix.zero(n, n);
        for (int j = 0; j < n; j++) {
            double sum = 0;
            for (int k = 0; k < j; k++) {
                sum += Math.pow(L.get(j, k), 2);
            }
            double diag = this.data[j][j] - sum;
            if (diag <= 0) {
                throw new IllegalStateException("Matrix is not positive definite.");
            }
            L.set(j, j, Math.sqrt(diag));

            for (int i = j + 1; i < n; i++) {
                double sum2 = 0;
                for (int k = 0; k < j; k++) {
                    sum2 += L.get(i, k) * L.get(j, k);
                }
                L.set(i, j, (this.data[i][j] - sum2) / L.get(j, j));
            }
        }
        return L;
    }

    /**
     * Computes the QR decomposition of a matrix.
     * Returns orthogonal matrix Q and upper triangular matrix R such that A = Q * R.
     * Uses Modified Gram-Schmidt process.
     *
     * @return a QRPair containing Q and R matrices
     */
    public QRPair qr() {
        Matrix Q = Matrix.zero(rows, cols);
        Matrix R = Matrix.zero(cols, cols);
        Matrix V = new Matrix(this.data);
        
        for (int k = 0; k < cols; k++) {
            double norm = 0;
            for (int i = 0; i < rows; i++) {
                norm += Math.pow(V.get(i, k), 2);
            }
            norm = Math.sqrt(norm);
            
            if (Math.abs(norm) < 1e-9) {
                throw new ArithmeticException("Zero norm vector encountered in QR decomposition (matrix might be rank-deficient).");
            }
            R.set(k, k, norm);
            
            for (int i = 0; i < rows; i++) {
                Q.set(i, k, V.get(i, k) / norm);
            }
            
            for (int j = k + 1; j < cols; j++) {
                double dot = 0;
                for (int i = 0; i < rows; i++) {
                    dot += Q.get(i, k) * V.get(i, j);
                }
                R.set(k, j, dot);
                
                for (int i = 0; i < rows; i++) {
                    V.set(i, j, V.get(i, j) - dot * Q.get(i, k));
                }
            }
        }
        return new QRPair(Q, R);
    }

    /**
     * Analytically solves for eigenvalues and eigenvectors.
     * Only supports 2x2 and 3x3 square matrices.
     *
     * @return an EigenResult containing the eigenvalues and corresponding eigenvectors
     * @throws UnsupportedOperationException if matrix is not 2x2 or 3x3
     * @throws IllegalStateException if eigenvalues are complex (this basic solver only handles real eigenvalues)
     */
    public EigenResult eigen() {
        if (!isSquare() || (rows != 2 && rows != 3)) {
            throw new UnsupportedOperationException("Analytical eigen solver only supports 2x2 and 3x3 matrices.");
        }
        
        if (rows == 2) {
            double a = data[0][0], b = data[0][1];
            double c = data[1][0], d = data[1][1];
            double trace = a + d;
            double det = a * d - b * c;
            double discriminant = trace * trace - 4 * det;
            if (discriminant < 0) {
                throw new IllegalStateException("Matrix has complex eigenvalues.");
            }
            
            double lambda1 = (trace + Math.sqrt(discriminant)) / 2.0;
            double lambda2 = (trace - Math.sqrt(discriminant)) / 2.0;
            
            Matrix vec1 = findEigenvector2x2(lambda1, a, b, c, d);
            Matrix vec2 = findEigenvector2x2(lambda2, a, b, c, d);
            
            return new EigenResult(new double[]{lambda1, lambda2}, new Matrix[]{vec1, vec2});
        } else {
            // 3x3 Analytical using characteristic equation: x^3 - c2*x^2 - c1*x - c0 = 0
            double a11 = data[0][0], a12 = data[0][1], a13 = data[0][2];
            double a21 = data[1][0], a22 = data[1][1], a23 = data[1][2];
            double a31 = data[2][0], a32 = data[2][1], a33 = data[2][2];
            
            double c2 = a11 + a22 + a33; // Trace
            double c1 = -((a11*a22 - a12*a21) + (a11*a33 - a13*a31) + (a22*a33 - a23*a32));
            double c0 = determinant(); // Det
            
            // Substitute x = y + c2/3 to get y^3 + p*y + q = 0
            double p = -c2*c2/3.0 - c1;
            double q = -2.0*c2*c2*c2/27.0 - c1*c2/3.0 - c0;
            
            double delta = (q*q/4.0) + (p*p*p/27.0);
            if (delta > 0) {
                 throw new IllegalStateException("Matrix has complex eigenvalues.");
            }
            
            // Trigonometric solution for 3 given real roots
            double r = Math.sqrt(Math.pow(-p/3.0, 3));
            double phi = Math.acos(-q / (2.0 * r));
            
            double y1 = 2.0 * Math.pow(r, 1.0/3.0) * Math.cos(phi / 3.0);
            double y2 = 2.0 * Math.pow(r, 1.0/3.0) * Math.cos((phi + 2.0 * Math.PI) / 3.0);
            double y3 = 2.0 * Math.pow(r, 1.0/3.0) * Math.cos((phi + 4.0 * Math.PI) / 3.0);
            
            double lambda1 = y1 + c2/3.0;
            double lambda2 = y2 + c2/3.0;
            double lambda3 = y3 + c2/3.0;
            
            Matrix vec1 = findEigenvector3x3(lambda1);
            Matrix vec2 = findEigenvector3x3(lambda2);
            Matrix vec3 = findEigenvector3x3(lambda3);
            
            return new EigenResult(new double[]{lambda1, lambda2, lambda3}, new Matrix[]{vec1, vec2, vec3});
        }
    }

    private Matrix findEigenvector2x2(double lambda, double a, double b, double c, double d) {
        if (Math.abs(b) > 1e-9) return new Matrix(new double[][]{ {lambda - d}, {c} }).normalize();
        if (Math.abs(c) > 1e-9) return new Matrix(new double[][]{ {b}, {lambda - a} }).normalize();
        return new Matrix(new double[][]{ {1}, {0} }); // Default fallback for diagonal matrices
    }

    private Matrix findEigenvector3x3(double lambda) {
        Matrix M = this.subtract(Matrix.identity(3).multiply(lambda));
        
        double[] cross01 = crossProduct(M.data[0], M.data[1]);
        if (vectorNorm(cross01) > 1e-6) {
            return new Matrix(new double[][]{{cross01[0]}, {cross01[1]}, {cross01[2]}}).normalize();
        }
        
        double[] cross12 = crossProduct(M.data[1], M.data[2]);
        if (vectorNorm(cross12) > 1e-6) {
            return new Matrix(new double[][]{{cross12[0]}, {cross12[1]}, {cross12[2]}}).normalize();
        }
        
        double[] cross02 = crossProduct(M.data[0], M.data[2]);
        if (vectorNorm(cross02) > 1e-6) {
            return new Matrix(new double[][]{{cross02[0]}, {cross02[1]}, {cross02[2]}}).normalize();
        }
        
        // Return standard basis if all are 0
        return new Matrix(new double[][]{{1}, {0}, {0}});
    }

    private double[] crossProduct(double[] u, double[] v) {
        return new double[]{
            u[1]*v[2] - u[2]*v[1],
            u[2]*v[0] - u[0]*v[2],
            u[0]*v[1] - u[1]*v[0]
        };
    }
    
    private double vectorNorm(double[] v) {
        return Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
    }

    /**
     * Normalizes a vector matrix (1xN or Nx1) to unit length.
     */
    public Matrix normalize() {
        if (!isVector()) {
            throw new IllegalStateException("Only vectors can be normalized.");
        }
        double mag = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mag += data[i][j] * data[i][j];
            }
        }
        if (Math.abs(mag) < 1e-9) return this;
        return this.multiply(1.0 / Math.sqrt(mag));
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

    /**
     * Checks if the matrix is a Diagonal Matrix
     * (all elements outside the main diagonal are zero).
     */
    public boolean isDiagonal() {
        if (!isSquare()) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i != j && Math.abs(data[i][j]) > 1e-9) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the matrix is symmetric (A = A^T).
     */
    public boolean isSymmetric() {
        if (!isSquare()) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < cols; j++) {
                if (Math.abs(data[i][j] - data[j][i]) > 1e-9) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the matrix is exactly the Identity Matrix.
     */
    public boolean isIdentity() {
        if (!isSquare()) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) {
                    if (Math.abs(data[i][j] - 1.0) > 1e-9) return false;
                } else {
                    if (Math.abs(data[i][j]) > 1e-9) return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the matrix is Orthogonal (A * A^T = I).
     */
    public boolean isOrthogonal() {
        if (!isSquare()) return false;
        Matrix transposed = this.transpose();
        Matrix multiplied = this.multiply(transposed);
        return multiplied.isIdentity();
    }

    /**
     * Solves the linear system A * X = B.
     * This relies upon finding the inverse of A.
     *
     * @param B the matrix or vector on the right-hand side
     * @return the solution matrix/vector X
     * @throws IllegalArgumentException if the dimensions of A and B do not align
     * @throws IllegalStateException if A is not square or is singular
     */
    public Matrix solve(Matrix B) {
        Objects.requireNonNull(B, "Right-hand side matrix (B) cannot be null.");
        if (this.rows != B.rows) {
            throw new IllegalArgumentException("Matrix dimensions do not agree for solving (A.rows must match B.rows).");
        }
        
        Matrix inverseA = this.inverse(); // Validates square matrix & singular nature intrinsically
        return inverseA.multiply(B);
    }

    /**
     * Applies a functional mapping over every element of the matrix.
     */
    public Matrix map(java.util.function.DoubleUnaryOperator function) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data[i][j] = function.applyAsDouble(this.data[i][j]);
            }
        }
        return result;
    }

    /**
     * Computes the Hadamard Product (element-wise multiplication).
     */
    public Matrix hadamardMultiply(Matrix matrix) {
        Objects.requireNonNull(matrix, "Matrix cannot be null.");
        if (this.rows != matrix.rows || this.cols != matrix.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for Hadamard product.");
        }
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data[i][j] = this.data[i][j] * matrix.data[i][j];
            }
        }
        return result;
    }

    /**
     * Computes the Dot Product of two vectors.
     */
    public double dotProduct(Matrix vector) {
        Objects.requireNonNull(vector, "Vector cannot be null.");
        if (!this.isVector() || !vector.isVector()) {
            throw new IllegalArgumentException("Dot product requires both operands to be vectors (1xN or Nx1).");
        }
        if (this.rows * this.cols != vector.rows * vector.cols) {
            throw new IllegalArgumentException("Vectors must be the same length.");
        }
        double product = 0;
        double[] v1 = this.toFlatArray();
        double[] v2 = vector.toFlatArray();
        for (int i = 0; i < v1.length; i++) {
            product += v1[i] * v2[i];
        }
        return product;
    }

    private boolean isVector() {
        return this.rows == 1 || this.cols == 1;
    }

    private double[] toFlatArray() {
        double[] arr = new double[rows * cols];
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[k++] = data[i][j];
            }
        }
        return arr;
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

    /**
     * A container for the LU Decomposition results.
     */
    public record LUPair(Matrix L, Matrix U) {}

    /**
     * A container for the QR Decomposition results.
     */
    public record QRPair(Matrix Q, Matrix R) {}

    /**
     * A container for analytical Eigen computational results.
     */
    public record EigenResult(double[] eigenvalues, Matrix[] eigenvectors) {}
}
