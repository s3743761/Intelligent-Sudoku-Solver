/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.Cage;
import grid.Cell;
import grid.KillerSudokuGrid;
import grid.SudokuGrid;


/**
 * Backtracking solver for Killer Sudoku.
 */
public class KillerBackTrackingSolver extends KillerSudokuSolver
{

    public KillerBackTrackingSolver() {

    } // end of KillerBackTrackingSolver()
    
    @Override
    public boolean solve(SudokuGrid grid) {
        KillerSudokuGrid killerSudokuGrid = (KillerSudokuGrid) grid;
        Cell[][] sudoku = killerSudokuGrid.getGrid();
        Cage[] cages = killerSudokuGrid.getCages();
        int dim = sudoku.length;

       return solveKillerSudoku(cages, sudoku,0, 0,dim);
    }

    public boolean solveKillerSudoku(Cage[] cages, Cell[][] sudoku, int row, int col, int dim){
        for (int num = dim; num >= 1; num--) {
            if(row == dim) {
                return true;
            }

            if(isSafe(sudoku, row, col, num) && isCageSafe(cages, sudoku,row,col,num)){
                sudoku[row][col].setValue(num);

                int newRow = row + (col + 1) / dim;
                int newCol = (col + 1) % dim;

                if (solveKillerSudoku(cages, sudoku, newRow, newCol,dim)){
                    return true;
                }
                sudoku[row][col].setValue(0);
            }
        }


        return false;
    }

    /*
    Checks if there are unique values in each cage and the sum cells is less than the sum of cage or not.

    */

    private boolean isCageSafe(Cage[] cages, Cell[][] sudoku, int row, int col, int num) {
        int cageIndex = sudoku[row][col].getCageIndex();
        int sum = 0;
        Cage cage = cages[cageIndex];


        for(int i = 0; i < cage.getCells().size(); i++)
        {
            int row1 = cage.getCells().get(i).getRow();
            int col1 = cage.getCells().get(i).getColumn();
            if ( sudoku[row1][col1].getValue() == num) {
                return false;
            }
            sum += sudoku[row1][col1].getValue();
        }
        if(sum + num <= cage.getSum()){
            return true;
        }

        return false;
    }

    /*
    Checks after assigning the current index of the grid becoms unsafe or not.
    keep track for a row, column and boxes. If any number has a frequency greater than 1 in the grid, 
    it returns false else return true.
    */

    private boolean isSafe(Cell[][] sudoku, int row, int col, int num) {
        for (int d = 0; d < sudoku.length; d++) {
            if (sudoku[row][d].getValue() == num) { 
                return false; 
            } 
        } 

        for (int r = 0; r < sudoku.length; r++) { 
            if (sudoku[r][col].getValue() == num) { 
                return false; 
            } 
        } 
        int sqrt = (int) Math.sqrt(sudoku.length); 
        int boxRowStart = row - row % sqrt; 
        int boxColStart = col - col % sqrt; 
 
        for (int r = boxRowStart; r < boxRowStart + sqrt; r++){ 
            for (int d = boxColStart; d < boxColStart + sqrt; d++) { 
                if (sudoku[r][d].getValue() == num) { 
                    return false; 
                } 
            } 
        } 
        return true;
    }

   
} // end of class KillerBackTrackingSolver()
