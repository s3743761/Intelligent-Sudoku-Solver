package solver;

import java.util.Arrays;

import grid.SudokuGrid;
/*

Reference : https://medium.com/javarevisited/building-a-sudoku-solver-in-java-with-dancing-links-180274b0b6c1
*/

public class SudokuUtils {

    int CONSTRAINTS = 4;
    private int SIZE;

    int[][] coverMatrix;

    public SudokuUtils(int SIZE){
        coverMatrix = new int[SIZE * SIZE * SIZE][SIZE * SIZE * CONSTRAINTS];
    }

    private int getCoverMatrixPosition(int row, int column, int num, int SIZE) {
        return (row) * SIZE * SIZE + (column) * SIZE + (num - 1);
    }


    //Each cell can contain only an integer between 1 and SIZE which corresponds to the size of the grid.
    public void populateCellConstraints(int SIZE) {
        int col = 0;
        for (int row = 0; row < SIZE * SIZE * SIZE; row++) {
            if (row > 0 && row % SIZE == 0) {
                col += 1;
            }
            coverMatrix[row][col] = 1;
        }
    }

    //Each row can contain only SIZE unique integers between 1 and SIZE.
    public void populateRowConstraints(int SIZE) {
        int startingColumnIndex = SIZE * SIZE;
        int col = startingColumnIndex;
        int maxCol = SIZE - 1;
        for (int i = 0; i < SIZE * SIZE * SIZE; i++) {
            // shift to right
            if (i > 0 && i % (SIZE * SIZE) == 0) {
                startingColumnIndex += SIZE;
                col = startingColumnIndex;
            }

            coverMatrix[i][col] = 1;

            if (col >= maxCol + startingColumnIndex) {
                col = startingColumnIndex;
            } else {
                col += 1;
            }
        }
    }

    //Each column can contain only SIZE unique integers between 1 and SIZE.
    public void populateColumnConstraints(int SIZE) {
        int startingColumnIndex = SIZE * SIZE * 2;
        int col = startingColumnIndex;
        for (int row = 0; row < SIZE * SIZE * SIZE; row++) {
            coverMatrix[row][col] = 1;
            if (col >= SIZE * SIZE * 2 + SIZE * SIZE - 1) {
                col = SIZE * SIZE * 2;
            } else {
                col += 1;
            }
        }
    }

    //Each box can contain only SIZE unique integers between 1 and SIZE.
    public void populateBoxConstraints(int SIZE) {
        int BOX_SIZE = (int) Math.sqrt(SIZE);
        int startingColumnIndex = SIZE * SIZE * 3;
        for (int row = 0; row < SIZE; row += BOX_SIZE) {
            for (int column = 0; column < SIZE; column += BOX_SIZE) {
                for (int n = 1; n <= SIZE; n++, startingColumnIndex++) {
                    for (int rowDelta = 0; rowDelta < BOX_SIZE; rowDelta++) {
                        for (int columnDelta = 0; columnDelta < BOX_SIZE; columnDelta++) {
                            int index = getCoverMatrixPosition(row + rowDelta, column + columnDelta, n, SIZE);
                            coverMatrix[index][startingColumnIndex] = 1;
                        }
                    }
                }
            }
        }
    }

    public void printCoverMatrix(int SIZE) {
        for (int row = 0; row < SIZE * SIZE * SIZE; row++) {
            for (int col = 0; col < 4 * SIZE * SIZE; col++) {
                System.out.print(coverMatrix[row][col]);
            }
            System.out.println();
        }
    }

    //Converting sudoku to cover Matrix
    public int[][] convertSudokuGridToCoverMatrix(SudokuGrid grid) {
        int SIZE = grid.getBoard().length;
        populateCellConstraints(SIZE);
        populateRowConstraints(SIZE);
        populateColumnConstraints(SIZE);
        populateBoxConstraints(SIZE);

        for(int row = 0 ; row < SIZE; row++) {
            for(int col = 0 ; col < SIZE ; col++) {
                int cell = grid.getBoard()[row][col];
                if(cell != 0){
                    for(int num = 1 ; num <= SIZE; num++){
                        if(num != cell){
                            Arrays.fill(coverMatrix[getCoverMatrixPosition(row,col,num, SIZE)],0);
                        }
                    }

                }
            }
        }
        return coverMatrix;
    }
}