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
}
