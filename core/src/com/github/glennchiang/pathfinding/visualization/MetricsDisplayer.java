package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmSolution;
import com.github.glennchiang.pathfinding.algorithms.AlgorithmStep;

// Displays metrics for the algorithm being visualized
public class MetricsDisplayer {
    private final Table table;
    private final Label distanceLabel;
    private final Label visitedLabel;
    public MetricsDisplayer() {
        table = new Table();
//        table.setDebug(true);
        table.top().padTop(16);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont(); // TODO: use nicer font

        style.fontColor = Color.WHITE;
        distanceLabel = new Label("Distance: 0", style);
        visitedLabel = new Label("Visited nodes: 0", style);

        table.add(distanceLabel).expandX();
        table.add(visitedLabel).expandX();
    }

    public void addToLayout(Table rootTable) {
        rootTable.add(table).expand().height(200).bottom().width(640);
    }

    public void render(AlgorithmStep algorithmStep) {
        
    }

    private void setDistance(int distance) {
        distanceLabel.setText("Distance: " + distance);
    }

    private void setVisited(int visited) {
        visitedLabel.setText("Visited: " + visited);
    }
}
