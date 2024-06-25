package com.github.glennchiang.pathfinding.algorithms;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmSolution {
    public final List<AlgorithmStep> steps = new ArrayList<>();

    public void addStep(AlgorithmStep step) {
        steps.add(step);
    }

    public List<Node> finalPath() {
        return steps.get(steps.size() - 1).currentPath;
    }
}
