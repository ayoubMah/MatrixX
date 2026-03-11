# MatrixX

[![](https://jitpack.io/v/ayoubMah/MatrixX.svg)](https://jitpack.io/#ayoubMah/MatrixX)

MatrixX is a Java library designed to provide a robust and intuitive API for matrix manipulation and algebra.

---

## Features

###  Matrix Creation
- **Identity Matrix**: Generate identity matrices of any size.
- **Zero Matrix**: Create zero matrices.
- **From Array**: Instantiate matrices directly from 2D arrays.
- **Static Generators**: Scaffold matrices quickly using `ones()`, `constant()`, and `random()`.
- **String Parsing**: Instantiate matrices intuitively from MATLAB-style strings (e.g., `"[1 2; 3 4]"`).

###  Print / Display
- Easily print matrices in a readable format.

###  Matrix Operations
- **Addition**: Element-wise matrix addition.
- **Subtraction**: Element-wise matrix subtraction.
- **Multiplication**: Standard matrix multiplication.
- **Hadamard Product**: Element-wise matrix multiplication (`hadamardMultiply()`).
- **Dot Product**: Perform inner dot products on vector constraints (`dotProduct()`).
- **Map Function**: Pass functional Lambda equations (`DoubleUnaryOperator`) over every matrix item (`map()`).

###  Utility Operations
- **Transpose**: Get the transpose of a matrix.
- **Scalar Multiplication**: Multiply a matrix by a scalar.
- **Equality Check**: Compare matrices for equality.
- **Matrix Power (Aⁿ)**: Raise a matrix to an integer power.
- **Determinant**: Compute determinants (supports 2x2, 3x3, and recursive for larger matrices).
- **Inverse**: Calculate the inverse of a matrix (if it exists).

###  Algebraic Tools
- **Linear Solver**: Analytically solve linear equations of the form $A \cdot X = B$ using `solve()`.
- **Decompositions**: 
  - **LU**: Decompose a matrix into lower and upper triangular matrices.
  - **Cholesky**: Robust decomposition for symmetric, positive-definite matrices.
  - **QR**: Gram-Schmidt orthogonalization yielding $Q$ and $R$ factors.
- **Eigen Solvers**: Analytically process real Eigenvalues and cross-product Eigenvectors for 2x2 and 3x3 matrices using `eigen()`.
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
        // 1. Matrix Creation
        double[][] data = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix A = new Matrix(data);
        Matrix I = Matrix.identity(3);

        // 2. Arithmetic Operations
        Matrix sum = A.add(I);
        System.out.println("A + I =");
        sum.print();

        // 3. Advanced Algebraic Tools
        Matrix B = new Matrix(new double[][]{
            {4, 7}, 
            {2, 6}
        });
        
        System.out.println("Determinant of B: " + B.determinant());
        
        System.out.println("Inverse of B:");
        B.inverse().print();
        
        // 4. Matrix Decompositions (LU Factorization)
        Matrix.LUPair lu = B.lu();
        System.out.println("L Matrix:");
        lu.L().print();
        System.out.println("U Matrix:");
        lu.U().print();
        
        // 5. Matrix Typed Validations
        System.out.println("Is I a Diagonal Matrix? " + I.isDiagonal());
        
        // 6. Linear Solvers & MATLAB String Parsing
        Matrix equations = Matrix.fromMatlab("[1 2; 3 4]");
        Matrix constants = Matrix.fromMatlab("[5; 11]");
        Matrix solution = equations.solve(constants);
        
        System.out.println("Solution to linear system:");
        solution.print();
    }
}
```

---

## Roadmap & Future Development

MatrixX is under active development.
We are pushing towards **v0.4**, targeting **Float Matrices**, **Vector classes**, and overall syntax polish!

To see what's planned for future releases—including Complex Numbers, Matrix Statistics, Singular Value Decomposition (SVD), and SIMD hardware acceleration—check out our detailed [Development Roadmap](ROADMAP.md).

---

## Contributing

PRs, feature requests, and bug reports are welcome :)

---

## License

This project is licensed under the MIT License.

---

## Author

[ayoubMah](https://github.com/ayoubMah)
