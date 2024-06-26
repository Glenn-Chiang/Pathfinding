package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.Gdx;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmStep;

import java.util.Iterator;

public class AlgorithmVisualizer {
    private float stepInterval = 0.05f; // Time interval, in seconds, between each step displayed
    private float stepTimer = stepInterval;
    private final VisualGrid grid;
    private AlgorithmSolution currentSolution;
    private Iterator<AlgorithmStep> stepIterator;
  private AlgorithmStep currentStep;

    private boolean isActive = false;

    public AlgorithmVisualizer(VisualGrid grid) {
        this.grid = grid;
    }

    public void visualize(AlgorithmSolution solution) {
        isActive = true;
        currentSolution = solution;
        stepIterator = currentSolution.steps.iterator();
        currentStep = stepIterator.next();
    }

    // Called every frame
    public void update() {
        if (!isActive) return;

        grid.renderStep(currentStep);

        // If we have reached the last step, stop iterating steps
        if (!stepIterator.hasNext()) {
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

            // Reset timer interval
            stepTimer = stepInterval;
        }
    }
}
