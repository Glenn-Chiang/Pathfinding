package com.github.glennchiang.pathfinding.algorithms;

import java.util.List;
import java.util.Set;

public class AlgorithmStep {
    public final List<Node> currentPath;
    public final Set<? extends Node> openNodes;
    public final Set<? extends Node> closedNodes;
    public AlgorithmStep(List<Node> currentPath, Set<? extends Node> openNodes, Set<? extends Node> closedNodes) {
        this.currentPath = currentPath;
        this.openNodes = openNodes;
        this.closedNodes = closedNodes;
    }
}
