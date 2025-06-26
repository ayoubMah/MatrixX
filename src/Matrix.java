import java.util.Scanner;

public class Matrix {
    private int[][] data;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols];
    }


    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }

    // create a matrix from user input
    static Scanner scanner = new Scanner(System.in);
    public Matrix createMatrixFromUserInput() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("Enter the value of [" + (i + 1) + "]"+"["+(j+1)+"] : ");
                this.data[i][j]= scanner.nextInt();
            }
        }
        return this;
    }

    // generate random matrix nxm =>  0<=n,m <=10
    public Matrix rendomMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = (int)(Math.random()*10);
            }
        }
        return this;
    }



    public void displayMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(this.data[i][j] + "   ");
            }
            System.out.println();
        }
    }

    // add two matrix
    public Matrix add(Matrix matrix) {
        if (this.rows == matrix.rows && this.cols == matrix.cols) {
            Matrix result = new Matrix(matrix.rows, matrix.cols);
            for (int i = 0; i < matrix.rows; i++) {
                for (int j = 0; j < matrix.cols; j++) {
                    result.data[i][j] = matrix.data[i][j] + this.data[i][j];
                }
            }
            return result;
        }
        throw new Error("The 2 matrix should be in the same size");

    }

    // sub two matrix
    public Matrix subtract(Matrix matrix) {
        if (this.rows == matrix.rows && this.cols == matrix.cols) {
            Matrix result = new Matrix(matrix.rows, matrix.cols);
            for (int i = 0; i < matrix.rows; i++) {
                for (int j = 0; j < matrix.cols; j++) {
                    result.data[i][j] = this.data[i][j] - matrix.data[i][j];
                }
            }
            return result;
        }
        throw new Error("The 2 matrix should be in the same size");
    }

    // multiplication
    //To perform matrix multiplication, the first matrix must have the same number of columns as the second matrix has rows
    public Matrix multiply(Matrix matrix) {
        if (this.rows == matrix.cols) {
            Matrix result = new Matrix(matrix.rows, matrix.cols);
            for (int i = 0; i < matrix.rows; i++) {
                for (int j = 0; j < matrix.cols; j++) {
                    for (int k = 0; k < matrix.rows; k++) {
                        result.data[i][j] += this.data[i][k] * matrix.data[k][j];
                    }
                }
            }
            return result;
        }
        throw new Error("You should respect the multiplication condition of 2 matrix");
    }

}
