package com.github.glennchiang.pathfinding.algorithms;

import java.util.Set;

public class DijkstraPathfinder extends PathfindingAlgorithm<DijkstraNode> {
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
        return null;
    }

    @Override
    protected void exploreNeighbor(DijkstraNode currentNode, DijkstraNode neighbor) {

    }
}
