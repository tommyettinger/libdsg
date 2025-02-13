package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.OrderedSet;
import com.epicness.dualspatialgrid.Sizing;

import static com.badlogic.gdx.graphics.Color.*;

public class DualSpatialGrid {

    private final SpatialGrid gridA, gridB;
    public final Sizing sizing;
    private final float cellSize, halfCellSize;
    private final OrderedSet<DSGItem> nearby;

    public DualSpatialGrid(Sizing sizing, Sprite pixelSprite) {
        this.sizing = sizing;
        this.cellSize = sizing.CELL_SIZE;
        this.halfCellSize = sizing.CELL_SIZE * 0.5f;
        gridA = new SpatialGrid(sizing, pixelSprite, SKY, BLUE);
        gridB = new SpatialGrid(sizing.expandedCopy(),
            pixelSprite, new Color(0f, 1f, 0f, 0.25f), FOREST);
        nearby = new OrderedSet<>();
    }

    public void draw(SpriteBatch spriteBatch) {
        gridA.draw(spriteBatch);
        gridB.draw(spriteBatch);
    }

    public void clear() {
        gridA.clear();
        gridB.clear();
    }

    public void insert(DSGItem dsgItem) {
        float x = dsgItem.getCenterX();
        float y = dsgItem.getCenterY();
        float xA = x - gridA.xOffset;
        float yA = y - gridA.yOffset;
        float xB = x - gridB.xOffset;
        float yB = y - gridB.yOffset;
        float centerAx = ((int) (xA / cellSize)) * cellSize + halfCellSize;
        float centerAy = ((int) (yA / cellSize)) * cellSize + halfCellSize;
        float centerBx = ((int) (xB / cellSize)) * cellSize + halfCellSize;
        float centerBy = ((int) (yB / cellSize)) * cellSize + halfCellSize;

        float distA = Math.abs(xA - centerAx) + Math.abs(yA - centerAy);
        float distB = Math.abs(xB - centerBx) + Math.abs(yB - centerBy);

        int col, row;

        if (distA < distB) {
            col = (int) (xA / cellSize);
            row = (int) (yA / cellSize);
            dsgItem.setGridA(true);
        } else {
            col = (int) (xB / cellSize);
            row = (int) (yB / cellSize);
            dsgItem.setGridA(false);
        }
        gridA.insert(dsgItem);
        gridB.insert(dsgItem);
        dsgItem.col = col;
        dsgItem.row = row;
    }

    public OrderedSet<DSGItem> getNearby(DSGItem dsgItem) {
        nearby.clear();
        SpatialGrid mainGrid = dsgItem.isGridA() ? gridA : gridB;
        SpatialGrid otherGrid = (mainGrid == gridA) ? gridB : gridA;

        // surprisingly, the min(max(clampedValue, lowerLimit), upperLimit) is faster than MathUtils.clamp .
        int col = Math.min(Math.max((int) ((dsgItem.getCenterX() - mainGrid.xOffset) / cellSize), 0), otherGrid.cLines.length);
        int row = Math.min(Math.max((int) ((dsgItem.getCenterY() - mainGrid.yOffset) / cellSize), 0), otherGrid.rLines.length);

//        nearby.addAll(mainGrid.getDSGItems(col, row));

        if (mainGrid == gridA) {
            for (int c = col; c < col + 2 && c < otherGrid.cells.length; c++) {
                for (int r = row; r < row + 2 && r < otherGrid.cells[c].length; r++) {
                    nearby.addAll(otherGrid.getDSGItems(c, r));
                }
            }
        } else {
            for (int c = col; c > col - 2 && c >= 0 && c < otherGrid.cells.length; c--) {
                for (int r = row; r > row - 2 && r >= 0 && r < otherGrid.cells[c].length; r--) {
                    nearby.addAll(otherGrid.getDSGItems(c, r));
                }
            }
        }

        return nearby;
    }
}
