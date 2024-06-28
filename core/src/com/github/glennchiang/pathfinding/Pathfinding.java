package com.github.glennchiang.pathfinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.glennchiang.pathfinding.algorithms.*;
import com.github.glennchiang.pathfinding.visualization.*;

import java.util.Arrays;
import java.util.List;

public class Pathfinding extends ApplicationAdapter {
    public final static int SCREEN_WIDTH = 800;
    public final static int SCREEN_HEIGHT = 800;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;

    private Grid grid;
    private GridDisplayer visualGrid;
    private AlgorithmVisualizer visualizer;
    private MetricsDisplayer metricsDisplayer;
    private WidgetHandler visualizerControls;

    @Override
    public void create() {
        // Boilerplate setup
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        Table rootTable = setUpStage();

        // Initialize grid for pathfinder to act on
        grid = new Grid(20, 32);
        // Visual representation of grid and algorithm
        int gridWidth = 640;
        int gridHeight = 400;
        visualGrid = new GridDisplayer((SCREEN_WIDTH - gridWidth) / 2, (SCREEN_HEIGHT - gridHeight) / 2 - 100,
                gridWidth, gridHeight, grid, shapeRenderer);

        metricsDisplayer = new MetricsDisplayer();
        visualizer = new AlgorithmVisualizer(visualGrid, metricsDisplayer);

        // Initialize start and target cells and obstacle positions
        setUpGrid();

        // Initialize list of available algorithms to select from
        PathfindingAlgorithm aStar = new AStarAlgorithm();
        PathfindingAlgorithm greedy = new GreedyAlgorithm();
        PathfindingAlgorithm dijkstra = new DijkstraAlgorithm();
        List<PathfindingAlgorithm> algorithms = Arrays.asList(aStar, greedy, dijkstra);
        AlgorithmManager algorithmManager = new AlgorithmManager(algorithms);

        AppController appController = new AppController(grid, algorithmManager, visualizer);

        visualizerControls = new WidgetHandler(appController);
        visualizerControls.addToLayout(rootTable, gridWidth, 300);
        rootTable.row();
        metricsDisplayer.addToLayout(rootTable, gridWidth, 100);
    }

    private Table setUpStage() {
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Show layout lines for debugging
//        rootTable.setDebug(true);

        return rootTable;
    }

    private void setUpGrid() {
        grid.setStart(0, 0);
        grid.setTarget(grid.numRows - 1, grid.numCols - 1);
        grid.setObstacles();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

		visualGrid.renderGrid();
        visualizer.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose();
    }
}
