package com.github.glennchiang.pathfinding.grid;

import java.util.List;

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
    private float obstacleDensity = 0.4f;

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
               // Mark empty cells
               } else {
                   setEmpty(row, col);
               }
           }
       }

       // Randomly select start and target cells from empty cells
        List<int[]> emptyCells = walker.getClearedCells();
        int[] startCell = getRandomCell(emptyCells);
        setStart(startCell[0], startCell[1]);
        emptyCells.remove(startCell); // Ensure that target cell is different from start cell
        int[] targetCell = getRandomCell(emptyCells);
        setTarget(targetCell[0], targetCell[1]);
    }

    private int[] getRandomCell(List<int[]> cells) {
        return cells.get((int) (Math.random() * cells.size()));
    }

    // Change the current start cell to an empty cell
    // Set the given position as the new start cell, only if it is empty
    public void setStart(int row, int col) {
        if (getCell(row, col) != CellType.EMPTY) return;

        setEmpty(startRow, startCol);
        grid[row][col] = CellType.START;
        startRow = row;
        startCol = col;
    }

    public void setTarget(int row, int col) {
        if (getCell(row, col) != CellType.EMPTY) return;

        setEmpty(targetRow, targetCol);
        grid[row][col] = CellType.TARGET;
        targetRow = row;
        targetCol = col;
    }

    private void setEmpty(int row, int col) {
        grid[row][col] = CellType.EMPTY;
    }

    private void setObstacle(int row, int col) {
        grid[row][col] = CellType.OBSTACLE;
    }

    public CellType getCell(int row, int col) {
        return grid[row][col];
    }

}
