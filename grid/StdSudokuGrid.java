/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package grid;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

import java.io.*;



/**
 * Class implementing the grid for standard Sudoku.
 * Extends SudokuGrid (hence implements all abstract methods in that abstract
 * class).
 * You will need to complete the implementation for this for task A and
 * subsequently use it to complete the other classes.
 * See the comments in SudokuGrid to understand what each overriden method is
 * aiming to do (and hence what you should aim for in your implementation).
 */
public class StdSudokuGrid extends SudokuGrid
{
    
    private int[][] grid ;
    int dim = 0 ;
    
    public StdSudokuGrid() {
        super();
    } // end of StdSudokuGrid()


    /* ********************************************************* */


    @Override
    public void initGrid(String filename)
        throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filename));;
        try {
            String line0 = reader.readLine();
            // String line0 = Files.readAllLines(Paths.get(filename)).get(0);
            dim = Integer.parseInt(line0);
            grid = new int[dim][dim];
            // int linecount = 0;   
            
            
            // possible values
            String line1 = reader.readLine();
            String data = reader.readLine();
            while (data != null) {
                
                String[] st = data.trim().split("[, ]");
                int rowIndex = Integer.parseInt(st[0]);
                int colIndex = Integer.parseInt(st[1]);
                int value = Integer.parseInt(st[2]);

                grid[rowIndex][colIndex] = value;
                data = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
 
    } // end of initBoard()


    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        StringBuilder build = new StringBuilder();
        for(int i = 0 ; i< grid.length;++i){
            for(int j = 0 ; j < grid.length;++j){
                build.append(grid[i][j]+"");
                if( j < grid.length - 1){
                    build.append(",");
                }
            }
            build.append("\n");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(build.toString());
        writer.close();

     
    } 


    @Override
    public String toString() {
        String line = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (int[] row : grid) {
            sb.append(Arrays.toString(row)).append(line);
        }
        
        String result = sb.toString();

        return result;
    } // end of toString()


    @Override
    public boolean validate() {
        //validates if each cell has a unique value.
        for(int i  = 0 ; i <  grid.length ; i++ ){
            for(int j = 0; j < grid[0].length ; j++){
                if(grid[i][j] == 0){
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
                        if(grid[row + pos%sqrt][col + pos/sqrt]==grid[row + pos2%sqrt][col + pos2/sqrt]){
                            return false;
                        }
                    }
                }
            }
        }
        
    return true;      
    }
    
    @Override
    public int[][] getBoard(){
        return grid;
    }
} 
