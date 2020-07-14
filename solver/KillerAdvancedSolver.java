/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import java.util.ArrayList;
import java.util.Collections;

import grid.Cage;
import grid.Cell;
import grid.KillerSudokuGrid;
import grid.SudokuGrid;
import javafx.scene.layout.RowConstraints;


/**
 * Your advanced solver for Killer Sudoku.
 */
public class KillerAdvancedSolver extends KillerSudokuSolver
{
    private ArrayList<ArrayList<Integer>> sumConstraintsCells;
    // TODO: Add attributes as needed.

    public KillerAdvancedSolver() {
        sumConstraintsCells = new ArrayList<ArrayList<Integer>>();
    
    } // end of KillerAdvancedSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // ArrayList<Integer> partial = new ArrayList<Integer>();
        // int[] numbers = {1,2,3,4,5,6,7,8,9};
        // sumElimination(numbers, 10, 3, partial);
        // System.out.println(sumConstraintsCells);
        KillerSudokuGrid killerSudokuGrid = (KillerSudokuGrid) grid;
        Cell[][] sudoku = killerSudokuGrid.getGrid();
        Cage[] cages = killerSudokuGrid.getCages();
        ruleOf45(sudoku,cages, killerSudokuGrid.getRegionSum());

        return false;
    } 

    public void sumElimination(int[] numbers, int cageSum, int cageLength, ArrayList<Integer> partial){
        int sum = 0;
        for(int i = 0 ; i < partial.size(); i++){
            sum += partial.get(i);
        }

        if(sum == cageSum && partial.size() == cageLength){
            
            Collections.sort(partial);
            if(!sumConstraintsCells.contains(partial)){
                sumConstraintsCells.add(partial);
            }
        }

        if(sum >= cageSum){
            return;
        }

        for(int j = 0; j < numbers.length; j++){
            int n = numbers[j];
            ArrayList<Integer> newPartial = new ArrayList<Integer>(partial);
            if(!partial.contains(n)){
                newPartial.add(n);
            }
            int[] remain = new int[numbers.length - 1];
            for(int k = 1; k < numbers.length; k++){
                remain[k-1] = numbers[k];
            }
            sumElimination(remain, cageSum, cageLength, newPartial);
        }

    }

    public void ruleOf45(Cell[][] grid, Cage[] cages, int regionSum){
        ArrayList<ArrayList<Cage>> rows = new ArrayList<ArrayList<Cage>>();
        for(int i = 0; i < grid.length; i++){
            
            ArrayList<Cage> rowList = new  ArrayList<Cage>();
            for(int k = 0;k < cages.length;k++){

                boolean rowContraint = true;

                for(int p = 0 ; p < cages[k].getCells().size(); p++){
                    if(cages[k].getCells().get(p).getRow() != i){
                        rowContraint = false;
                    }
                }
                if(rowContraint == true){
                    rowList.add(cages[k]);
                }
            }
            rows.add(rowList);
        }

        ArrayList<ArrayList<Cage>> columns = new ArrayList<ArrayList<Cage>>();
        for(int j = 0; j < grid.length; j++){
            
            ArrayList<Cage> colList = new  ArrayList<Cage>();
            for(int z = 0; z < cages.length; z++){

                boolean colContraint = true;

                for(int q = 0 ; q < cages[z].getCells().size(); q++){
                    if(cages[z].getCells().get(q).getColumn() != j){
                        colContraint = false;
                    }
                }
                if(colContraint == true){
                    colList.add(cages[z]);
                }
            }
            columns.add(colList);
        }

        for(int d = 0 ; d < columns.size() ; d++){

            int colSum = 0;
            int numCells = 0;
            int row = -1;

            for(int e = 0 ; e < columns.get(d).size();e++){
                Cage cageInCol = columns.get(d).get(e);
                colSum +=cageInCol.getSum();
                numCells += cageInCol.getCells().size();
                row = cageInCol.getCells().get( cageInCol.getCells().size() - 1).getRow();
            }

            if((numCells == grid.length - 1) && row != -1){
                int remainingSum = regionSum - colSum;
                grid[row][d].setValue(remainingSum);
            }
            
        }

        for(int r = 0 ; r < rows.size() ; r++){

            int rowSum = 0;
            int numCells = 0;
            int selectedCol = -1;

            for(int e = 0 ; e < columns.get(r).size();e++){
                Cage cageInCol = columns.get(r).get(e);
                rowSum +=cageInCol.getSum();
                numCells += cageInCol.getCells().size();
                selectedCol = cageInCol.getCells().get( cageInCol.getCells().size() - 1).getRow();
            }

            if((numCells == grid.length - 1) && selectedCol != -1){
                int remainingSum = regionSum - rowSum;
                grid[selectedCol][r].setValue(remainingSum);
            }
            
        }



    }

} 
