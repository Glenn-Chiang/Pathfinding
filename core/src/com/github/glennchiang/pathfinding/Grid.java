package com.github.glennchiang.pathfinding;

// The grid on which the pathfinding algorithms will act
// Stores obstacle positions
public class Grid {
    public final int numRows;
    public final int numCols;
    private CellType[][] grid;

    public Grid(int num_rows, int num_cols) {
        this.numRows = num_rows;
        this.numCols = num_cols;
        this.grid = new CellType[num_rows][num_cols];
        for (int row = 0; row < numRows; row ++) {
            for (int col = 0; col < numCols; col++) {
                grid[row][col] = CellType.EMPTY;
            }
        }
    }

    public void setStart(int row, int col) {
        grid[row][col] = CellType.START;
    }

    public void setTarget(int row, int col) {
        grid[row][col] = CellType.TARGET;
    }

    public void setObstacle(int row, int col) {
        grid[row][col] = CellType.OBSTACLE;
    }

    public CellType getCell(int row, int col) {
        return grid[row][col];
    }

    public void setObstacles() {
        // TODO: Randomize obstacle positions?
        setObstacle(5, 5);
        setObstacle(5, 6);
        setObstacle(4, 6);

    }

}
