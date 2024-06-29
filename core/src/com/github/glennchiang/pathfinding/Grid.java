package com.github.glennchiang.pathfinding;

// The grid on which the pathfinding algorithms will act
// Stores obstacle positions
public class Grid {
    public final int numRows;
    public final int numCols;
    private final CellType[][] grid;

    private int startRow;
    private int startCol;
    private int targetRow;
    private int targetCol;

    // The proportion of cells that are obstacles
    private float obstacleDensity = 0.2f;

    public Grid(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new CellType[numRows][numCols];
        for (int row = 0; row < this.numRows; row ++) {
            for (int col = 0; col < this.numCols; col++) {
                grid[row][col] = CellType.EMPTY;
            }
        }
    }

    public int getStartRow() {
        return startRow;
    }
    public int getStartCol() {
        return startCol;
    }
    public int getTargetRow() {
        return targetRow;
    }
    public int getTargetCol() {
        return targetCol;
    }

    // Generate randomly placed obstacles
    public void generate() {
        // Decide number of empty cells in the grid
        int emptyCount = (int) ((1 - obstacleDensity) * numRows * numCols);
       RandomWalker walker = new RandomWalker(numRows, numCols, emptyCount);
       walker.walk();
       for (int row = 0; row < numRows; row++) {
           for (int col = 0; col < numCols; col++) {
               // Mark obstacles
               if (walker.isObstacle(row, col)) {
                   setObstacle(row, col);
               }
           }
       }
    }

    public void setStart(int row, int col) {
        grid[row][col] = CellType.START;
        startRow = row;
        startCol = col;
    }

    public void setTarget(int row, int col) {
        grid[row][col] = CellType.TARGET;
        targetRow = row;
        targetCol = col;
    }

    private void setObstacle(int row, int col) {
        grid[row][col] = CellType.OBSTACLE;
    }

    public CellType getCell(int row, int col) {
        return grid[row][col];
    }

}
