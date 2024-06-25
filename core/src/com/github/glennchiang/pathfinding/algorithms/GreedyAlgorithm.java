package com.github.glennchiang.pathfinding.algorithms;

import java.util.Set;

public class GreedyAlgorithm extends PathfindingAlgorithm<GreedyNode> {
    @Override
    protected GreedyNode createNode(int row, int col) {
        return new GreedyNode(row, col);
    }

    @Override
    protected GreedyNode[][] createNodeGraph(int rows, int cols) {
        return new GreedyNode[rows][cols];
    }

    @Override
    protected GreedyNode selectBestNode(Set<GreedyNode> nodes) {
        return null;
    }

    @Override
    protected void exploreNeighbor(GreedyNode currentNode, GreedyNode neighbor) {

    }
}
