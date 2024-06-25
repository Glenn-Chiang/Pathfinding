package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.*;

import java.util.*;

public class AStarPathfinder extends Pathfinder {
    @Override
    protected void exploreNeighbor(Node neighbor) {
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

    // Heuristic distance between given node and target node
    private int calculateHCost(Node node) {
        return distanceMetric.getDistance(node.row, node.col, targetRow, targetCol);
    }

}
