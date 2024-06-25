package com.github.glennchiang.pathfinding.algorithms;

import com.github.glennchiang.pathfinding.Grid;
import com.github.glennchiang.pathfinding.Node;
import com.github.glennchiang.pathfinding.PathVisualizer;

import java.util.Set;

public class DijkstraPathfinder extends Pathfinder {
    @Override
    public void solve() {

    }

    @Override
    protected Node selectBestNode(Set<Node> nodes) {
        return null;
    }

    @Override
    public void acceptVisualizer(PathVisualizer visualizer) {
        visualizer.visualize(this);
    }
}
