package grid;
/*
The Cell class describes one cell in our Killer Sudoku Puzzle. 
It has a row and column attributes, to signify it’s position in the 
grid (column 0 being the left-most column and row 0 being the top-most row). It also has a value 
attribute (initialised as 0) as well as a cage index since a cell can only belong to one cage. 
*/
public class Cell {
    private int row;
    private int column;
    private int value;
    private int cageIndex;
    

    public Cell(int row, int col,int cageIndex){
        this.row = row;
        this.column = col;
        value = 0 ;
        this.cageIndex = cageIndex;
        // cages = new ArrayList<Cage>();
    }

    public int getCageIndex(){
        return this.cageIndex;
    }
    
    public void setCageIndex(int cageIndex){
        this.cageIndex = cageIndex;
    }


    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }
}