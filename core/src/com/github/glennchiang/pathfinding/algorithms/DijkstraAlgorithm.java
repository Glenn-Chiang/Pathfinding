package com.github.glennchiang.pathfinding.algorithms;

public class DijkstraAlgorithm extends PathfindingAlgorithm {
    @Override
    protected int getCost(Node node) {
        return node.distanceFromStart;
    }
}
