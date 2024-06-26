package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.*;

import java.util.*;

public abstract class PathfindingAlgorithm<TNode extends Node> implements Pathfinder {
    protected int targetRow;
    protected int targetCol;

    protected TNode[][] nodeGraph;
    private final Set<TNode> openNodes = new HashSet<>(); // Nodes that we are interested to explore
    private final Set<TNode> closedNodes = new HashSet<>(); // Nodes that we are no longer interested in exploring

    protected DistanceMetric distanceMetric = DistanceMetric.EUCLIDEAN; // Determines how distance is measured

    protected abstract TNode[][] createNodeGraph(int rows, int cols);
    protected abstract TNode createNode(int row, int col);

    public final AlgorithmSolution findPath(Grid grid) {
        // Clear any state from previous solution
        openNodes.clear();
        closedNodes.clear();

        // Initialize data for the given terrain grid
        int startRow = grid.getStartRow();
        int startCol = grid.getStartCol();
        targetRow = grid.getTargetRow();
        targetCol = grid.getTargetCol();

        // Initialize a node at each cell in the grid
        nodeGraph = createNodeGraph(grid.numRows, grid.numCols);
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                nodeGraph[row][col] = createNode(row, col);
            }
        }

        TNode targetNode = nodeGraph[targetRow][targetCol];
        TNode startNode = nodeGraph[startRow][startCol];
        openNode(startNode);

        // Records all steps taken to find path
        List<AlgorithmStep> steps = new ArrayList<>();

        while (!openNodes.isEmpty()) {
            TNode currentNode = selectBestNode(openNodes);
            closeNode(currentNode);

            // If we have reached the target, stop
            if (currentNode == targetNode ) {
                steps.add(saveStep(getPath(currentNode)));
                return new AlgorithmSolution(steps, true);
            }

            // Explore neighbors
            for (TNode neighbor : getNeighbors(currentNode, grid)) {
                // Skip closed nodes
                if (isClosed(neighbor)) {
                    continue;
                }
                exploreNeighbor(currentNode, neighbor);
            }

            // Update the solution with each step taken
            steps.add(saveStep(getPath(currentNode)));
        }

        // If we reach this point, that means we have failed to reach target node
        return new AlgorithmSolution(steps, false);
    }

    // Subclasses may perform additional operations on visited neighbors
    protected abstract void exploreNeighbor(TNode currentNode, TNode neighbor);

    // Select the best node from a set of nodes
    // Subclasses decide how to determine the best node
    protected abstract TNode selectBestNode(Set<TNode> nodes);

    // Check whether given position is within grid bounds and is not an obstacle
    private boolean isTraversable(int row, int col, Grid grid) {
        return row >= 0 && row < grid.numRows && col >= 0 && col < grid.numCols && grid.getCell(row, col) != CellType.OBSTACLE;
    }

    protected boolean isOpen(TNode node) {
        return openNodes.contains(node);
    }

    private boolean isClosed(TNode node) {
        return closedNodes.contains(node);
    }

    protected void openNode(TNode node) {
        openNodes.add(node);
    }

    private void closeNode(TNode node) {
        openNodes.remove(node);
        closedNodes.add(node);
    }

    // Get neighboring nodes of given node
    // Distance metric affects which nodes are considered neighbors
    private List<TNode> getNeighbors(Node node, Grid grid) {
        List<TNode> neighbors = new ArrayList<>();
        for (int[] dir : distanceMetric.directions) {
            int row = node.getRow() + dir[0];
            int col = node.getCol() + dir[1];
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
        if (node.getParent() == null) {
            return path;
        }
        return recursePath(node.getParent(), path);
    }

    private AlgorithmStep saveStep(List<Node> currentPath) {
        // Copy the node sets instead of passing by reference
        Set<Node> openNodes = new HashSet<>(this.openNodes);
        Set<Node> closedNodes = new HashSet<>(this.closedNodes);
        return new AlgorithmStep(currentPath, openNodes, closedNodes);
    }
}
