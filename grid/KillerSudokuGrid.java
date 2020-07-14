/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package grid;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors


/**
 * Class implementing the grid for Killer Sudoku. Extends SudokuGrid (hence
 * implements all abstract methods in that abstract class). You will need to
 * complete the implementation for this for task E and subsequently use it to
 * complete the other classes. See the comments in SudokuGrid to understand what
 * each overriden method is aiming to do (and hence what you should aim for in
 * your implementation).
 */
public class KillerSudokuGrid extends SudokuGrid {

    private int dim;
    Cell[][] grid;
    Cage[] cages; 

    public KillerSudokuGrid() {
        super();    
            
    } 

   

    @Override
    public void initGrid(String filename) throws FileNotFoundException, IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        try {
            dim = Integer.parseInt(reader.readLine());
            grid = new Cell[dim][dim];

            reader.readLine();
            int numCages = Integer.parseInt(reader.readLine());
            cages = new Cage[numCages];

            String data = reader.readLine();

            int cageIndex = 0;
            while(data != null) {
                String[] cageCells = data.split("[ , ]");
                int cageSum =  Integer.parseInt(cageCells[0]);
    
                Cage cage = new Cage(cageSum);
                cages[cageIndex] = cage;
    
                for(int l = 1; l < cageCells.length; l += 2){
                    int row = Integer.parseInt(cageCells[l]);
                    int col = Integer.parseInt(cageCells[l+1]);
    
                    Cell cell = new Cell(row, col, cageIndex);
                    grid[row][col] = cell;
                    cage.addCell(cell);
                }

                data = reader.readLine();
                cageIndex++;
            }

            reader.close();
        } 

        
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
   

    } 

    @Override
    public void outputGrid(String filename) throws FileNotFoundException, IOException {
        StringBuilder build = new StringBuilder();
        for(int i = 0 ; i< grid.length;++i){
            for(int j = 0 ; j < grid.length;++j){
                build.append(grid[i][j].getValue() +"");
                if( j < grid.length - 1){
                    build.append(",");
                }
            }
            build.append("\n");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(build.toString());
        writer.close();
    } // end of outputBoard()

    @Override
    public String toString() {
        String line = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < grid.length ; i++){
            for(int j = 0 ; j < grid[0].length ; j++){
                sb.append(grid[i][j].getValue());
                if(j != grid[0].length - 1){
                    sb.append(" ");
                }
            }
            sb.append(line);
        }
        
        String result = sb.toString();

        return result;
        

    } 

    @Override
    public boolean validate() {
        for(int i  = 0 ; i <  grid.length ; i++ ){
            for(int j = 0; j < grid[0].length ; j++){
                if(grid[i][j].getValue() == 0){
                    return false;
                }
            }
        }

        //validates if each row has a unique value.
        for(int i  = 0 ; i <  grid.length ; i++ ){
            for(int j = 0; j < i; j++){
                if(grid[i] == grid[j]){
                    return false;
                }
            }
        }

         //validates if each column has a unique value.
        for(int i  = 0 ; i <  grid[0].length ; i++ ){
            for(int j = 0; j < i; j++){
                if(grid[i] == grid[j]){
                    return false;
                }
            }
        }

        int sqrt = (int) Math.sqrt(dim);
   
        
        //validates if each grid has a unique value.
        for(int row = 0; row < grid.length;row += sqrt){
            for(int col = 0; col < grid[0].length ; col += sqrt){
                for(int pos = 0; pos < grid.length - 1; pos++){
                    for(int pos2 = pos + 1; pos2 < grid.length; pos2++){
                        if(grid[row + pos%sqrt][col + pos/sqrt].getValue() == grid[row + pos2%sqrt][col + pos2/sqrt].getValue()){
                            return false;
                        }
                    }
                }
            }
        }
        
    return true; 
    } // end of validate()


    @Override
    public int[][] getBoard() {
        return  null;
    }

    public Cage[] getCages() {
       return cages;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getSize(){
        return dim;
    }

    public int getRegionSum() {
        int regionSum = 0;
        for(int i = 1; i <= dim; i++) {
            regionSum += i;
        }

        return regionSum;
    }

} 
