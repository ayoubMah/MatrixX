# MatrixX

[![](https://jitpack.io/v/ayoubMah/MatrixX.svg)](https://jitpack.io/#ayoubMah/MatrixX)

MatrixX is a Java library designed to provide a robust and intuitive API for matrix manipulation and algebra.

---

## Features

###  Matrix Creation
- **Identity Matrix**: Generate identity matrices of any size.
- **Zero Matrix**: Create zero matrices.
- **From Array**: Instantiate matrices directly from 2D arrays.

###  Print / Display
- Easily print matrices in a readable format.

###  Matrix Operations
- **Addition**: Element-wise matrix addition.
- **Subtraction**: Element-wise matrix subtraction.
- **Multiplication**: Standard matrix multiplication.

###  Utility Operations
- **Transpose**: Get the transpose of a matrix.
- **Scalar Multiplication**: Multiply a matrix by a scalar.
- **Equality Check**: Compare matrices for equality.
- **Matrix Power (Aⁿ)**: Raise a matrix to an integer power.
- **Determinant**: Compute determinants (supports 2x2, 3x3, and recursive for larger matrices).
- **Inverse**: Calculate the inverse of a matrix (if it exists).

###  Algebraic Tools
- **LU Decomposition**: Decompose a matrix into lower and upper triangular matrices.
- **Gauss-Jordan Elimination**: Perform row reduction for solving systems or finding inverses.
- **Rank of Matrix**: Compute the rank.
- **Reduced Row Echelon Form (RREF)**: Reduce matrices to their canonical form.

###  Special Matrix Types Support
- **Diagonal Matrices**
- **Symmetric Matrices**
- **Identity Matrices**
- **Orthogonal Matrices**

---

## Installation

### Step 1. Add the JitPack repository
Add the JitPack repository to your `pom.xml` file:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Step 2. Add the dependency
Add `MatrixX` to your project dependencies:

```xml
<dependency>
    <groupId>com.github.ayoubMah</groupId>
    <artifactId>MatrixX</artifactId>
    <version>v0.2</version>
</dependency>
```

Alternatively, you can clone the repository and build it locally using Maven:

```shell
git clone https://github.com/ayoubMah/MatrixX.git
cd MatrixX
mvn clean install
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
- [x] Determinant and inverse (recursive algorithms)
- [x] LU decomposition, Gauss-Jordan, RREF
- [x] Special matrix type detection and optimizations

---

## Contributing

PRs, feature requests, and bug reports are welcome :)

---

## License

This project is licensed under the MIT License.

---

## Author

[ayoubMah](https://github.com/ayoubMah)
