package com.epicness.dualspatialgrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.epicness.dualspatialgrid.logic.Logic;

import static com.badlogic.gdx.Input.Keys.*;

/**
 * Testing functionality
 */
public class Input extends InputAdapter {

    private final Logic logic;

    public Input(Logic logic) {
        this.logic = logic;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case S: // Spawn
                logic.spawnBalls();
                break;
            case T: // Toggle (iterations)
                logic.collisionResolver.toggleIterations();
                break;
            case Q: // Quit
            case ESCAPE:
                Gdx.app.exit();
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        logic.ballMover.target.set(screenX, screenY);
        return false;
    }
}