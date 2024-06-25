package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.CellType;
import com.github.glennchiang.pathfinding.DistanceMetric;
import com.github.glennchiang.pathfinding.Grid;
import com.github.glennchiang.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Pathfinder {
    // The grid of interest. Contains positions of obstacles as well as start and target nodes
    protected Grid terrainGrid;
    protected int startRow;
    protected int startCol;
    protected int targetRow;
    protected int targetCol;

    protected DistanceMetric distanceMetric = DistanceMetric.EUCLIDEAN; // Determines how distance is measured

    public abstract void init(Grid grid);
    public abstract void solve();

    // Select the best node from a set of nodes. Subclasses decide how to determine the best node.
    protected abstract Node selectBestNode(Set<Node> nodes);

    // Retrace the path backwards from the given node to the start node
    protected static List<Node> getPath(Node node) {
        return recursePath(node, new ArrayList<>());
    }
    private static List<Node> recursePath(Node node, List<Node> path) {
        path.add(0, node);
        if (node.parent == null) {
            return path;
        }
        return recursePath(node.parent, path);
    }

    // Check whether the given position is within terrain grid bounds
    protected final boolean inBounds(int row, int col) {
        return row >= 0 && row < terrainGrid.numRows && col >= 0 && col < terrainGrid.numCols;
    }

    // Check whether given position is within grid bounds and is not an obstacle
    protected final boolean isTraversable(int row, int col) {
        return inBounds(row, col) && terrainGrid.getCell(row, col) != CellType.OBSTACLE;
    }


}
