package solver;

public class ColumnNode extends Node {
   
    public int size;
    public int id;

    public ColumnNode(int id){
        super();
        size = 0;
        this.id = id;
        columnNode = this;

    }
}