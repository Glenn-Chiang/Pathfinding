package com.github.glennchiang.pathfinding.algorithms;

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

    // Get cost of current node, i.e. its distance from the start node
    public int getCost() {
        return currentPath.get(currentPath.size() - 1).distanceFromStart();
    }
}
