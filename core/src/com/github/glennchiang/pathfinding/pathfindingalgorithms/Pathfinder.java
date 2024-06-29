package com.github.glennchiang.pathfinding.pathfindingalgorithms;

import com.github.glennchiang.pathfinding.grid.Grid;

public interface Pathfinder {
    AlgorithmSolution findPath(Grid grid);
}
