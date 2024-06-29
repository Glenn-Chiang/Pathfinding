package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.glennchiang.pathfinding.pathfindingalgorithms.AlgorithmStep;
import com.github.glennchiang.pathfinding.ui.WidgetFactory;

// Displays metrics for the algorithm being visualized
public class MetricsDisplayer {
    private final Table table;
    private final Label distanceLabel;
    private final Label visitedLabel;
    public MetricsDisplayer() {
        table = new Table();
        table.top().padTop(16);

        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        distanceLabel = widgetFactory.createLabel("Distance: 0");
        visitedLabel = widgetFactory.createLabel("Visited: 0");

        table.add(distanceLabel).expandX();
        table.add(visitedLabel).expandX();
    }

    public void addToLayout(Table rootTable, int width, int height) {
        rootTable.add(table).expand().bottom().width(width).height(height);
    }

    public void update(AlgorithmStep algorithmStep) {
        setDistance(algorithmStep.getDistance());
        setVisited(algorithmStep.getVisitedCount());
    }

    public void reset() {
        setDistance(0);
        setVisited(0);
    }

    private void setDistance(int distance) {
        distanceLabel.setText("Distance: " + distance);
    }

    private void setVisited(int visited) {
        visitedLabel.setText("Visited: " + visited);
    }
}
