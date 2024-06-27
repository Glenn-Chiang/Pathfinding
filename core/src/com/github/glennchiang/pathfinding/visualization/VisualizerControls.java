package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class VisualizerControls {
    private Table table;
    private final AlgorithmVisualizer algorithmVisualizer;

    public VisualizerControls(AlgorithmVisualizer algorithmVisualizer) {
        table = new Table();
        table.setDebug(true);

        this.algorithmVisualizer = algorithmVisualizer;
        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        Button startButton = widgetFactory.createStartButton();
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                algorithmVisualizer.toggleStart();
            }
        });

        Button resetButton = widgetFactory.createResetButton();
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                algorithmVisualizer.reset();
            }
        });

        table.add(startButton);
        table.add(resetButton);
    }

    public void addToLayout(Table rootTable, int width, int height) {
        rootTable.add(table).expand().top().width(width).height(height);
    }
}
