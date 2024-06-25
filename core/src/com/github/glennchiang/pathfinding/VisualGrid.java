package com.github.glennchiang.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class VisualGrid {
    private final ShapeRenderer renderer;
    private final Grid grid;
    private final Rectangle[][] cells;

    public VisualGrid(int x, int y, int width, int height, Grid grid, ShapeRenderer renderer) {
        this.grid = grid;
        this.renderer = renderer;

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
        // Draw cells
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                Rectangle cell = cells[row][col];

                // Draw cell border
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.valueOf("#2980B9"));
                renderer.rect(cell.x, cell.y, cell.width, cell.height);
                renderer.end();

                CellType cellType = grid.getCell(row, col);
                Color color;
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                switch (cellType) {
                    case START:
                        color = Color.valueOf("#58D68D");
                        break;
                    case TARGET:
                        color = Color.valueOf("#E74C3C");
                        break;
                    case OBSTACLE:
                        color = Color.valueOf("#21618C");
                        break;
                    case EMPTY:
                    default:
                        color = Color.WHITE;
                        break;
                }
                renderer.setColor(color);
                renderer.rect(cell.x, cell.y, cell.width - 1, cell.height - 1);
                renderer.end();
            }
        }
    }
}
