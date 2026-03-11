package matrixx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    @Test
    public void testMatrixCreationAndGet() {
        double[][] data = { {1, 2}, {3, 4} };
        Matrix m = new Matrix(data);
        assertEquals(2, m.getRows());
        assertEquals(2, m.getCols());
        assertEquals(4.0, m.get(1, 1));
    }
    
    @Test
    public void testMatrixCreationValidations() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(null));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[0][0]));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[][]{ {1, 2}, {3} })); // ragged array
    }

    @Test
    public void testBoundsChecking() {
        Matrix m = new Matrix(2, 2);
        assertThrows(IndexOutOfBoundsException.class, () -> m.get(2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> m.set(-1, 0, 5.0));
    }

    @Test
    public void testAddition() {
        Matrix m1 = new Matrix(new double[][]{ {1, 2}, {3, 4} });
        Matrix m2 = new Matrix(new double[][]{ {5, 6}, {7, 8} });
        Matrix sum = m1.add(m2);
        
        Matrix expected = new Matrix(new double[][]{ {6, 8}, {10, 12} });
        assertEquals(expected, sum); // uses overridden equals()
    }

    @Test
    public void testMultiplication() {
        Matrix m1 = new Matrix(new double[][]{ {1, 2}, {3, 4} });
        Matrix m2 = new Matrix(new double[][]{ {2, 0}, {1, 2} });
        Matrix mult = m1.multiply(m2);
        
        Matrix expected = new Matrix(new double[][]{ {4, 4}, {10, 8} });
        assertEquals(expected, mult);
    }

    @Test
    public void testDeterminant2x2() {
        Matrix m = new Matrix(new double[][]{ {4, 6}, {3, 8} });
        // det = (4*8) - (6*3) = 32 - 18 = 14
        assertEquals(14.0, m.determinant(), 1e-9);
    }

    @Test
    public void testDeterminant3x3() {
        Matrix m = new Matrix(new double[][]{ 
            {6, 1, 1}, 
            {4, -2, 5}, 
            {2, 8, 7} 
        });
        // det = 6(-14 - 40) - 1(28 - 10) + 1(32 - (-4))
        // = 6(-54) - 1(18) + 1(36) = -324 - 18 + 36 = -306
        assertEquals(-306.0, m.determinant(), 1e-9);
    }

    @Test
    public void testDeterminantSingular() {
        Matrix m = new Matrix(new double[][]{ 
            {1, 2, 3}, 
            {4, 5, 6}, 
            {7, 8, 9} 
        }); // rows are linearly dependent
        assertEquals(0.0, m.determinant(), 1e-9);
    }

    @Test
    public void testSubMatrix() {
        Matrix m = new Matrix(new double[][]{ 
            {1, 2, 3}, 
            {4, 5, 6}, 
            {7, 8, 9} 
        });
        Matrix sub = m.getSubMatrix(1, 1); // remove row 1, col 1
        Matrix expected = new Matrix(new double[][]{ {1, 3}, {7, 9} });
        assertEquals(expected, sub);
    }

    @Test
    public void testInverse2x2() {
        Matrix m = new Matrix(new double[][]{ {4, 7}, {2, 6} });
        Matrix inv = m.inverse();
        
        Matrix expected = new Matrix(new double[][]{ {0.6, -0.7}, {-0.2, 0.4} });
        assertEquals(expected, inv);
        
        // A * A^-1 should equal Identity matrix
        assertEquals(Matrix.identity(2), m.multiply(inv));
    }

    @Test
    public void testInverse3x3() {
        Matrix m = new Matrix(new double[][]{ 
            {3, 0, 2}, 
            {2, 0, -2}, 
            {0, 1, 1} 
        });
        Matrix inv = m.inverse();
        
        // Let's just verify A * A^-1 = Identity
        assertEquals(Matrix.identity(3), m.multiply(inv));
    }

    @Test
    public void testInverseSingularException() {
        Matrix m = new Matrix(new double[][]{ 
            {1, 2, 3}, 
            {4, 5, 6}, 
            {7, 8, 9} 
        });
        assertThrows(IllegalStateException.class, () -> m.inverse());
    }

    @Test
    public void testNonSquareDeterminantException() {
        Matrix m = new Matrix(2, 3);
        assertThrows(IllegalStateException.class, () -> m.determinant());
    }

    @Test
    public void testLUDecomposition() {
        Matrix m = new Matrix(new double[][]{
            {2, -1, -2},
            {-4, 6, 3},
            {-4, -2, 8}
        });
        Matrix.LUPair lu = m.lu();
        
        Matrix expectedL = new Matrix(new double[][]{
            {1, 0, 0},
            {-2, 1, 0},
            {-2, -1, 1}
        });
        Matrix expectedU = new Matrix(new double[][]{
            {2, -1, -2},
            {0, 4, -1},
            {0, 0, 3}
        });
        
        assertEquals(expectedL, lu.L(), "L matrix is incorrect");
        assertEquals(expectedU, lu.U(), "U matrix is incorrect");
        
        // Final sanity check: L * U = A
        assertEquals(m, lu.L().multiply(lu.U()), "L * U does not equal A");
    }

    @Test
    public void testLUDecompositionZeroPivot() {
        Matrix m = new Matrix(new double[][]{
            {0, 1},
            {1, 0}
        });
        assertThrows(ArithmeticException.class, () -> m.lu());
    }

    @Test
    public void testRREF() {
        Matrix m = new Matrix(new double[][]{
            {1, 2, -1, -4},
            {2, 3, -1, -11},
            {-2, 0, -3, 22}
        });
        Matrix rref = m.rref();
        
        Matrix expected = new Matrix(new double[][]{
            {1, 0, 0, -8},
            {0, 1, 0, 1},
            {0, 0, 1, -2}
        });
        
        assertEquals(expected, rref);
    }

    @Test
    public void testIsDiagonal() {
        Matrix m = new Matrix(new double[][]{ {2, 0}, {0, 3} });
        assertTrue(m.isDiagonal());
        
        Matrix m2 = new Matrix(new double[][]{ {2, 1}, {0, 3} });
        assertFalse(m2.isDiagonal());
    }

    @Test
    public void testIsSymmetric() {
        Matrix m = new Matrix(new double[][]{
            {1, 7, 3},
            {7, 4, -5},
            {3, -5, 6}
        });
        assertTrue(m.isSymmetric());
        
        Matrix m2 = new Matrix(new double[][]{ {1, 2}, {3, 4} });
        assertFalse(m2.isSymmetric());
    }

    @Test
    public void testIsIdentity() {
        Matrix m = Matrix.identity(3);
        assertTrue(m.isIdentity());
        
        Matrix m2 = new Matrix(new double[][]{ {1, 0}, {0, 2} });
        assertFalse(m2.isIdentity());
    }

    @Test
    public void testIsOrthogonal() {
        // A simple Orthogonal Matrix (rotation by 90 degrees)
        Matrix m = new Matrix(new double[][]{ {0, -1}, {1, 0} });
        assertTrue(m.isOrthogonal());
        
        // A 3x3 Permutation Matrix is Orthogonal
        Matrix m2 = new Matrix(new double[][]{
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        });
        assertTrue(m2.isOrthogonal());
        
        Matrix m3 = new Matrix(new double[][]{ {1, 2}, {3, 4} });
        assertFalse(m3.isOrthogonal());
    }

    @Test
    public void testSolve() {
        Matrix A = new Matrix(new double[][]{
            {1, 2},
            {3, 4}
        });
        Matrix B = new Matrix(new double[][]{
            {5},
            {11}
        });
        
        // solve A * X = B
        Matrix X = A.solve(B);
        
        Matrix expectedX = new Matrix(new double[][]{
            {1},
            {2}
        });
        assertEquals(expectedX, X);
        
        // Verify A * X = B
        assertEquals(B, A.multiply(X));
    }

    @Test
    public void testSolveExceptions() {
        Matrix A = new Matrix(new double[][]{ {1, 2, 3}, {4, 5, 6} }); // not square
        Matrix B = Matrix.identity(2);
        assertThrows(IllegalStateException.class, () -> A.solve(B));

        Matrix C = Matrix.identity(3);
        assertThrows(IllegalArgumentException.class, () -> C.solve(B)); // B rows (2) != C rows (3)
    }

    @Test
    public void testStaticBuilders() {
        Matrix ones = Matrix.ones(2, 3);
        assertEquals(new Matrix(new double[][]{{1, 1, 1}, {1, 1, 1}}), ones);
        
        Matrix constant = Matrix.constant(2, 2, 5.5);
        assertEquals(new Matrix(new double[][]{{5.5, 5.5}, {5.5, 5.5}}), constant);
        
        Matrix rand = Matrix.random(4, 4);
        assertEquals(4, rand.getRows());
        assertEquals(4, rand.getCols());
        assertTrue(rand.get(0, 0) >= 0.0 && rand.get(0, 0) <= 1.0);
    }

    @Test
    public void testFromMatlab() {
        Matrix m1 = Matrix.fromMatlab("[1 2 3; 4 5 6]");
        assertEquals(new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}}), m1);

        Matrix m2 = Matrix.fromMatlab("1.5, 2.5; 3.5, 4.5");
        assertEquals(new Matrix(new double[][]{{1.5, 2.5}, {3.5, 4.5}}), m2);
        
        assertThrows(IllegalArgumentException.class, () -> Matrix.fromMatlab(""));
        assertThrows(IllegalArgumentException.class, () -> Matrix.fromMatlab("[1 2; 3 4 5]")); // Unbalanced
        assertThrows(IllegalArgumentException.class, () -> Matrix.fromMatlab("[1 x; 3 4]")); // Bad parsing
    }

    @Test
    public void testCholesky() {
        Matrix m = new Matrix(new double[][]{
            {4, 12, -16},
            {12, 37, -43},
            {-16, -43, 98}
        });
        Matrix L = m.cholesky();
        Matrix expectedL = new Matrix(new double[][]{
            {2, 0, 0},
            {6, 1, 0},
            {-8, 5, 3}
        });
        assertEquals(expectedL, L);
        assertEquals(m, L.multiply(L.transpose()));
    }

    @Test
    public void testCholeskyExceptions() {
        Matrix notSymmetric = new Matrix(new double[][]{{1, 2}, {3, 4}});
        assertThrows(IllegalStateException.class, () -> notSymmetric.cholesky());

        Matrix notDefinite = new Matrix(new double[][]{{1, 2}, {2, 1}}); // det = 1 - 4 = -3
        assertThrows(IllegalStateException.class, () -> notDefinite.cholesky());
    }

    @Test
    public void testQR() {
        Matrix A = new Matrix(new double[][]{
            {12, -51, 4},
            {6, 167, -68},
            {-4, 24, -41}
        });
        Matrix.QRPair qr = A.qr();
        assertTrue(qr.Q().isOrthogonal());
        
        Matrix QR = qr.Q().multiply(qr.R());
        for (int i=0; i<A.getRows(); i++) {
            for (int j=0; j<A.getCols(); j++) {
                assertEquals(A.get(i, j), QR.get(i, j), 1e-5);
            }
        }
    }

    @Test
    public void testEigen2x2() {
        Matrix A = new Matrix(new double[][]{{1, 2}, {2, 1}});
        Matrix.EigenResult res = A.eigen();
        assertEquals(3.0, Math.max(res.eigenvalues()[0], res.eigenvalues()[1]), 1e-5);
        assertEquals(-1.0, Math.min(res.eigenvalues()[0], res.eigenvalues()[1]), 1e-5);
        
        Matrix v0 = res.eigenvectors()[0];
        Matrix lambdaV0 = v0.multiply(res.eigenvalues()[0]);
        Matrix Av0 = A.multiply(v0);
        for (int i=0; i<v0.getRows(); i++) assertEquals(lambdaV0.get(i, 0), Av0.get(i, 0), 1e-5);
    }

    @Test
    public void testEigen3x3() {
        Matrix S = new Matrix(new double[][]{
            {2, -1, 0},
            {-1, 2, -1},
            {0, -1, 2}
        });
        Matrix.EigenResult res = S.eigen();
        Matrix v0 = res.eigenvectors()[0];
        Matrix lambdaV0 = v0.multiply(res.eigenvalues()[0]);
        Matrix Av0 = S.multiply(v0);
        for (int i=0; i<v0.getRows(); i++) assertEquals(lambdaV0.get(i, 0), Av0.get(i, 0), 1e-5);
    }

    @Test
    public void testMap() {
        Matrix A = new Matrix(new double[][]{{0, Math.PI / 2}});
        Matrix B = A.map(Math::sin);
        assertEquals(new Matrix(new double[][]{{0, 1}}), B);
    }

    @Test
    public void testHadamard() {
        Matrix A = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix B = new Matrix(new double[][]{{2, 3}, {4, 5}});
        assertEquals(new Matrix(new double[][]{{2, 6}, {12, 20}}), A.hadamardMultiply(B));
    }

    @Test
    public void testDotProduct() {
        Matrix v1 = new Matrix(new double[][]{{1}, {2}, {3}});
        Matrix v2 = new Matrix(new double[][]{{4}, {5}, {6}});
        assertEquals(32.0, v1.dotProduct(v2), 1e-9);
        
        Matrix v3 = new Matrix(new double[][]{{1, 2, 3}});
        assertEquals(32.0, v3.dotProduct(v2), 1e-9);
    }
}
