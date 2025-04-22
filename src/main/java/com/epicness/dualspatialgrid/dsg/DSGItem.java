package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.math.Vector2;
import com.epicness.dualspatialgrid.utils.BooleanConsumer;
import com.epicness.dualspatialgrid.utils.FloatFloatBiConsumer;
import com.epicness.dualspatialgrid.utils.Movable;

public abstract class DSGItem implements Movable {

    private boolean gridA;
    protected BooleanConsumer gridAListener;
    protected FloatFloatBiConsumer translationListener;
    public int col, row;
    public boolean kinematic;

    public boolean isGridA() {
        return gridA;
    }

    public void setGridA(boolean gridA) {
        this.gridA = gridA;
        if (gridAListener != null) gridAListener.accept(gridA);
    }

    /**
     * Creates and returns a new Vector2 that holds the center position of this DSGItem.
     * @return a new Vector2 containing the center x,y position of this DSGItem
     */
    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }

    public abstract float getCenterX();

    public abstract float getCenterY();

    public abstract void setPositionCentered(float x, float y);

    public void setPositionCentered(Vector2 v) {
        setPositionCentered(v.x, v.y);
    }
}
