package matrixx;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== MatrixX Demo ===");

        double[][] dataA = {
            {1, 2},
            {3, 4}
        };
        Matrix A = new Matrix(dataA);
        
        System.out.println("\nMatrix A:");
        A.print();

        Matrix I = Matrix.identity(2);
        System.out.println("\nIdentity Matrix I:");
        I.print();

        System.out.println("\nA + I:");
        Matrix sum = A.add(I);
        sum.print();

        System.out.println("\nA * A (A²):");
        Matrix A2 = A.power(2);
        A2.print();
        
        System.out.println("\nTranspose of A:");
        A.transpose().print();
    }
}