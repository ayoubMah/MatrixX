public class Main {
    public static void main(String[] args) {

        System.out.println("================================================");

        MatrixUtils matrixUtils = new MatrixUtils(4 , 5);
        int[][] randomeMatrix = matrixUtils.rendomMatrix();

        for (int i = 0; i < matrixUtils.getRows(); i++) {
            for (int j = 0; j < matrixUtils.getCols(); j++) {
                System.out.print(randomeMatrix[i][j] + "   ");
            }
            System.out.println();
        }


    }
}