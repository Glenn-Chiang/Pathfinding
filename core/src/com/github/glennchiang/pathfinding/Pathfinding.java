package com.github.glennchiang.pathfinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.glennchiang.pathfinding.algorithms.AStarPathfinder;
import com.github.glennchiang.pathfinding.algorithms.DijkstraPathfinder;
import com.github.glennchiang.pathfinding.algorithms.GreedyPathfinder;
import com.github.glennchiang.pathfinding.algorithms.Pathfinder;

import java.util.ArrayList;
import java.util.List;

public class Pathfinding extends ApplicationAdapter {
    public final static int SCREEN_WIDTH = 800;
    public final static int SCREEN_HEIGHT = 640;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;

    private Grid grid;
    private VisualGrid visualGrid;

    @Override
    public void create() {
        // Boilerplate setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        stage = new Stage(viewport);

        // Initialize grid for pathfinder to act on
        grid = new Grid(20, 32);
        grid.setStart(0, 0);
        grid.setTarget(grid.numRows - 1, grid.numCols - 1);
        grid.setObstacles();

        // Visual representation of grid and algorithm
        int gridWidth = 640;
        int gridHeight = 400;
        visualGrid = new VisualGrid((SCREEN_WIDTH - gridWidth) / 2, (SCREEN_HEIGHT - gridHeight) / 2, gridWidth, gridHeight, grid, shapeRenderer);

        Pathfinder aStar = new AStarPathfinder();
        Pathfinder greedy = new GreedyPathfinder();
        Pathfinder dijkstra = new DijkstraPathfinder();

        Pathfinder[] algorithms = new Pathfinder[]{ aStar, greedy, dijkstra };

        for (Pathfinder algorithm: algorithms) {
            algorithm.findPath(grid);
        }

    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

		visualGrid.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose();
    }
}
