package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class VisualizerControls {
    private Table table;
    private final AlgorithmVisualizer algorithmVisualizer;

    public VisualizerControls(AlgorithmVisualizer algorithmVisualizer) {
        table = new Table();
        table.bottom().left().padBottom(4);
        table.setDebug(true);

        this.algorithmVisualizer = algorithmVisualizer;
        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        TextButton playButton = widgetFactory.createPlayButton();
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playButton.isChecked()) {
                    playButton.setText("Pause");
                } else {
                    playButton.setText("Play");
                }
                algorithmVisualizer.toggleStart();
            }
        });

        TextButton resetButton = widgetFactory.createResetButton();
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                algorithmVisualizer.reset();
            }
        });

        table.add(playButton).width(80).height(30).space(4);
        table.add(resetButton).width(80).height(30);
    }

    public void addToLayout(Table rootTable, int width, int height) {
        rootTable.add(table).expand().top().width(width).height(height);
    }
}
