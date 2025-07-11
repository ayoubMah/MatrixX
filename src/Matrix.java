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
                while (true){
                    System.out.println("Enter the value of [" + (i + 1) + "]"+"["+(j+1)+"] : ");
                    if (scanner.hasNextInt()){
                        data[i][j] = scanner.nextInt();
                        break;
                    }else {
                        System.out.println("That's not a valid integer. Try again.");
                        scanner.next(); // discard invalid input
                    }
                }

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
//        if(this == null){
//            System.out.println("can not display a null matrix");
//        }
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

    // generate identity matrix
    public Matrix identity() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (i == j){
                    this.data[i][j] = 1;
                }else  {
                    this.data[i][j] = 0;
                }
            }
        }
        return this;
    }

    public Matrix nullMatrix() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = 0;
            }
        }
        return this;
    }

    public Matrix scalar(int scalar) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] *= scalar;
            }
        }
        return this;
    }

    public boolean isEquals(Matrix matrix) {
        if (this.rows != matrix.rows || this.cols != matrix.cols) {
            return false;
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.data[i][j] != matrix.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // tanspose of a matrix
    public Matrix transpose() {
        Matrix result = new Matrix(this.cols, this.rows); // switching numbers of cols and rows
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] = this.data[j][i];
            }
        }
        return result;
    }

    public boolean isSquare() {
        return this.rows == this.cols;
    }

    public Matrix power(int n){
        if (n == 0){
            return this.identity();
        }
        if (n == 1){
            return this;
        }
        System.out.println("ok");
        return this.multiply(this.power(n-1));
    }

}
