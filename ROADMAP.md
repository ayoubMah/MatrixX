# MatrixX Development Roadmap

This document outlines the planned future features and development phases for the MatrixX library. Our primary goal is to provide a pure-Java, dependency-free matrix manipulation library targeted at students, independent game developers, Android engineering, and robust scientific simulation tooling.

---

## Phase 1: Quick Wins & Polish (v0.4)
*Focus: Enhancing usability, adding standard primitive support, and maturing the API syntax for everyday coding.*

- [ ] **Float Support (`MatrixF`)**: Add single-precision support to radically decrease memory footprint—essential for Android, real-time gaming, and embedded systems.
- [ ] **Vector Class Implementation**:
  - Add native column/row Vector classes with seamless `Matrix <-> Vector` interoperability.
  - Operations: `.asRow()`, `.norm()`, `.normalize()`, `.outerProduct(Vector)`.
- [ ] **Immutability & Fluent Builder API**:
  - Guarantee immutability in chaining: `A.plus(B).multiply(C).transpose()`.
  - Add `.toImmutable()` or standard immutable subtypes for Thread Safety.
- [ ] **Additional Factory Methods**:
  - `randomNormal(mean, std)`
  - `randomUniform(min, max)`
  - `linspace(start, stop, num)`
  - `meshgrid`, `toeplitz`, `vandermonde`, `magic(n)`.

---

## Phase 2: Differentiation & Broad Appeal (v0.5)
*Focus: Stand out from generic Java math libraries by targeting specific engineering and scientific domains.*

- [ ] **Complex Number Matrices (`MatrixC`)**: 
  - Essential for signal processing (FFTs), electrical engineering, and quantum computing simulations.
- [ ] **Functional Element-Wise Operations**:
  - Expanded lambdas: `.map((row, col, val) -> ...)`, `.mapBi((a, b) -> ...)`.
  - Advanced mapping: `.filter()`, `.zipWith()`.
- [ ] **In-Place / Mutating Variants (Performance Critical)**:
  - Add `.addInPlace(other)`, `.mulInPlace(scalar)`, `.mapInPlace(...)` to avoid GC overhead and memory allocations in hot loops.
- [ ] **Basic Row/Column Statistics**:
  - `.rowMean()`, `.columnStd()`.
  - `.min()`, `.max()`, `.argmin()`, `.argmax()`.
  - `.covarianceMatrix()`.
- [ ] **Enhanced Error Handling**:
  - Replace raw `IllegalArgumentExceptions` with contextual ones: `SingularMatrixException`, `DimensionMismatchException`, `NotPositiveDefiniteException`.

---

## Phase 3: Advanced & Performance Features (v1.0+)
*Focus: Deepening mathematical capabilities and modern Java optimizations.*

- [ ] **Singular Value Decomposition (SVD)**:
  - Core requirement for PCA, Pseudo-inverses, and loss-rank approximations.
- [ ] **Advanced Eigen Decompositions**:
  - Expand beyond analytical 2x2/3x3 matrices using algorithms like the Power Method or iterative QR for larger, real symmetric cases.
- [ ] **Sparse Matrix Support (CSR / COO)**:
  - Data architecture for handling graph algorithms and giant scientific data mappings.
- [ ] **Leverage Java Vector API (JDK 21+)**:
  - Use modern Java SIMD access to dramatically speed up inner loop calculations (dot products, matrix multiplications).
- [ ] **Optional Mini-DSL**:
  - Allow parsing natural math equations directly: `"C = 2 * A.inv() * B + eye(5)"`.
- [ ] **Serialization**:
  - Allow round-trip JSON and Binary saves/loads natively or via mapped Jackson adapters.

---

## Future / Community-Driven Ideas (Post v1.0)
- Multi-threaded matmul / decompositions (leveraging `ForkJoinPool` or JDK 21 Virtual Threads).
- Micro-benchmark publishing (JMH) to validate optimizations.
- Implementation of specialized niche matrices (Unitary, Skew-Symmetric).
- Kotlin-friendly extension operators.
