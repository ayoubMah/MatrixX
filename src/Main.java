public class Main {
    public static void main(String[] args) {

        System.out.println("================================================");

        MatrixUtils matrixUtils1 = new MatrixUtils(4 , 5);
        int[][] randomeMatrix1 = matrixUtils1.rendomMatrix();

        MatrixUtils matrixUtils2 = new MatrixUtils(5 , 5);
        int[][] randomeMatrix2 = matrixUtils2.rendomMatrix();

        matrixUtils1.desplyMatrix(randomeMatrix1);
        System.out.println("=============================");
        matrixUtils2.desplyMatrix(randomeMatrix2);
    }
}