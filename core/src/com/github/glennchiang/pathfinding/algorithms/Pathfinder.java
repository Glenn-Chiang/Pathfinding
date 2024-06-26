package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.Grid;

import java.util.Set;

public interface Pathfinder {
    AlgorithmSolution findPath(Grid grid);
}
