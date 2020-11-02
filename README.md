# Intelligent Sudoku Solver
Applies transform-and-conquer strategies to solve Sudoku puzzles of various sizes.

Implements various algorithms to solve the puzzle: Backtracking, Algorithm X and Dancing Links
approach (using Exact Cover and Linked Lists) which solved a hard 16x16 puzzle in just 0.08 seconds .

### Code Structure

Followed Object Oriented Principles: encapsulation, abstraction, inheritance, and polymorphism. 
- `Sudoku.java` Class implements basic IO and processing code. 

- `grid/SudokuGrid.java` is an abstract class for Sudoku Grids. Can be implemented by either Standard Sudoku Grid or a Killer Sudoku Grid
- `grid/StdSudokuGrid.java` represent a Standard Sudoku Grid of variable size (4x4, 9x9, etc.)

- `solver/StdSudokuSolver.java` is an abstract class for Sudoku solver algorithms, contains common methods.
- `BackTrackingSolver.java`, `AlgorXSolver.java`, `DancingLinksSolver.java` implements individual solving techniques
### Tests
Tested the solver with various sudoku puzzles, 4x4, 9x9, 16x16 and 24x24. 
All tests are listed in the sampleGames folder.