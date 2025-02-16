package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.utils.OrderedSet;
import com.epicness.dualspatialgrid.Sizing;

public class DualSpatialGrid {

    private final SpatialGrid gridA, gridB;
    public final Sizing sizing;
    private final float cellSize, halfCellSize;
    private final OrderedSet<DSGItem> nearby;

    public DualSpatialGrid(Sizing sizing) {
        this.sizing = sizing;
        this.cellSize = sizing.CELL_SIZE;
        this.halfCellSize = sizing.CELL_SIZE * 0.5f;
        gridA = new SpatialGrid(sizing);
        gridB = new SpatialGrid(sizing.expandedCopy());
        nearby = new OrderedSet<>();
    }

    public void clear() {
        gridA.clear();
        gridB.clear();
    }

    public void insert(DSGItem dsgItem) {
        float xA = dsgItem.getCenterX();
        float yA = dsgItem.getCenterY();
        float xB = xA - gridB.offsetX;
        float yB = yA - gridB.offsetY;
        int colA = (int) (xA / cellSize);
        int rowA = (int) (yA / cellSize);
        int colB = (int) (xB / cellSize);
        int rowB = (int) (yB / cellSize);
        float centerAx = colA * cellSize + halfCellSize;
        float centerAy = rowA * cellSize + halfCellSize;
        float centerBx = colB * cellSize + halfCellSize;
        float centerBy = rowB * cellSize + halfCellSize;

        float distA = Math.abs(xA - centerAx) + Math.abs(yA - centerAy);
        float distB = Math.abs(xB - centerBx) + Math.abs(yB - centerBy);

        if (distA < distB) {
            dsgItem.col = colA;
            dsgItem.row = rowA;
            dsgItem.setGridA(true);
        } else {
            dsgItem.col = colB;
            dsgItem.row = rowB;
            dsgItem.setGridA(false);
        }
        gridA.insert(dsgItem);
        gridB.insert(dsgItem);
    }

    public OrderedSet<DSGItem> getNearby(DSGItem dsgObject) {
        nearby.clear();
        SpatialGrid mainGrid = dsgObject.isGridA() ? gridA : gridB;

        int col = (int) ((dsgObject.getCenterX() - mainGrid.offsetX) / cellSize);
        int row = (int) ((dsgObject.getCenterY() - mainGrid.offsetY) / cellSize);

        if (mainGrid == gridA) {
            for (int c = col + 1; c >= col; c--) {
                for (int r = row + 1; r >= row; r--) {
                    nearby.addAll(gridB.getDSGItems(c, r));
                }
            }
        } else {
            for (int c = col - 1; c <= col; c++) {
                for (int r = row - 1; r <= row; r++) {
                    nearby.addAll(gridA.getDSGItems(c, r));
                }
            }
        }

        return nearby;
    }
}
