package com.github.glennchiang.pathfinding.algorithms;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmSolution {
    public final boolean isValid; // Whether the solution actually reaches the target node
    public final List<AlgorithmStep> steps;

    public AlgorithmSolution(List<AlgorithmStep> steps, boolean isValid) {
        this.steps = steps;
        this.isValid = isValid;
    }

    public List<Node> finalPath() {
        return steps.get(steps.size() - 1).currentPath;
    }

}
