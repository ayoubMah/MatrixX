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
    public void testMatrixCreationValidations() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(null));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[0][0]));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[][] { { 1, 2 }, { 3 } })); // ragged
                                                                                                            // array
    }

    @Test
    public void testBoundsChecking() {
        Matrix m = new Matrix(2, 2);
        assertThrows(IndexOutOfBoundsException.class, () -> m.get(2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> m.set(-1, 0, 5.0));
    }

    @Test
    public void testAddition() {
        Matrix m1 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix m2 = new Matrix(new double[][] { { 5, 6 }, { 7, 8 } });
        Matrix sum = m1.add(m2);

        Matrix expected = new Matrix(new double[][] { { 6, 8 }, { 10, 12 } });
        assertEquals(expected, sum); // uses overridden equals()
    }

    @Test
    public void testAdditionValidations() {
        Matrix m1 = new Matrix(2, 2);
        Matrix m2 = new Matrix(3, 3);
        assertThrows(IllegalArgumentException.class, () -> m1.add(m2));
        assertThrows(NullPointerException.class, () -> m1.add(null));
    }

    @Test
    public void testMultiplication() {
        Matrix m1 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix m2 = new Matrix(new double[][] { { 2, 0 }, { 1, 2 } });
        Matrix mult = m1.multiply(m2);

        Matrix expected = new Matrix(new double[][] { { 4, 4 }, { 10, 8 } });
        assertEquals(expected, mult);
    }

    @Test
    public void testMultiplicationByScalar() {
        Matrix m1 = new Matrix(new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } });
        Matrix scaled = m1.multiply(2.5);
        Matrix expected = new Matrix(new double[][] { { 2.5, 5.0 }, { 7.5, 10.0 } });
        assertEquals(expected, scaled);
    }

    @Test
    public void testIdentityAndPower() {
        Matrix m = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix identity = Matrix.identity(2);

        assertEquals(m, m.multiply(identity));
        assertEquals(identity, m.power(0));

        Matrix expectedA2 = new Matrix(new double[][] { { 7, 10 }, { 15, 22 } });
        assertEquals(expectedA2, m.power(2));
    }

    @Test
    public void testTranspose() {
        Matrix m = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
        Matrix transposed = m.transpose();

        Matrix expected = new Matrix(new double[][] { { 1, 4 }, { 2, 5 }, { 3, 6 } });
        assertEquals(expected, transposed);
    }

    @Test
    public void testEqualsAndHashCode() {
        Matrix m1 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix m2 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
        Matrix m3 = new Matrix(new double[][] { { 1, 2 }, { 3, 5 } });

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1, m3);
        assertNotEquals(m1, null);
        assertNotEquals(m1, new Object());
    }

    @Test
    public void testToString() {
        Matrix m = new Matrix(new double[][] { { 1.1 }, { 2.2 } });
        String str = m.toString();
        assertTrue(str.contains("1.10")); // format check
        assertTrue(str.contains("2.20"));
    }
}
