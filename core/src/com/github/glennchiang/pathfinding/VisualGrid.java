package com.github.glennchiang.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class VisualGrid {
    private ShapeRenderer renderer;
    private final Grid grid;

    private final Rectangle background;
    private final Rectangle[][] cells;

    public VisualGrid(int x, int y, int width, int height, Grid grid, ShapeRenderer renderer) {
        this.grid = grid;
        this.renderer = renderer;

        background = new Rectangle();
        background.x = x;
        background.y = y;
        background.width = width;
        background.height = height;

        cells = new Rectangle[grid.numRows][grid.numCols];
        int cellWidth = width / grid.numCols;
        int cellHeight = height / grid.numRows;

        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                Rectangle cell = new Rectangle();
                cell.x = x + col * cellWidth;
                cell.y = y + height - (row + 1) * cellHeight;
                cell.width = cellWidth;
                cell.height = cellHeight;
                cells[row][col] = cell;
            }
        }
    }

    public void render() {
        // Draw background of grid
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(background.x, background.y, background.width, background.height);
        renderer.end();

        // Draw cells
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                Rectangle cell = cells[row][col];

                // Draw cell border
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.valueOf("#2980B9"));
                renderer.rect(cell.x, cell.y, cell.width, cell.height);
                renderer.end();

                // Mark obstacles with color
                if (grid.isObstacle(row, col)) {
                    renderer.begin(ShapeRenderer.ShapeType.Filled);
                    renderer.setColor(Color.valueOf("#21618C"));
                    renderer.rect(cell.x, cell.y, cell.width, cell.height);
                    renderer.end();
                }
            }
        }
    }
}
