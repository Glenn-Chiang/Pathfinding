package com.github.glennchiang.pathfinding.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.glennchiang.pathfinding.AppController;
import com.github.glennchiang.pathfinding.visualization.GridDisplayer;

public class InputManager implements InputProcessor {
    public final InputMultiplexer multiplexer = new InputMultiplexer();
    private final Viewport viewport;
    private final AppController appController;
    private final GridDisplayer gridDisplayer;

    public InputManager(Viewport viewport,Stage stage, AppController appController, GridDisplayer gridDisplayer) {
        this.viewport = viewport;
        this.appController = appController;
        this.gridDisplayer = gridDisplayer;
        // Add stage first to prioritize inputs on UI widgets
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Disabled when visualizer is running
        if (appController.getState() != AppController.State.INACTIVE) return false;

        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        viewport. unproject(touchPos);
        Vector2 touchPos2D = new Vector2(touchPos.x, touchPos.y);

        switch (button) {
            case Input.Buttons.LEFT:
                gridDisplayer.handleStartTouch(touchPos2D);
                break;
            case Input.Buttons.RIGHT:
                gridDisplayer.handleTargetTouch(touchPos2D);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
