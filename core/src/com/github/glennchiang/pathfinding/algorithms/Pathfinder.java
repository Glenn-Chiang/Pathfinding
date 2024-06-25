package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Pathfinder {
    protected int targetRow;
    protected int targetCol;

    protected Node[][] nodeGraph;
    protected final Set<Node> openNodes = new HashSet<>(); // Nodes that we are interested to explore
    protected final Set<Node> closedNodes = new HashSet<>(); // Nodes that we are no longer interested in exploring

    protected DistanceMetric distanceMetric = DistanceMetric.EUCLIDEAN; // Determines how distance is measured

    public final void findPath(Grid grid) {
        // Clear any state from previous solution
        openNodes.clear();
        closedNodes.clear();

        // Initialize data for the given terrain grid
        int startRow = grid.getStartRow();
        int startCol = grid.getStartCol();
        targetRow = grid.getTargetRow();
        targetCol = grid.getTargetCol();

        // Initialize a node at each cell in the grid
        this.nodeGraph = new Node[grid.numRows][grid.numCols];
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                nodeGraph[row][col] = new Node(row, col);
            }
        }

        Node targetNode = nodeGraph[targetRow][targetCol];
        Node startNode = nodeGraph[startRow][startCol];
        openNode(startNode);

        while (!openNodes.isEmpty()) {
            Node currentNode = selectBestNode(openNodes);
            closeNode(currentNode);

            // If we have reached the target, stop
            if (currentNode == targetNode ) {
                saveState(getPath(currentNode));
                return;
            }

            // Explore neighbors
            for (Node neighbor : getNeighbors(currentNode, grid)) {
                // Skip closed nodes
                if (isClosed(neighbor)) {
                    continue;
                }
                exploreNeighbor(currentNode, neighbor);
            }

            saveState(getPath(currentNode));
        }
    }

    // Subclasses may perform additional operations on visited neighbors
    protected abstract void exploreNeighbor(Node currentNode, Node neighbor);

    // Select the best node from a set of nodes
    // Subclasses decide how to determine the best node
    protected abstract Node selectBestNode(Set<Node> nodes);

    // Check whether given position is within grid bounds and is not an obstacle
    private boolean isTraversable(int row, int col, Grid grid) {
        return row >= 0 && row < grid.numRows && col >= 0 && col < grid.numCols && grid.getCell(row, col) != CellType.OBSTACLE;
    }

    protected boolean isOpen(Node node) {
        return openNodes.contains(node);
    }

    private boolean isClosed(Node node) {
        return closedNodes.contains(node);
    }

    protected void openNode(Node node) {
        openNodes.add(node);
    }

    private void closeNode(Node node) {
        openNodes.remove(node);
        closedNodes.add(node);
    }

    // Get neighboring nodes of given node
    // Distance metric affects which nodes are considered neighbors
    private List<Node> getNeighbors(Node node, Grid grid) {
        List<Node> neighbors = new ArrayList<>();
        for (int[] dir : distanceMetric.directions) {
            int row = node.row + dir[0];
            int col = node.col + dir[1];
            if (isTraversable(row, col, grid)) {
                neighbors.add(nodeGraph[row][col]);
            }
        }
        return neighbors;
    }

    // Retrace the path backwards from the given node to the start node
    private static List<Node> getPath(Node node) {
        return recursePath(node, new ArrayList<>());
    }
    private static List<Node> recursePath(Node node, List<Node> path) {
        path.add(0, node);
        if (node.parent == null) {
            return path;
        }
        return recursePath(node.parent, path);
    }

    private void saveState(List<Node> currentPath) {

    }
}
