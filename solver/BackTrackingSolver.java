/*
  * @author Jeffrey Chan & Minyi Li, RMIT 2020
  */

  package solver;
 
  import grid.SudokuGrid;
 
 
  /**
   * Backtracking solver for standard Sudoku.
   */
  public class BackTrackingSolver extends StdSudokuSolver
  {
      // TODO: Add attributes as needed.
 
      public BackTrackingSolver() {
          // TODO: any initialisation you want to implement.
      } // end of BackTrackingSolver()
 
 
    @Override
    public boolean solve(SudokuGrid grid) {
        int[][] board = grid.getBoard(); 
        int N = board.length;
        int row = -1; 
        int col = -1; 
        boolean empty = true; 
        for (int i = 0; i < N; i++) { 
            for (int j = 0; j < N; j++){ 
                if (board[i][j] == 0){  
                    row = i; 
                    col = j; 
                    empty = false;  
                    break; 
                } 
            }
            if(!empty){
                break;
            }
        }  
        if (empty){ 
            return true; 
        } 
        for(int num = 1; num <= N; num++){
            if(isSafe(board,row,col,num)){
                board[row][col] = num;
                if(solve(grid)){
                    return true;
                }
                else{
                    board[row][col] = 0;
                }
            }
        }   
        return false;
        
    } 
 
 
 
    public static boolean isSafe(int[][] board, int row, int col, int num)  { 
        for (int d = 0; d < board.length; d++) {
            if (board[row][d] == num) { 
                return false; 
            } 
        } 
        for (int r = 0; r < board.length; r++){ 
            if (board[r][col] == num){ 
                return false; 
            } 
        } 

        int sqrt = (int) Math.sqrt(board.length); 
        int rowBox = row - row % sqrt; 
        int colBox = col - col % sqrt; 
 
        for (int r = rowBox; r < rowBox + sqrt; r++) { 
            for (int d = colBox; d < colBox + sqrt; d++){ 
                if (board[r][d] == num){ 
                    return false; 
                } 
            } 
        } 
        return true; 
    } 
 
   
}