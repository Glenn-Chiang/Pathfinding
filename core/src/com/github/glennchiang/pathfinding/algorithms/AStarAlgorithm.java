package com.github.glennchiang.pathfinding.algorithms;

import java.util.*;

public class AStarAlgorithm extends PathfindingAlgorithm<AStarNode> {
    @Override
    protected AStarNode createNode(int row, int col) {
        return new AStarNode(row, col);
    }

    @Override
    protected AStarNode[][] createNodeGraph(int rows, int cols) {
        return new AStarNode[rows][cols];
    }

    @Override
    protected AStarNode selectBestNode(Set<AStarNode> nodes) {
        Optional<AStarNode> bestNode = nodes.stream().min((nodeA, nodeB) -> {
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

    @Override
    protected void exploreNeighbor(AStarNode currentNode, AStarNode neighbor) {
    // Distance between neighbor and current node
        int distanceFromCurrent = distanceMetric.getDistance(currentNode.getRow(), currentNode.getCol(), neighbor.getRow(), neighbor.getCol());
        // gCost of neighbor is set to gCost of current node added to distance between neighbor and current node
        int gCost = currentNode.gCost + distanceFromCurrent;

        // If neighbor is not yet open for exploration or
        // if new g cost is less than its current g cost,
        // Set the f, g and h costs for the node and open it for exploration
        if (!isOpen(neighbor) || gCost < neighbor.gCost) {
            neighbor.gCost = gCost;
            neighbor.hCost = calculateHCost(neighbor);
            neighbor.setParent(currentNode);
            openNode(neighbor);
        }
    }

    // Heuristic distance between given node and target node
    private int calculateHCost(AStarNode node) {
        return distanceMetric.getDistance(node.row, node.col, targetRow, targetCol);
    }

}
