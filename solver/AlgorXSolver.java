/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package solver;

import java.util.ArrayList;
import java.util.Arrays;


import grid.SudokuGrid;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


/**
 * Algorithm X solver for standard Sudoku.
 */
public class AlgorXSolver extends StdSudokuSolver {

    
    int CONSTRAINTS = 4;
    public AlgorXSolver() {
       
    } 

    @Override
    public boolean solve(SudokuGrid grid) {

        int[][] board = grid.getBoard();
        int SIZE = board.length;
        
      
        SudokuUtils utils = new SudokuUtils(SIZE);
        int[][] coverMatrix =  utils.convertSudokuGridToCoverMatrix(grid);
        


        ArrayList<HashMap<String , int[]>> matrixOfHashes  = new ArrayList<HashMap<String, int[]>>();
  
        int index = 0;
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++) {
                for(int num = 1; num <= SIZE; num++){
                    String key = num + "," + row + "," + col;
                    HashMap<String, int[]> rowHash = new HashMap<String, int[]>();
                    rowHash.put(key, coverMatrix[index]);
                    matrixOfHashes.add(index, rowHash);
                    index++;
                }
            }
        }

       

        ArrayList<String> solution = new ArrayList<String>();
        boolean result = applyAlgorithmX(matrixOfHashes, solution);
       
        for(int s = 0; s < solution.size(); s++) {
            String[] solutionCell = solution.get(s).split(",");
            int num = Integer.parseInt(solutionCell[0]);
            int x = Integer.parseInt(solutionCell[1]);
            int y = Integer.parseInt(solutionCell[2]);
            board[x][y] = num;
            
        }

       

        return result;
    } // end of solve()

    public boolean applyAlgorithmX(ArrayList<HashMap<String , int[]>> coverMatrixOfHashes, ArrayList<String> solution) {
   
        if(coverMatrixOfHashes.size() == 0){
            return true;
        } 

        int coverMatrix[][] = new int[coverMatrixOfHashes.size()][coverMatrixOfHashes.get(0).size()];
        for(int i = 0; i < coverMatrixOfHashes.size(); i++) {

            HashMap<String, int[]> r = coverMatrixOfHashes.get(i);
            
            for ( String key : r.keySet() ) {
                int[] values = r.get(key);    
                coverMatrix[i] = values;   
            }
        }

    
        int numberOfCol = coverMatrix[0].length;

        if(numberOfCol == 0){
            return true;
        } 

        HashMap<Integer, Integer> num1Count = addKeysToMap(coverMatrix);
       
        int column = getMinKeyValue(num1Count);
       
        if(column == -1){
            return false;
        }

        ArrayList<Integer> foundRows = getFoundRows(column, coverMatrix);
     
        for(int r = 0 ; r < foundRows.size() ; r++){
            int firstFoundRow = foundRows.get(r);

            String solutionKey = "";

            for ( String key : coverMatrixOfHashes.get(firstFoundRow).keySet() ) {
                solutionKey = key;
                solution.add(solutionKey);
            }
            

            ArrayList<Integer> foundColumn = getFoundColumn(coverMatrix, firstFoundRow);
            ArrayList<Integer> rowsToBeDeleted = getRowsToBeDeleted(coverMatrix, foundColumn); 
           
            ArrayList<HashMap<String , int[]>> copyMatrix = deleteFromMatrix(coverMatrixOfHashes, foundColumn, rowsToBeDeleted);
          
         
            boolean result = applyAlgorithmX(copyMatrix, solution);
            if(result){
                return true;
            }
            else{
                solution.remove(solutionKey);
            }

        }

        return false;
    
       

    }

    public ArrayList<HashMap<String , int[]>> deleteFromMatrix(ArrayList<HashMap<String , int[]>> coverMatrixOfHashes, 
        ArrayList<Integer> foundColumn, ArrayList<Integer> rowsToBeDeleted){
    
        ArrayList<HashMap<String , int[]>> copyMatrix  = new ArrayList<HashMap<String, int[]>>();
        int count = 0;
        for(int i = 0; i < coverMatrixOfHashes.size(); i++) {
            HashMap<String, int[]> r = coverMatrixOfHashes.get(i);
            HashMap<String, int[]> rCopy = new HashMap<String, int[]>(r);

            if(!rowsToBeDeleted.contains(i)){
                copyMatrix.add(count, rCopy);
                count++;
            }
        }
        if(copyMatrix.size() > 0){

            ArrayList<HashMap<String , int[]>> copyMatrixCol  = new ArrayList<HashMap<String, int[]>>();
            for(int i = 0; i < copyMatrix.size();i++){
                HashMap<String, int[]> r = copyMatrix.get(i);
                HashMap<String, int[]> rCopy = new HashMap<String, int[]>();
                
                for(String key : r.keySet()){
                    int[] values = r.get(key);
                    int[] reduced = new int[values.length - foundColumn.size()];
                    int countCol = 0;
                    for(int j = 0; j < values.length;j++){
                        if(!foundColumn.contains(j)){
                            reduced[countCol] = values[j];
                            countCol++;
                        }
                    }
                    rCopy.put(key, reduced);
                }

                copyMatrixCol.add(i, rCopy);
            }
        
            return copyMatrixCol;

        }
        else{
            return copyMatrix;
        }
       

       
    }

    public ArrayList<Integer> getRowsToBeDeleted(int[][] testMatrix, ArrayList<Integer> foundColumn){
        ArrayList<Integer> rowsToBeDeleted = new ArrayList<Integer>(); 
       
        for(int i = 0; i < testMatrix.length; i++ ){
            for(int j = 0 ; j < testMatrix[0].length;j++ ){
                if(foundColumn.contains(j)){
                    if(testMatrix[i] != null){
                        if(testMatrix[i][j] == 1){
                            if(!rowsToBeDeleted.contains(i)){
                                rowsToBeDeleted.add(i);
                            }
                        }
                    }
                }
                
            }
        }
        return rowsToBeDeleted;
    }

    public HashMap<Integer, Integer> addKeysToMap(int[][] testMatrix){
        HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();

        for (int i = 0; i < testMatrix.length; i++) {

            for (int j = 0; j < testMatrix[0].length; j++) {

                if (testMatrix[i][j] == 1) {
                    if (hmap.get(j) != null) {
                        hmap.put(j, hmap.get(j) + 1);
                    } 
                    else {
                        hmap.put(j, 1);
                    }
                }
                else{
                    if (hmap.get(j) == null) {
                        hmap.put(j, 0);
                    } 
                }
            }

        }

        return hmap;
                
        
    }

    public int getMinKeyValue(HashMap<Integer, Integer> hmap){
        int min = Integer.MAX_VALUE; 
        List<Integer> minKeys = new ArrayList<> ();
        for(Map.Entry<Integer, Integer> entry : hmap.entrySet()) {
            if(entry.getValue() < min) {
                min = entry.getValue();
                minKeys.clear();
            }
            if(entry.getValue() == min) {
                minKeys.add(entry.getKey());
            }
        }

        int choosenCol = minKeys.get(0);
        if(hmap.get(choosenCol) == 0){
            return -1;
        }
        return choosenCol;
    }


    public ArrayList<Integer> getFoundRows(int column, int[][] testMatrix){
        
        ArrayList<Integer> foundRows = new ArrayList<Integer>(); 
        
        for(int i = 0; i < testMatrix.length; i++ ){
            if(testMatrix[i][column] == 1){
                foundRows.add(i);
        
            }
        }
        return foundRows;
    }

    public ArrayList<Integer> getFoundColumn(int[][] testMatrix, int firstFoundRow){
        ArrayList<Integer> foundColumn = new ArrayList<Integer>(); 
        for(int i = 0; i < testMatrix[0].length; i++ ){
            if(testMatrix[firstFoundRow][i] == 1){
                foundColumn.add(i);
            }
        }
        return foundColumn;
    }


    public static int[][] deepCopy( int[][] original){
        if(original == null){
            return null;
        }
        final int[][] result = new int[original.length][];
        for(int i = 0 ; i < original.length ; i++){
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

} 
