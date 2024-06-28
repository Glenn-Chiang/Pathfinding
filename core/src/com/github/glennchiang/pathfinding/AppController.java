package com.github.glennchiang.pathfinding;

import com.github.glennchiang.pathfinding.algorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.visualization.AlgorithmManager;
import com.github.glennchiang.pathfinding.visualization.AlgorithmVisualizer;

// Top-level controller coordinating between AlgorithmManager and AlgorithmVisualizer
public class AppController {
    private final Grid grid;
    private final AlgorithmManager algorithmManager;
    private final AlgorithmVisualizer visualizer;
    public AppController(Grid grid, AlgorithmManager algorithmManager, AlgorithmVisualizer visualizer) {
        this.grid = grid;
        this.algorithmManager = algorithmManager;
        this.visualizer = visualizer;

    }

    public enum State {
        INACTIVE,
        RUNNING,
        PAUSED,
    }

    private State state = State.INACTIVE;

    public State getState() {
        return state;
    }

    // Called when the run button is clicked. Different effect depending on current state.
    public void toggleRun() {
        switch (state) {
            case INACTIVE:
                visualizer.reset();

                state = State.RUNNING;
                // Run the selected algorithm to find the path on the grid's current setup
                AlgorithmSolution solution = algorithmManager.runAlgorithm(grid);
                // Visualize the generated solution
                visualizer.setAlgorithmSolution(solution);
                visualizer.run();
                break;

            // If already running, pause
            case RUNNING:
                state = State.PAUSED;
                visualizer.pause();
                break;

            // If paused, continue running the current generated solution
            case PAUSED:
                state = State.RUNNING;
                visualizer.run();
                break;
        }
    }

    public void reset() {
        state = State.INACTIVE;
        visualizer.reset();
    }
}
