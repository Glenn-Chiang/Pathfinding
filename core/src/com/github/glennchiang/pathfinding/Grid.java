package com.github.glennchiang.pathfinding;

// The grid on which the pathfinding algorithms will act
// Stores obstacle positions
public class Grid {
    public final int numRows;
    public final int numCols;
    private boolean[][] grid;

    public Grid(int num_rows, int num_cols) {
        this.numRows = num_rows;
        this.numCols = num_cols;
        this.grid = new boolean[num_rows][num_cols];
    }

    public void setObstacles() {
        // TODO: Randomize obstacle positions?
        grid[5][5] = true;
    }

    public boolean isObstacle(int row, int col) {
        return grid[row][col];
    }
}
