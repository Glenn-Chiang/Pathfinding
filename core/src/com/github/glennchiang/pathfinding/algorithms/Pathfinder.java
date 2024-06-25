package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.Grid;

import java.util.Set;

public interface Pathfinder {
    Set<? extends Node> getOpenNodes();
    void findPath(Grid grid);
}
