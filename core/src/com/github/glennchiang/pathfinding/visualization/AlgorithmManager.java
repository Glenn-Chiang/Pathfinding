package com.github.glennchiang.pathfinding.visualization;

import com.github.glennchiang.pathfinding.Grid;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.algorithms.DistanceMetric;
import com.github.glennchiang.pathfinding.algorithms.PathfindingAlgorithm;

import java.util.Collections;
import java.util.List;

// Manages selection and running of pathfinding algorithms
public class AlgorithmManager {
    private final List<PathfindingAlgorithm> algorithms; // List of algorithms to select from
    private PathfindingAlgorithm selectedAlgorithm;

    private DistanceMetric distanceMetric = DistanceMetric.EUCLIDEAN;

    public AlgorithmManager(List<PathfindingAlgorithm> algorithms) {
        this.algorithms = algorithms;
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
