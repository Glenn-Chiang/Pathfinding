package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.Gdx;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmStep;

import java.util.Iterator;

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

    public AlgorithmVisualizer(GridDisplayer grid, MetricsDisplayer metricsDisplayer) {
        this.grid = grid;
        this.metricsDisplayer = metricsDisplayer;
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
        if (state == State.INACTIVE) return;

        // If paused but not stopped, continue rendering the current step
        grid.renderStep(currentStep);

        // If paused or reached the last step, stop iterating steps
        if (state == State.PAUSED || !stepIterator.hasNext()) {
            if (currentSolution.isValid) {
                grid.renderSolutionPath(currentSolution.finalPath());
            }
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
