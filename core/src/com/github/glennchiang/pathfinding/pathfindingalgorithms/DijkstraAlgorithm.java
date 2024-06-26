package com.github.glennchiang.pathfinding.pathfindingalgorithms;

public class DijkstraAlgorithm extends PathfindingAlgorithm {
    @Override
    protected int getCost(Node node) {
        return node.distanceFromStart;
    }

    @Override
    public String getName() {
        return "Dijkstra";
    }
}
