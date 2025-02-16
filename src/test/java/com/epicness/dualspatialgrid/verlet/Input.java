package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.epicness.dualspatialgrid.verlet.logic.Logic;

import static com.badlogic.gdx.Input.Buttons.LEFT;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.L;

public class Input extends InputAdapter {

    private final Logic<?> logic;

    public Input(Logic<?> logic) {
        this.logic = logic;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        if (button == LEFT) {
            logic.pieceSpawner.spawnPiece(screenX, screenY);
        } else {
            logic.pieceSpawner.spawnPieces(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode != L) return false;
        System.out.println(Gdx.graphics.getFramesPerSecond());
        return false;
    }
}