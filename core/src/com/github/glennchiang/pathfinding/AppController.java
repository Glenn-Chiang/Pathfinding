package com.github.glennchiang.pathfinding;

import com.github.glennchiang.pathfinding.grid.Grid;
import com.github.glennchiang.pathfinding.pathfindingalgorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.visualization.AlgorithmVisualizer;

// Top-level controller coordinating between AlgorithmManager and AlgorithmVisualizer
public class AppController implements AlgorithmVisualizer.Listener {
    public final Grid grid;
    public final AlgorithmManager algorithmManager;
    private final AlgorithmVisualizer visualizer;
    public AppController(Grid grid, AlgorithmManager algorithmManager, AlgorithmVisualizer visualizer) {
        this.grid = grid;
        this.algorithmManager = algorithmManager;
        this.algorithmManager.setSelectedAlgorithm(0);
        this.visualizer = visualizer;
        visualizer.registerListener(this);
    }

    public enum State {
        INACTIVE, // No visualization shown
        RUNNING,
        PAUSED,
        COMPLETED // Path visualization has been completed and is still being shown
    }

    private State state = State.INACTIVE;

    public State getState() {
        return state;
    }

    // Called when the run button is clicked. Different effect depending on current state.
    public void toggleRun() {
        switch (state) {
            case INACTIVE:
                state = State.RUNNING;
                newRun();
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

            case COMPLETED:
                visualizer.reset();
                state = State.RUNNING;
                newRun();
        }
    }

    // Run the select algorithm on the grid and visualize the solution
    private void newRun() {
        AlgorithmSolution solution = algorithmManager.runAlgorithm(grid);
        visualizer.setAlgorithmSolution(solution);
        visualizer.run();
    }

    public void reset() {
        state = State.INACTIVE;
        visualizer.reset();
    }

    // When visualizer has completed the visualization of the current algorithm solution,
    // change to inactive state
    @Override
    public void onVisualizationComplete() {
        state = State.COMPLETED;
    }
}
