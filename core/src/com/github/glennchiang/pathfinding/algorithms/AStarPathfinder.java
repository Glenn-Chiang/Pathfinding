package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.CellType;
import com.github.glennchiang.pathfinding.DistanceMetric;
import com.github.glennchiang.pathfinding.Grid;
import com.github.glennchiang.pathfinding.Node;

import java.util.*;

public class AStarPathfinder extends Pathfinder {
    private Node[][] nodeGrid;
    private final Set<Node> openNodes = new HashSet<>(); // Nodes that we are interested to explore
    private final Set<Node> closedNodes = new HashSet<>(); // Nodes that we are no longer interested in exploring

    // Called by the client to solve a new grid with different start/end cells or different obstacle positions
    public void init(Grid grid) {
        this.terrainGrid = grid;

        // Get start and target cells from terrain grid
        startRow = grid.getStartRow();
        startCol = grid.getStartCol();
        targetRow = grid.getTargetRow();
        targetCol = grid.getTargetCol();

        // Initialize a node at each cell in the grid
        this.nodeGrid = new Node[grid.numRows][grid.numCols];
        for (int row = 0; row < grid.numRows; row++) {
            for (int col = 0; col < grid.numCols; col++) {
                nodeGrid[row][col] = new Node(row, col);
            }

        }
        // Clear any state from previous solution
        openNodes.clear();
        closedNodes.clear();
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
            for (Node neighbor : getNeighbors(currentNode)) {
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

    // Heuristic distance between given node and target node
    private int calculateHCost(Node node) {
        return distanceMetric.getDistance(node.row, node.col, targetRow, targetCol);
    }

    private boolean isTarget(Node node) {
        return node.row == targetRow && node.col == targetCol;
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

    // Get neighboring nodes of given node
    // Distance metric affects which nodes are considered neighbors
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (int[] dir : distanceMetric.directions) {
            int row = node.row + dir[0];
            int col = node.col + dir[1];
            if (inBounds(row, col)) {
                neighbors.add(nodeGrid[row][col]);
            }
        }
        return neighbors;
    }
    private void saveState(List<Node> currentPath) {

    }

    @Override
    protected Node selectBestNode(Set<Node> nodes) {
        Optional<Node> bestNode = nodes.stream().min((nodeA, nodeB) -> {
            // Select node with lower f cost
            int fCostComparison = Integer.compare(nodeA.fCost(), nodeB.fCost());
            if (fCostComparison != 0) {
                return fCostComparison;
                // If f costs are equal, select node with lower g cost
            } else {
                return Integer.compare(nodeA.gCost, nodeB.gCost);
            }
        });
        return bestNode.orElse(null);
    }
}
