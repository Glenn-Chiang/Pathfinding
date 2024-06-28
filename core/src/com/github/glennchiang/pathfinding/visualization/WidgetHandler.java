package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.glennchiang.pathfinding.AppController;

public class WidgetHandler implements AlgorithmVisualizer.Listener {
    private Table table;
    private final TextButton runButton;

    public WidgetHandler(AppController appController, AlgorithmVisualizer visualizer) {
        visualizer.registerListener(this);

        table = new Table();
        table.bottom().left().padBottom(4);
        table.setDebug(true);

        WidgetFactory widgetFactory = WidgetFactory.getInstance();

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

        TextButton resetButton = widgetFactory.createResetButton();
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                appController.reset();
                runButton.setText("Run");
//                runButton.setChecked(true);
            }
        });

        table.add(runButton).width(80).height(30).space(4);
        table.add(resetButton).width(80).height(30);
    }

    @Override
    public void onVisualizationComplete() {
        setToRun();
    }

    public void setToRun() {
        runButton.setText("Run");
    }

    public void setToPause() {
        runButton.setText("Pause");
    }

    public void addToLayout(Table rootTable, int width, int height) {
        rootTable.add(table).expand().top().width(width).height(height);
    }
}
