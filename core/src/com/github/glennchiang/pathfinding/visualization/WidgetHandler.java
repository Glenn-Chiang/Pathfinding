package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.github.glennchiang.pathfinding.AlgorithmManager;
import com.github.glennchiang.pathfinding.AppController;
import com.github.glennchiang.pathfinding.algorithms.PathfindingAlgorithm;

public class WidgetHandler implements AlgorithmVisualizer.Listener {
    private Table table;

    private final WidgetFactory widgetFactory;

    private final TextButton runButton;

    public WidgetHandler(AppController appController, AlgorithmVisualizer visualizer) {
        visualizer.registerListener(this);

        table = new Table();
        table.bottom().left().padBottom(4);
        table.setDebug(true);

        widgetFactory = WidgetFactory.getInstance();

        AlgorithmManager algorithmManager = appController.algorithmManager;

        // Configure algorithm dropdown
        Array<String> algorithmNames = new Array<>();
        for (PathfindingAlgorithm algorithm: algorithmManager.getAlgorithms()) {
            algorithmNames.add(algorithm.getName());
        }

        Label algorithmLabel = widgetFactory.createLabel("Algorithm:");
        SelectBox<String> selectBox = WidgetFactory.getInstance().createSelectBox(algorithmNames);
        // Set selected algorithm to the default algorithm of the algorithm manager
        selectBox.setSelected(algorithmManager.getSelectedAlgorithm().getName());
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                algorithmManager.setSelectedAlgorithm(selectBox.getSelectedIndex());
            }
        });

        table.add(algorithmLabel);
        table.add(selectBox);
        table.row();

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
                runButton.setChecked(true);
            }
        });

        table.add(runButton).width(80).height(30).space(4);
        table.add(resetButton).width(80).height(30);
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
