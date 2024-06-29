package com.github.glennchiang.pathfinding.pathfindingalgorithms;

public class GreedyAlgorithm extends PathfindingAlgorithm {
    @Override
    protected int getCost(Node node) {
        return getDistanceToTarget(node);
    }

    @Override
    public String getName() {
        return "Greedy";
    }
}
