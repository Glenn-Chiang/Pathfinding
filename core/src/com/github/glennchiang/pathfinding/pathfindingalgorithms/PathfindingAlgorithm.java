package com.github.glennchiang.pathfinding.pathfindingalgorithms;

import com.github.glennchiang.pathfinding.*;
import com.github.glennchiang.pathfinding.grid.CellType;
import com.github.glennchiang.pathfinding.grid.Grid;

import java.util.*;

public abstract class PathfindingAlgorithm implements Pathfinder {
    protected int targetRow;
    protected int targetCol;

    protected Node[][] nodeGraph;
    private final Set<Node> openNodes = new HashSet<>(); // Nodes that we are interested to explore
    private final Set<Node> closedNodes = new HashSet<>(); // Nodes that we are no longer interested in exploring

    public DistanceMetric distanceMetric = DistanceMetric.EUCLIDEAN; // Determines how distance is measured

    // Clear any previous solution state
    public void reset() {
        openNodes.clear();
        closedNodes.clear();
        nodeGraph = null;
        targetRow = 0;
        targetCol = 0;
    }

    public final AlgorithmSolution findPath(Grid grid) {
        reset();

        // Initialize data for the given terrain grid
        int startRow = grid.getStartRow();
        int startCol = grid.getStartCol();
        targetRow = grid.getTargetRow();
        targetCol = grid.getTargetCol();

        // Initialize a node at each cell in the grid
        nodeGraph = new Node[grid.numRows][grid.numCols];
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                nodeGraph[row][col] = new Node(row, col);
            }
        }

        Node targetNode = nodeGraph[targetRow][targetCol];
        Node startNode = nodeGraph[startRow][startCol];
        openNode(startNode);

        // Records all steps taken to find path
        List<AlgorithmStep> steps = new ArrayList<>();

        while (!openNodes.isEmpty()) {
            Node currentNode = selectBestNode(openNodes);
            closeNode(currentNode);

            // If we have reached the target, stop
            if (currentNode == targetNode ) {
                steps.add(saveStep(getPath(currentNode)));
                return new AlgorithmSolution(steps, true);
            }

            // Explore neighbors
            for (Node neighbor : getNeighbors(currentNode, grid)) {
                // Skip closed nodes
                if (isClosed(neighbor)) {
                    continue;
                }

                int distanceFromCurrent = distanceMetric.getDistance(currentNode.row, currentNode.col, neighbor.row, neighbor.col);
                // Distance from start node to current node to neighbor
                int currentDistance = currentNode.distanceFromStart + distanceFromCurrent;

                if (!isOpen(neighbor) || currentDistance < neighbor.distanceFromStart) {
                    // Update neighbor's distance from start node if the new path is shorter
                    neighbor.distanceFromStart = currentDistance;
                    neighbor.parent = currentNode;
                    openNode(neighbor);
                }
            }

            // Update the solution with each step taken
            steps.add(saveStep(getPath(currentNode)));
        }

        // If we reach this point, that means we have failed to reach target node
        return new AlgorithmSolution(steps, false);
    }

    // Heuristic distance between given node and target node
    protected final int getDistanceToTarget(Node node) {
        return distanceMetric.getDistance(node.row, node.col, targetRow, targetCol);
    }

    // Subclass algorithms decide how to determine the cost of a node
    protected abstract int getCost(Node node);

    // Select node with the lowest cost
    protected Node selectBestNode(Set<Node> nodes) {
        Optional<Node> bestNode = nodes.stream().min(Comparator.comparingInt(this::getCost));
        return bestNode.orElse(null);
    }

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

    private AlgorithmStep saveStep(List<Node> currentPath) {
        // Copy the node sets instead of passing by reference
        Set<Node> openNodes = new HashSet<>(this.openNodes);
        Set<Node> closedNodes = new HashSet<>(this.closedNodes);
        return new AlgorithmStep(currentPath, openNodes, closedNodes);
    }

    // Return user-friendly name of algorithm
    public abstract String getName();
}
