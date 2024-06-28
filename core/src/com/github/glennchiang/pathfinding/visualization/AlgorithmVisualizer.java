package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.Gdx;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmStep;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

public class AlgorithmVisualizer {
    private float stepInterval = 0.05f; // Time interval, in seconds, between each step displayed
    private float stepTimer = stepInterval;
    private AlgorithmSolution currentSolution;
    private Iterator<AlgorithmStep> stepIterator;
    private AlgorithmStep currentStep;
    private final GridDisplayer grid;
    private final MetricsDisplayer metricsDisplayer;

    private enum State {
        INACTIVE, RUNNING, PAUSED,
    }
    private State state = State.INACTIVE;

    public interface Listener extends EventListener {
        void onCompleteVisualization();
    }

    private final List<Listener> listeners = new ArrayList<>();

    public AlgorithmVisualizer(GridDisplayer grid, MetricsDisplayer metricsDisplayer) {
        this.grid = grid;
        this.metricsDisplayer = metricsDisplayer;
    }
    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    // Notify all listeners that the visualizer has completed
    public void publishComplete() {
        for (Listener listener: listeners) {
            listener.onCompleteVisualization();
        }
    }

    public void setAlgorithmSolution(AlgorithmSolution solution) {
        currentSolution = solution;
        stepIterator = currentSolution.steps.iterator();
        currentStep = stepIterator.next();
    }

    public void run() {
        state = State.RUNNING;
    }

    public void pause() {
        state = State.PAUSED;
    }

    public void reset() {
        state = State.INACTIVE;
        currentSolution = null;
        stepIterator = null;
        currentStep = null;
        metricsDisplayer.reset();
    }

    // Called every frame
    public void update() {
        if (currentStep != null) {
            grid.renderStep(currentStep);
        }

        if (state != State.RUNNING) {
            return;
        }

        // Completed visualizing solution
        if (!stepIterator.hasNext()) {
            // Notify any listeners that visualization is complete
            publishComplete();
            // If a valid path is found, highlight the path
            if (currentSolution.isValid) {
                grid.renderSolutionPath(currentSolution.finalPath());
            }
            state = State.INACTIVE;
            return;
        }

        // Update timer each frame
        stepTimer -= Gdx.graphics.getDeltaTime();

        // At every interval, iterate to the next step
        if (stepTimer <= 0) {
            currentStep = stepIterator.next();
            metricsDisplayer.update(currentStep);

            // Reset timer interval
            stepTimer = stepInterval;
        }
    }
}
