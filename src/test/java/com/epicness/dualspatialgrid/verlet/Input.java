package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import static com.badlogic.gdx.Input.Buttons.LEFT;
import static com.badlogic.gdx.Input.Keys.F;

public class Input extends InputAdapter {

    private final PieceSpawner<?> spawner;

    public Input(PieceSpawner<?> spawner) {
        this.spawner = spawner;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        if (button == LEFT) {
            spawner.spawnPiece(screenX, screenY);
        } else {
            spawner.spawnPieces(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode != F) return false; // Frames Per Second
        System.out.println(Gdx.graphics.getFramesPerSecond());
        return false;
    }
}