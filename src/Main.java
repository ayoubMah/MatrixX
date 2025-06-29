public class Main {
    public static void main(String[] args) {

        System.out.println("=== === === === === === === ===");

//        MatrixUtils matrixUtils1 = new MatrixUtils(7 , 5);
//        int[][] randomeMatrix1 = matrixUtils1.rendomMatrix();
//
//        MatrixUtils matrixUtils2 = new MatrixUtils(5 , 5);
//        int[][] randomeMatrix2 = matrixUtils2.rendomMatrix();
//
//        matrixUtils1.desplyMatrix(randomeMatrix1);
//        System.out.println("=============================");
//        matrixUtils2.desplyMatrix(randomeMatrix2);
//
//        System.out.println(randomeMatrix1.length);
        Matrix matrix2 = new Matrix(3,3);
        matrix2.rendomMatrix();
        System.out.println("first matrix is: ");
        matrix2.displayMatrix();
        Matrix matrix3 = new Matrix(2,2);
        matrix3.rendomMatrix();
        System.out.println("second matrix is: ");
        matrix3.displayMatrix();
//        System.out.println("the mult of the first and the second matrix is");
//        (matrix2.multiply(matrix3)).displayMatrix();

        Matrix col = matrix2.power(2);
        System.out.println("power of your matrix is: ");
        col.displayMatrix();


//        Matrix matrix1 = new Matrix(3,3);
//        matrix1.createMatrixFromUserInput();
//        System.out.println("second matrix is: ");
//        matrix1.displayMatrix();
//
//        System.out.println("is matrix1 equal to matrix2:  ? ");
//        System.out.println(matrix1.isEquals(matrix2));

//        Matrix matrix = new Matrix(4,4);
//        matrix.identity();
//        matrix.displayMatrix();
//        System.out.println("---------------");
//
//        matrix.scalar(5);
//        matrix.displayMatrix();
//
//        System.out.println("==");
//
//        Matrix matrixnull = new Matrix(4,4);
//        matrixnull.nullMatrix();
//        matrixnull.displayMatrix();
//
//        System.out.println("==");



        // generated matrix
//        Matrix matrix1 = new Matrix(2,2);
//        matrix1.rendomMatrix();
//        System.out.println("first matrix is: ");
//        matrix1.displayMatrix();
//
//        Matrix matrix2 = new Matrix(2,2);
//        matrix2.rendomMatrix();
//        System.out.println("second matrix is: ");
//        matrix2.displayMatrix();

//        System.out.println("=== adding 2 matrix ===");
//        Matrix adding = matrix1.add(matrix2);
//        adding.displayMatrix();
//
//        System.out.println("=== sub 2 matrix ===");
//        Matrix sub = matrix1.subtract(matrix2);
//        sub.displayMatrix();
//
//        System.out.println("=== multiplying matrix ===");
//        Matrix multiply = matrix1.multiply(matrix2);
//        multiply.displayMatrix();



//        Matrix matrix2 = new Matrix(3,3);
//        int[][] mat2 = matrix2.createMatrix();
//        System.out.println("===========Matrix Created==========");
//        matrix2.desplyMatrix(mat2);
//
//        System.out.println("========= the sum of 2 matrix");
//        Matrix sum = matrix.add(matrix2);




    }
}