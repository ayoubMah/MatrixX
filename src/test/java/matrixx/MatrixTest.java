package matrixx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    @Test
    public void testMatrixCreationAndGet() {
        double[][] data = { { 1, 2 }, { 3, 4 } };
        Matrix m = new Matrix(data);
        assertEquals(2, m.getRows());
        assertEquals(2, m.getCols());
        assertEquals(4.0, m.get(1, 1));
    }

    @Test
    public void testAddition() {
        Matrix m1 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix m2 = new Matrix(new double[][] { { 5, 6 }, { 7, 8 } });
        Matrix sum = m1.add(m2);

        Matrix expected = new Matrix(new double[][] { { 6, 8 }, { 10, 12 } });
        assertTrue(expected.isEquals(sum));
    }

    @Test
    public void testMultiplication() {
        Matrix m1 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix m2 = new Matrix(new double[][] { { 2, 0 }, { 1, 2 } });
        // (1*2 + 2*1)=4, (1*0 + 2*2)=4
        // (3*2 + 4*1)=10, (3*0 + 4*2)=8
        Matrix mult = m1.multiply(m2);

        Matrix expected = new Matrix(new double[][] { { 4, 4 }, { 10, 8 } });
        assertTrue(expected.isEquals(mult));
    }

    @Test
    public void testIdentityAndPower() {
        Matrix m = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix identity = Matrix.identity(2);

        // A * I = A
        assertTrue(m.isEquals(m.multiply(identity)));

        // A^0 = I
        assertTrue(identity.isEquals(m.power(0)));

        // A^2
        Matrix expectedA2 = new Matrix(new double[][] { { 7, 10 }, { 15, 22 } });
        assertTrue(expectedA2.isEquals(m.power(2)));
    }

    @Test
    public void testTranspose() {
        Matrix m = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
        Matrix transposed = m.transpose();

        Matrix expected = new Matrix(new double[][] { { 1, 4 }, { 2, 5 }, { 3, 6 } });
        assertTrue(expected.isEquals(transposed));
    }

    @Test
    public void testInvalidMultiplication() {
        Matrix m1 = new Matrix(2, 3);
        Matrix m2 = new Matrix(2, 3);
        assertThrows(IllegalArgumentException.class, () -> {
            m1.multiply(m2);
        });
    }
}
