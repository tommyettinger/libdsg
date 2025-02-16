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
    private final GridPoint2 gridPoint2;
    private static final Array<DSGItem> EMPTY = new Array<>(true, 0, DSGItem.class);

    public SpatialGrid(Sizing sizing) {
        this.sizing = sizing;
        cellSize = sizing.getCellSize();
        offsetX = sizing.getOffsetX();
        offsetY = sizing.getOffsetY();

        gridMap = new HashMap<>(1024, 0.5f);
        gridPoint2 = new GridPoint2();
    }

    private Array<DSGItem> getContents(int col, int row) {
//        GridPoint2 key = new GridPoint2(col, row);
//        OrderedSet<DSGItem> existing = gridMap.get(key);
//        if(existing == null){
//            gridMap.put(key, existing = new OrderedSet<>(8, 0.5f));
//        }
//        return existing;
        return gridMap.computeIfAbsent(new GridPoint2(col, row), k -> new Array<>(true, 8, DSGItem.class));
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
        return gridMap.getOrDefault(gridPoint2.set(col, row), EMPTY);
    }
}