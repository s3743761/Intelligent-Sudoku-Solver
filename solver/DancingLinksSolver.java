/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import grid.SudokuGrid;


/**
 * Dancing links solver for standard Sudoku.
 */
public class DancingLinksSolver extends StdSudokuSolver
{

    // private ColumnNode header;
    private List<Node> solution = new ArrayList<Node>();;
    ColumnNode headerNode = new ColumnNode(-1);
    ArrayList<ColumnNode> columnNodes = new ArrayList<ColumnNode>();
    

    public DancingLinksSolver() {
        // TODO: any initialisation you want to implement.

    } // end of DancingLinksSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
       
        int[][] board = grid.getBoard();
        int SIZE = board.length;
        SudokuUtils utils = new SudokuUtils(SIZE);
        int[][] coverMatrix = utils.convertSudokuGridToCoverMatrix(grid);
        createDLXList(coverMatrix);

      
        
        boolean validSolution = search(0);
     

        if(!validSolution ){
            return false;
        }

        ArrayList<Integer> solutionRowIds = new ArrayList<Integer>();
        for(int i = 0 ; i < solution.size(); i++){
            
            solutionRowIds.add(solution.get(i).rowId);
            
        }
        
        int rowCounter = 1;
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++) {
                for(int num = 1; num <= SIZE; num++){
                    
                    if(solutionRowIds.contains(rowCounter)){
                       
                        board[row][col] = num;
                    }
                    rowCounter += 1;
                }
            }
        }

        return true;
    } 


    //Converts the grid to a Linked List.
    public void createDLXList(int[][] sampleMatrix) {
        
        
        headerNode.size = sampleMatrix[0].length;
        
        for(int i = 0 ; i < sampleMatrix[0].length;i++){
            ColumnNode c = new ColumnNode(i);
            columnNodes.add(c);
            if( i >= 1 ){
                ColumnNode leftNode = columnNodes.get(i-1);
                c.left = leftNode;
                c.left.right = c;
            }
          
        }

        headerNode.right = columnNodes.get(0);
        columnNodes.get(0).left = headerNode;

        headerNode.left = columnNodes.get( columnNodes.size() - 1);
        columnNodes.get( columnNodes.size() - 1).right = headerNode;

        

        for(int i = 0 ; i < sampleMatrix.length;i++){
            Node prev = null;
            for(int j = 0 ; j < sampleMatrix[0].length ; j++){

                if(sampleMatrix[i][j] == 1){
                    ColumnNode c =  columnNodes.get(j);
                    c.size++;
                    Node node = new Node(c, (i + 1));
                  

                    if( prev == null){
                        prev = node;
                    }

                    c.top.linkDownNode(node);
                    
                    prev = prev.linkRighNode(node);

                
                }
                    
            }
        }

       
    }
    

    private boolean search(int k){
       
        if(headerNode.right == headerNode){
            
            return true;
        }
        else{
            ColumnNode column = getMinColumnNode();

            if(column.id == -1){
                return false;
            }
            
            cover(column);
            
            for(Node row = column.bottom; row != column; row = row.bottom){
                solution.add(row);
               

                for(Node rightNode = row.right; rightNode != row; rightNode = rightNode.right){
                    cover(rightNode.columnNode);
                }

                if(search(k+1) == true){
                    return true;
                }
                
                solution.remove(solution.size() -1);
               
                column = row.columnNode;

                for( Node leftNode = row.left ; leftNode != row ; leftNode = leftNode.left ) {                                                                            
                    uncover( leftNode.columnNode ); 
                } 
                
            }

            uncover(column);

        }
        return false;
        
    }

    private ColumnNode getMinColumnNode(){
        ColumnNode chosenColumn = null;
        int min = Integer.MAX_VALUE; 
        for(ColumnNode c = headerNode.right.columnNode ; c != headerNode;c = c.right.columnNode ){
            
            if(c.size < min){
                chosenColumn = c;
                min = c.size;
            }
        }

        return chosenColumn;
    }

    /*
    Deletion of columns and all the rows to which nodes of that  column belongs to.
    To remove a column we unlink header of that column from neighbouring headers.Similarly to remove a row we have to unlink all
    nodes of row from nodes of row above and below it.
   
    */
    private void cover(ColumnNode column){
        column.right.left = column.left;
        column.left.right = column.right;

        for(Node row = column.bottom; row != column; row = row.bottom){
            for(Node rightNode = row.right ; rightNode != row; rightNode = rightNode.right){
                rightNode.top.bottom = rightNode.bottom;
                rightNode.bottom.top = rightNode.top;
            }
        }
    }

    /*
    When the algorithm reahces a dead end and no solution is possible, the algorithms needs to backtrack.
    As we had removed columns and rows, when backtracking, we need to link again those remooved rows and columns.
    */
    private void uncover(ColumnNode column){
        for(Node row = column.top; row != column; row = row.top){
            for(Node leftNode = row.left ; leftNode != row; leftNode = leftNode.left){
                leftNode.top.bottom = leftNode;
                leftNode.bottom.top = leftNode;
                column.size++;
            }
        }
        column.left.right = column; 
        column.right.left = column; 
    }
} 

