package grid;
/*
The Cage class describes each coloured block in the Killer Sudoku puzzle. 
It has a sum attribute and a variable list of Cells. 

*/
import java.util.ArrayList;

public class Cage {
    private int sum;
    private ArrayList<Cell> cells ;

    public Cage(int sum){
        this.sum = sum;
        cells = new ArrayList<Cell>();
    }

    public ArrayList<Cell> getCells(){
        return this.cells;
    }

    public void addCell(Cell cell){
        cells.add(cell);
    }

    public int getSum(){
        return this.sum;
    }
}