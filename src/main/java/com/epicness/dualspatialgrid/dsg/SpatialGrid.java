package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.epicness.dualspatialgrid.Sizing;

import java.util.HashMap;
import java.util.Map;

public class SpatialGrid {

    public final float cellSize, offsetX, offsetY;
    private final Map<GridPoint2, Array<DSGItem>> gridMap;
    public final Sizing sizing;
    private transient final GridPoint2 gridPoint2 = new GridPoint2();
    private static final Array<DSGItem> EMPTY = new Array<>(true, 0, DSGItem.class);

    public SpatialGrid(Sizing sizing) {
        this.sizing = sizing;
        cellSize = sizing.getCellSize();
        offsetX = sizing.getOffsetX();
        offsetY = sizing.getOffsetY();

        gridMap = new HashMap<>(1024, 0.5f);
    }

    private Array<DSGItem> getContents(int col, int row) {
        // Sadly reimplementing computeIfAbsent() because RoboVM can't use it.
        gridPoint2.set(col, row);
        Array<DSGItem> v;
        if ((v = gridMap.get(gridPoint2)) == null) {
            Array<DSGItem> newValue = new Array<>(true, 8, DSGItem.class);
            gridMap.put(gridPoint2.cpy(), newValue);
            return newValue;
        }
        return v;
    }

    public void clear() {
        gridMap.clear();
    }

    public void insert(DSGItem dsgItem) {
        int col = (int) ((dsgItem.getCenterX() - offsetX) / cellSize);
        int row = (int) ((dsgItem.getCenterY() - offsetY) / cellSize);

        getContents(col, row).add(dsgItem);
    }

    public Array<DSGItem> getDSGItems(int col, int row) {
        // Reimplementing getOrDefault() because RoboVM can't use it.
        Array<DSGItem> got = gridMap.get(gridPoint2.set(col, row));
        return got == null ? EMPTY : got;
    }
}