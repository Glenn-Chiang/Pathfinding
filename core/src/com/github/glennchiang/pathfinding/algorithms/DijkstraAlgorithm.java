package com.github.glennchiang.pathfinding.algorithms;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class DijkstraAlgorithm extends PathfindingAlgorithm<DijkstraNode> {
    @Override
    protected DijkstraNode createNode(int row, int col) {
        return new DijkstraNode(row, col);
    }
    @Override
    protected DijkstraNode[][] createNodeGraph(int rows, int cols) {
        return new DijkstraNode[rows][cols];
    }

    @Override
    protected DijkstraNode selectBestNode(Set<DijkstraNode> nodes) {
        // Select node with the lowest cost, i.e. the shortest distance from start node
        Optional<DijkstraNode> bestNode = nodes.stream().min(Comparator.comparingInt(node -> node.cost));
        return bestNode.orElse(null);
    }

    @Override
    protected void exploreNeighbor(DijkstraNode currentNode, DijkstraNode neighbor) {
        // Distance between neighbor and current node
        int distanceFromCurrent = distanceMetric.getDistance(currentNode.getRow(), currentNode.getCol(), neighbor.getRow(), neighbor.getCol());
        // gCost of neighbor is set to gCost of current node added to distance between neighbor and current node
        int updatedCost = currentNode.cost + distanceFromCurrent;

        // If neighbor is not yet open for exploration or
        // if new g cost is less than its current g cost,
        // Set the f, g and h costs for the node and open it for exploration
        if (!isOpen(neighbor) || updatedCost < neighbor.cost) {
            neighbor.cost = updatedCost;
            neighbor.setParent(currentNode);
            openNode(neighbor);
        }
    }
}
