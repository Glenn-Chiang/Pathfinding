package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.CellType;
import com.github.glennchiang.pathfinding.DistanceMetric;
import com.github.glennchiang.pathfinding.Grid;
import com.github.glennchiang.pathfinding.Node;

import java.util.*;

// Solves a single pathfinding problem
// Controls the state required to solve this particular problem
public class AStarSolution {
    // Represents positions of obstacles as well as start and target cells
    private final Grid terrainGrid;
    private final int startRow;
    private final int startCol;
    private final int targetRow;
    private final int targetCol;

    private final Node[][] nodeGrid;
    private Set<Node> openNodes = new HashSet<>(); // Nodes that we are interested to explore
    private Set<Node> closedNodes = new HashSet<>(); // Nodes that we are no longer interested in exploring

    private DistanceMetric distanceMetric; // Determines how distance is measured

    public AStarSolution(Grid grid, DistanceMetric distanceMetric) {
        this.terrainGrid = grid;
        this.distanceMetric = distanceMetric;

        startRow = grid.getStartRow();
        startCol = grid.getStartCol();
        targetRow = grid.getTargetRow();
        targetCol = grid.getTargetCol();

        this.nodeGrid = new Node[grid.numRows][grid.numCols];
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                nodeGrid[row][col] = new Node(row, col);
            }
        }
    }

    // Heuristic distance between given node and target node
    private int calculateHCost(Node node) {
        return distanceMetric.getDistance(node.row, node.col, targetRow, targetCol);
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < terrainGrid.numRows && col >= 0 && col < terrainGrid.numCols;
    }

    // Check whether given position is within grid bounds and is not an obstacle
    private boolean isTraversable(int row, int col) {
        boolean inBounds = row >= 0 && row < terrainGrid.numRows && col >= 0 && col < terrainGrid.numCols;
        return inBounds && terrainGrid.getCell(row, col) != CellType.OBSTACLE;
    }

    private boolean isTarget(Node node) {
        return node.row == targetRow && node.col == targetCol;
    }

    // Get neighboring nodes of given node
    // Distance metric affects which nodes are considered neighbors
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (int[] dir: distanceMetric.directions) {
            int row = node.row + dir[0];
            int col = node.col + dir[1];
            if (inBounds(row, col)) {
                neighbors.add(nodeGrid[row][col]);
            }
        }
        return neighbors;
    }

    private boolean isOpen(Node node) {
        return openNodes.contains(node);
    }

    private boolean isClosed(Node node) {
        return closedNodes.contains(node);
    }

    private void openNode(Node node) {
        openNodes.add(node);
    }

    private void closeNode(Node node) {
        openNodes.remove(node);
        closedNodes.add(node);
    }

    public void solve() {
        Node startNode = nodeGrid[startRow][startCol];
        openNode(startNode);

        while (!openNodes.isEmpty()) {
            Node currentNode = selectBestNode(openNodes);
            closeNode(currentNode);

            if (isTarget(currentNode)) {
                saveState(getPath(currentNode));
                return;
            }

            // Explore neighbors
            for (Node neighbor: getNeighbors(currentNode)) {
                // Skip nodes that are obstacles or closed
                if (!isTraversable(neighbor.row, neighbor.col) || isClosed(neighbor)) {
                    continue;
                }

                // Distance between neighbor and current node
                int distanceFromCurrent = distanceMetric.getDistance(currentNode.row, currentNode.col, neighbor.row, neighbor.col);
                // gCost of neighbor is set to gCost of current node added to distance between neighbor and current node
                int gCost = currentNode.gCost + distanceFromCurrent;

                // If neighbor is not yet open for exploration or
                // if new g cost is less than its current g cost,
                // Set the f, g and h costs for the node and open it for exploration
                if (!isOpen(neighbor) || gCost < neighbor.gCost) {
                    neighbor.gCost = gCost;
                    neighbor.hCost = calculateHCost(neighbor);
                    neighbor.parent = currentNode;
                    openNode(neighbor);
                }
            }

            saveState(getPath(currentNode));
        }
    }

    private void saveState(List<Node> currentPath) {

    }

    // Retrace the path backwards from the given node to the start node
    private List<Node> getPath(Node node) {
        return recursePath(node, new ArrayList<>());
    }
    private List<Node> recursePath(Node node, List<Node> path) {
        path.add(0, node);
        if (node.parent == null) {
            return path;
        }
        return recursePath(node.parent, path);
    }

    private Node selectBestNode(Set<Node> nodes) {
        // TODO
        Optional<Node> bestNode = nodes.stream().min(Comparator.comparingInt(Node::fCost));
        return bestNode.orElse(null);
    }
}
