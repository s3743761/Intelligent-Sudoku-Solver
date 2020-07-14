package solver;
/*
Each row of the matrix is a circular Linked List to each other with left and right 
pointers and each column of the matrix will also be circular linked list linked to each above with up and down pointer.

Each Node contains following fields:
Pointer to node left to it
Pointer to node right to it
Pointer to node above it
Pointer to node below it
Pointet to list head node
*/
public class Node {
    public Node left, right, top, bottom,prev;
    public ColumnNode columnNode;
    public int rowId;

    public Node() {
       left = right = top = bottom = this;

    }

    public Node(ColumnNode column, int rowId){
        this();
        columnNode = column;
        this.rowId = rowId;

    }

    public Node linkRighNode(Node node){
        node.right = right;
        node.right.left = node;
        node.left = this;
        right = node;
        return node;
    }

    public Node linkDownNode(Node node){
        node.bottom = bottom;
        node.bottom.top = node;
        node.top = this;
        bottom = node;
        return node;
    }


    
}
