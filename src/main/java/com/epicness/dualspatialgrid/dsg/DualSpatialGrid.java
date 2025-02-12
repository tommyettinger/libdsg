package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.epicness.dualspatialgrid.utils.IndexedSet;

import static com.badlogic.gdx.graphics.Color.*;

public class DualSpatialGrid {

    private final SpatialGrid gridA, gridB;
    private final float cellSize, halfCellSize;
    private final IndexedSet<DSGObject> nearby;

    public DualSpatialGrid(int cols, int rows, float cellSize, float xOffset, float yOffset, Sprite pixelSprite) {
        this.cellSize = cellSize;
        this.halfCellSize = cellSize / 2f;
        gridA = new SpatialGrid(cols, rows, cellSize, xOffset, yOffset, pixelSprite, SKY, BLUE);
        gridB = new SpatialGrid(cols + 1, rows + 1, cellSize, xOffset - halfCellSize, yOffset - halfCellSize,
            pixelSprite, new Color(0f, 1f, 0f, 0.25f), FOREST);
        nearby = new IndexedSet<>();
    }

    public void draw(SpriteBatch spriteBatch) {
        gridA.draw(spriteBatch);
        gridB.draw(spriteBatch);
    }

    public void clear() {
        gridA.clear();
        gridB.clear();
    }

    public void insert(DSGObject dsgObject) {
        float x = dsgObject.getCenterX();
        float y = dsgObject.getCenterY();
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
            dsgObject.setGridA(true);
        } else {
            col = (int) (xB / cellSize);
            row = (int) (yB / cellSize);
            dsgObject.setGridA(false);
        }
        gridA.insert(dsgObject);
        gridB.insert(dsgObject);
        dsgObject.col = col;
        dsgObject.row = row;
    }

    public IndexedSet<DSGObject> getNearby(DSGObject dsgObject) {
        nearby.clear();
        SpatialGrid mainGrid = dsgObject.isGridA() ? gridA : gridB;
        SpatialGrid otherGrid = (mainGrid == gridA) ? gridB : gridA;

        int col = (int) ((dsgObject.getCenterX() - mainGrid.xOffset) / cellSize);
        int row = (int) ((dsgObject.getCenterY() - mainGrid.yOffset) / cellSize);

        nearby.addAll(mainGrid.getDSGObjects(col, row));

        if (mainGrid == gridA) {
            for (int c = col; c < col + 2 && c < otherGrid.cells.length; c++) {
                for (int r = row; r < row + 2 && r < otherGrid.cells[c].length; r++) {
                    nearby.addAll(otherGrid.getDSGObjects(c, r));
                }
            }
        } else {
            for (int c = col; c > col - 2 && c >= 0 && c < otherGrid.cells.length; c--) {
                for (int r = row; r > row - 2 && r >= 0 && r < otherGrid.cells[c].length; r--) {
                    nearby.addAll(otherGrid.getDSGObjects(c, r));
                }
            }
        }

        return nearby;
    }
}
