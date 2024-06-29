package com.github.glennchiang.pathfinding;

import com.github.glennchiang.pathfinding.grid.Grid;
import com.github.glennchiang.pathfinding.pathfindingalgorithms.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Manages selection and running of pathfinding algorithms
public class AlgorithmManager {
    private final List<PathfindingAlgorithm> algorithms; // List of algorithms to select from
    private PathfindingAlgorithm selectedAlgorithm;

    private DistanceMetric distanceMetric = DistanceMetric.EUCLIDEAN;

    public AlgorithmManager() {
        // Initialize list of available algorithms to select from
        PathfindingAlgorithm aStar = new AStarAlgorithm();
        PathfindingAlgorithm greedy = new GreedyAlgorithm();
        PathfindingAlgorithm dijkstra = new DijkstraAlgorithm();
        this.algorithms = Arrays.asList(aStar, greedy, dijkstra);
    }

    public AlgorithmSolution runAlgorithm(Grid grid) {
        return selectedAlgorithm.findPath(grid);
    }

    // Returns an unmodifiable view of the list of algorithms
    public List<PathfindingAlgorithm> getAlgorithms() {
        return Collections.unmodifiableList(algorithms);
    }

    public PathfindingAlgorithm getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    public void setSelectedAlgorithm(int index) {
        selectedAlgorithm = algorithms.get(index);
    }

    public DistanceMetric getDistanceMetric() {
        return distanceMetric;
    }

    public void setDistanceMetric(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
        for (PathfindingAlgorithm algorithm: algorithms) {
            algorithm.distanceMetric = distanceMetric;
        }
    }
}
