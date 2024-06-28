package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.github.glennchiang.pathfinding.AlgorithmManager;
import com.github.glennchiang.pathfinding.AppController;
import com.github.glennchiang.pathfinding.algorithms.DistanceMetric;
import com.github.glennchiang.pathfinding.algorithms.PathfindingAlgorithm;

public class WidgetHandler implements AlgorithmVisualizer.Listener {
    private Table table;

    private final WidgetFactory widgetFactory;

    private final TextButton runButton;

    public WidgetHandler(AppController appController, AlgorithmVisualizer visualizer) {
        // Subscribe to algorithm visualizer to be notified when visualizations are completed
        visualizer.registerListener(this);

        table = new Table();
        table.bottom().left().padBottom(8);
//        table.setDebug(true);

        widgetFactory = WidgetFactory.getInstance();

        // Configure algorithm dropdown
        AlgorithmManager algorithmManager = appController.algorithmManager;
        Array<String> algorithmNames = new Array<>();
        for (PathfindingAlgorithm algorithm: algorithmManager.getAlgorithms()) {
            algorithmNames.add(algorithm.getName());
        }

        Label algorithmLabel = widgetFactory.createLabel("Algorithm:");
        SelectBox<String> algorithmSelectBox = widgetFactory.createSelectBox(algorithmNames);

        // Set selected algorithm to the default algorithm of the algorithm manager
        algorithmSelectBox.setSelected(algorithmManager.getSelectedAlgorithm().getName());
        algorithmSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                algorithmManager.setSelectedAlgorithm(algorithmSelectBox.getSelectedIndex());
            }
        });

        // Configure distance metric dropdown
        Label distanceMetricLabel = widgetFactory.createLabel("Distance metric:");
        Array<String> distanceMetricNames = new Array<>();
        for (DistanceMetric metric: DistanceMetric.values()) {
            distanceMetricNames.add(metric.name());
        }
        SelectBox<String> distanceMetricSelectBox = widgetFactory.createSelectBox(distanceMetricNames);
        distanceMetricSelectBox.setSelected(algorithmManager.getDistanceMetric().name());
        distanceMetricSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                algorithmManager.setDistanceMetric(DistanceMetric.valueOf(distanceMetricSelectBox.getSelected()));
            }
        });

        // Configure run/pause button
        runButton = widgetFactory.createRunButton();
        runButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                appController.toggleRun();
                if (appController.getState() == AppController.State.RUNNING) {
                    setToPause();
                } else {
                    setToRun();
                }
            }
        });

        // Configure reset button
        TextButton resetButton = widgetFactory.createResetButton();
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                appController.reset();
                setToRun();
            }
        });

        // Add algorithm dropdown
        table.add(algorithmLabel).spaceBottom(8).width(80).height(30).fill();
        table.add(algorithmSelectBox).spaceBottom(8).width(80).height(30).fill();

        // Add DistanceMetric dropdown
        table.add(distanceMetricLabel);
        table.add(distanceMetricSelectBox);

        table.row();

        // Add run and reset buttons
        table.add(runButton).width(80).height(30).space(4);
        table.add(resetButton).width(80).height(30).left();
    }

    @Override
    public void onVisualizationComplete() {
        setToRun();
    }

    private void setToRun() {
        runButton.setText("Run");
        widgetFactory.setButtonStyle(runButton, "run");
    }

    private void setToPause() {
        runButton.setText("Pause");
        widgetFactory.setButtonStyle(runButton, "pause");
    }

    public void addToLayout(Table rootTable, int width, int height) {
        rootTable.add(table).expand().top().width(width).height(height);
    }
}
