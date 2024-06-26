package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.glennchiang.pathfinding.grid.CellType;
import com.github.glennchiang.pathfinding.grid.Grid;
import com.github.glennchiang.pathfinding.pathfindingalgorithms.AlgorithmStep;
import com.github.glennchiang.pathfinding.pathfindingalgorithms.Node;

import java.awt.*;

public class GridDisplayer {
    private final ShapeRenderer renderer;
    private final Grid grid;
    private final Rectangle[][] cells;

    public GridDisplayer(int x, int y, int width, int height, Grid grid, ShapeRenderer renderer) {
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

    public void handleStartTouch(Vector2 touchPos) {
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                Rectangle cell = cells[row][col];
                if (cell.contains(touchPos.x, touchPos.y)) {
                    grid.setStart(row, col);
                }
            }
        }
    }

    public void handleTargetTouch(Vector2 touchPos) {
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                Rectangle cell = cells[row][col];
                if (cell.contains(touchPos.x, touchPos.y)) {
                    grid.setTarget(row, col);
                }
            }
        }
    }

    // Called every frame
    // Renders the grid, including the start and target cells and the obstacles
    // Is not affected by the algorithm visualizer
    public void renderGrid() {
        // Draw cells
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                Rectangle cell = cells[row][col];

                CellType cellType = grid.getCell(row, col);

                // Fill cell with color
                Color color;
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                switch (cellType) {
                    case START:
                        color = Color.GREEN;
                        break;
                    case TARGET:
                        color = Color.RED;
                        break;
                    case OBSTACLE:
                        color = Color.valueOf("#1B4F72");
                        break;
                    case EMPTY:
                    default:
                        color = Color.WHITE;
                        break;
                }
                renderer.setColor(color);
                renderer.rect(cell.x, cell.y, cell.width, cell.height);
                renderer.end();

                drawCellBorder(cell);
            }
        }
    }

    private void drawCellBorder(Rectangle cell) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.valueOf("#2980B9"));
        renderer.rect(cell.x, cell.y, cell.width, cell.height);
        renderer.end();
    }

    // Renders the given algorithm step by marking out the current path as well as open and closed nodes
    // Should only be called by AlgorithmVisualizer
    public void renderStep(AlgorithmStep algorithmStep) {
        // Mark open nodes
        for (Node node: algorithmStep.openNodes) {
            markNode(node, Color.valueOf("#4FC3F7"));
        }

        // Mark closed nodes
        for (Node node: algorithmStep.closedNodes) {
            markNode(node, Color.valueOf("#03A9F4"));
        }

        // Mark current path
        for (Node node: algorithmStep.currentPath) {
            markNode(node, Color.GOLD);
        }
    }
    private void markNode(Node node, Color color) {
        CellType cellType = grid.getCell(node.row, node.col);
        if (cellType != CellType.EMPTY) {
            return;
        }

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        Rectangle cell = cells[node.row][node.col];
        renderer.rect(cell.x, cell.y, cell.width, cell.height);
        renderer.end();

        drawCellBorder(cell);
    }

}
