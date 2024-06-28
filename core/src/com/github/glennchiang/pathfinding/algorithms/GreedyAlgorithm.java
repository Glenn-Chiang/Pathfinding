package com.github.glennchiang.pathfinding.algorithms;

import java.util.Set;

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
