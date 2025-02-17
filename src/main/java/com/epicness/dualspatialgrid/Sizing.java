package com.epicness.dualspatialgrid;

import com.badlogic.gdx.Gdx;

/**
 * Stores sizing information about the window, grid, cell size, offsets, and so on.
 * This can be subclassed to change the behavior of any getters for a specific game.
 */
public class Sizing {

    public Sizing(){
        this(1600, 920, 40, 40, 40);
    }
    
    public Sizing(int windowWidth, int windowHeight, float xOffset, float yOffset, float cellSize) {
        this.WINDOW_WIDTH = windowWidth;
        this.WINDOW_HEIGHT = windowHeight;

        this.GRID_X = xOffset;
        this.GRID_Y = yOffset;

        this.CELL_SIZE = cellSize;
    }

    public int WINDOW_WIDTH;
    public int WINDOW_HEIGHT;
    public float CELL_SIZE;
    public float GRID_X;
    public float GRID_Y;

    public int getWindowWidth() {
        return WINDOW_WIDTH < 0 && Gdx.graphics != null ? Gdx.graphics.getBackBufferWidth() : WINDOW_WIDTH;
    }
    public int getWindowHeight() {
        return WINDOW_HEIGHT < 0 && Gdx.graphics != null ? Gdx.graphics.getBackBufferHeight() : WINDOW_HEIGHT;
    }

    public float getCellSize() {
        return CELL_SIZE;
    }

    public float getOffsetX() {
        return GRID_X;
    }

    public float getOffsetY() {
        return GRID_Y;
    }

    public float getEffectiveWidth() {
        return getWindowWidth() - getOffsetX() * 2;
    }

    public float getEffectiveHeight() {
        return getWindowHeight() - getOffsetY() * 2;
    }

    public Sizing expandedCopy() {
        return new Sizing(WINDOW_WIDTH, WINDOW_HEIGHT, getOffsetX() - getCellSize() / 2, getOffsetY() - getCellSize() / 2, getCellSize());
    }
}