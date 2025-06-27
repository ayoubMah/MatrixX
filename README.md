# MatrixX

MatrixX is a Java library designed to provide a robust and intuitive API for matrix manipulation and algebra. Whether you are working on scientific computing, engineering, data analysis, or algorithm development, MatrixX delivers high-performance matrix operations and algebraic tools out of the box.

---

## Features

### 🟦 Matrix Creation
- **Identity Matrix**: Generate identity matrices of any size.
- **Zero Matrix**: Create zero matrices.
- **From Array**: Instantiate matrices directly from 2D arrays.

### 🖨️ Print / Display
- Easily print matrices in a readable format.

### ➕ Matrix Operations
- **Addition**: Element-wise matrix addition.
- **Subtraction**: Element-wise matrix subtraction.
- **Multiplication**: Standard matrix multiplication.

### 🔹 Utility Operations
- **Transpose**: Get the transpose of a matrix.
- **Scalar Multiplication**: Multiply a matrix by a scalar.
- **Equality Check**: Compare matrices for equality.
- **Matrix Power (Aⁿ)**: Raise a matrix to an integer power.
- **Determinant**: Compute determinants (supports 2x2, 3x3, and recursive for larger matrices).
- **Inverse**: Calculate the inverse of a matrix (if it exists).

### 🔸 Algebraic Tools
- **LU Decomposition**: Decompose a matrix into lower and upper triangular matrices.
- **Gauss-Jordan Elimination**: Perform row reduction for solving systems or finding inverses.
- **Rank of Matrix**: Compute the rank.
- **Reduced Row Echelon Form (RREF)**: Reduce matrices to their canonical form.

### 🔹 Special Matrix Types Support
- **Diagonal Matrices**
- **Symmetric Matrices**
- **Identity Matrices**
- **Orthogonal Matrices**

---

## Installation

Add `MatrixX` to your Java project (Maven/Gradle setup coming soon).  
For now, clone the repository and include the source files in your project:

```shell
git clone https://github.com/ayoubMah/MatrixX.git
```

---

## Basic Usage

```java
import matrixx.Matrix;

public class Example {
    public static void main(String[] args) {
        double[][] data = {
            {1, 2},
            {3, 4}
        };
        Matrix A = new Matrix(data);
        Matrix B = Matrix.identity(2);

        Matrix sum = A.add(B);
        sum.print(); // Display the resulting matrix
    }
}
```

---

## Roadmap

- [x] Matrix creation and display
- [x] Basic arithmetic operations
- [x] Transpose, scalar multiplication, equality check
- [ ] Determinant and inverse (recursive algorithms)
- [ ] LU decomposition, Gauss-Jordan, RREF
- [ ] Special matrix type detection and optimizations

---

## Contributing

Pull requests, feature requests, and bug reports are welcome!  
Please open an issue to discuss your ideas or questions.

---

## License

This project is licensed under the MIT License.

---

## Author

[ayoubMah](https://github.com/ayoubMah)
