package com.github.glennchiang.pathfinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.glennchiang.pathfinding.grid.Grid;
import com.github.glennchiang.pathfinding.ui.InputManager;
import com.github.glennchiang.pathfinding.ui.WidgetManager;
import com.github.glennchiang.pathfinding.visualization.*;

public class Pathfinding extends ApplicationAdapter {
    public final static int SCREEN_WIDTH = 800;
    public final static int SCREEN_HEIGHT = 600;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;

    private Grid grid;
    private GridDisplayer gridDisplayer;
    private AlgorithmVisualizer visualizer;
    private MetricsDisplayer metricsDisplayer;
    private WidgetManager widgetHandler;

    private AppController appController;

    @Override
    public void create() {
        // Boilerplate setup
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        // Set up stage
        stage = new Stage(viewport);
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Initialize grid for pathfinder to act on
        grid = new Grid(40, 64);
        grid.generate();

        // Visual representation of grid and algorithm
        int gridWidth = 640;
        int gridHeight = 400;
        gridDisplayer = new GridDisplayer((SCREEN_WIDTH - gridWidth) / 2,
                (SCREEN_HEIGHT - gridHeight) / 2,
                gridWidth, gridHeight, grid, shapeRenderer);

        metricsDisplayer = new MetricsDisplayer();

        visualizer = new AlgorithmVisualizer(gridDisplayer, metricsDisplayer);

        AlgorithmManager algorithmManager = new AlgorithmManager();
        appController = new AppController(grid, algorithmManager, visualizer);

        // Set up widgets
        widgetHandler = new WidgetManager(appController, visualizer);
        widgetHandler.addToLayout(rootTable, gridWidth, 100);
        rootTable.row();
        metricsDisplayer.addToLayout(rootTable, gridWidth, 100);

        // Set up input processor
        InputManager inputManager = new InputManager(camera, stage, appController, gridDisplayer);
        Gdx.input.setInputProcessor(inputManager.multiplexer);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

		gridDisplayer.renderGrid();
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
