package com.github.glennchiang.pathfinding.algorithms;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class AStarAlgorithm extends PathfindingAlgorithm {
    @Override
    protected int getCost(Node node) {
        return node.distanceFromStart + getDistanceToTarget(node);
    }

    @Override
    protected Node selectBestNode(Set<Node> nodes) {
        // If costs are equal, select node closer to target
        Optional<Node> bestNode = nodes.stream().min(Comparator.comparingInt(this::getCost)
                .thenComparingInt(this::getDistanceToTarget));
        return bestNode.orElse(null);
    }
}
