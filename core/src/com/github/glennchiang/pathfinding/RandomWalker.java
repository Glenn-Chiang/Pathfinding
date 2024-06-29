package com.github.glennchiang.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Generate a grid with randomly placed obstacles
// In the resultant grid, all empty cells will be connected to all other empty cells
public class RandomWalker {
    private final int gridRows;
    private final int gridCols;
    private final boolean[][] grid;

    // Possible directions to walk in
    private final int[][] directions = DistanceMetric.MANHATTAN.getDirections();

    // Random walker will iterate until this number of cells is cleared in the grid
    private final int requiredClearCount;

    private final List<int[]> clearedCells = new ArrayList<>();

    public RandomWalker(int gridRows, int gridCols, int requiredClearCount) {
        this.gridRows = gridRows;
        this.gridCols = gridCols;
        // In the grid, 'true' represents cleared cells while 'false' represents obstacle cells
        // Initially, all cells are obstacles
        grid = new boolean[gridRows][gridCols];
        this.requiredClearCount = requiredClearCount;
    }

    public void walk() {
        // Pick a random start position
        int currentRow = (int) (Math.random() * gridRows);
        int currentCol = (int) (Math.random() * gridCols);
        clear(currentRow, currentCol);

        int clearCount = 1;

        while (clearCount < requiredClearCount) {
            // Pick a random direction
            int[] direction = randomDirection();
            int nextRow = currentRow + direction[0];
            int nextCol = currentCol + direction[1];
            // If the resultant position is invalid, repeat until a valid position is found
            while (!isValidPosition(nextRow, nextCol)) {
                direction = randomDirection();
                nextRow = currentRow + direction[0];
                nextCol = currentCol + direction[1];
            }
            // Once valid cell is found, update current cell and clear that cell
            currentRow = nextRow;
            currentCol = nextCol;
            // If cell is not already cleared, clear it and update clear count
            if (isObstacle(currentRow, currentCol)) {
                clear(currentRow, currentCol);
                clearedCells.add(new int[]{currentRow, currentCol});
                clearCount++;
            }
        }
    }

    // Check whether given position is cleared
    public boolean isObstacle(int row, int col) {
        return !grid[row][col];
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < gridRows && col >= 0 && col < gridCols;
    }

    private int[] randomDirection() {
        int index = (int) (Math.random() * directions.length);
        return directions[index];
    }

    // Mark the given position as empty
    private void clear(int row, int col) {
        grid[row][col] = true;
    }

    public List<int[]> getClearedCells() {
        return new ArrayList<>(clearedCells);
    }
}
