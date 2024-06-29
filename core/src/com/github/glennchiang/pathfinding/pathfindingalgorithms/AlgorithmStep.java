package com.github.glennchiang.pathfinding.pathfindingalgorithms;

import java.util.List;
import java.util.Set;

public class AlgorithmStep {
    public final List<Node> currentPath;
    public final Set<Node> openNodes;
    public final Set<Node> closedNodes;
    public AlgorithmStep(List<Node> currentPath, Set<Node> openNodes, Set<Node> closedNodes) {
        this.currentPath = currentPath;
        this.openNodes = openNodes;
        this.closedNodes = closedNodes;
    }

    // Get distance from start node to current node
    public int getDistance() {
        return currentPath.get(currentPath.size() - 1).distanceFromStart();
    }

    // How many nodes visited in total
    public int getVisitedCount() {
        return openNodes.size() + closedNodes.size();
    }
}
