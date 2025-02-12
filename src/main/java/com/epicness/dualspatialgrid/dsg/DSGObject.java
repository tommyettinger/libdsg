package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.math.Vector2;
import com.epicness.dualspatialgrid.utils.Movable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class DSGObject implements Movable {

    private boolean gridA;
    Consumer<Boolean> gridAListener;
    BiConsumer<Float, Float> translationListener;
    public int col, row;
    public boolean kinematic;

    public boolean isGridA() {
        return gridA;
    }

    public void setGridA(boolean gridA) {
        this.gridA = gridA;
        if (gridAListener != null) gridAListener.accept(gridA);
    }

    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }

    public abstract float getCenterX();

    public abstract float getCenterY();

    public abstract void setPositionCentered(float x, float y);
}
