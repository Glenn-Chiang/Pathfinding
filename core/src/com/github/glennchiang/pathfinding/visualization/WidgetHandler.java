package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.glennchiang.pathfinding.AppController;

public class WidgetHandler {
    private Table table;

    public WidgetHandler(AppController appController) {
        table = new Table();
        table.bottom().left().padBottom(4);
        table.setDebug(true);

        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        TextButton runButton = widgetFactory.createRunButton();
        runButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (appController.getState() != AppController.State.RUNNING) {
                    // Start running and change to pause button
                    runButton.setText("Pause");
                } else {
                    // Pause and change to run button
                    runButton.setText("Run");
                }
                appController.toggleRun();
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

    public void addToLayout(Table rootTable, int width, int height) {
        rootTable.add(table).expand().top().width(width).height(height);
    }
}
