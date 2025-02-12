package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectSet;
import com.epicness.dualspatialgrid.Sizing;

import java.util.HashSet;
import java.util.Set;

public class SpatialGrid {

    public final Sprite[][] cells;
    public final float cellSize, xOffset, yOffset;
    public final Sprite[] cLines, rLines;
    private final ObjectSet<DSGObject>[][] grid;
    public final Sizing sizing;

    @SuppressWarnings("unchecked")
    public SpatialGrid(Sizing sizing,
                       Sprite pixelSprite, Color color, Color lineColor) {
        this.sizing = sizing;
        cells = new Sprite[sizing.getGridColumns()][sizing.getGridRows()];
        this.cellSize = sizing.getCellSize();
        this.xOffset = sizing.getOffsetX();
        this.yOffset = sizing.getOffsetY();

        cLines = new Sprite[sizing.getGridColumns() - 1];
        rLines = new Sprite[sizing.getGridRows() - 1];
        grid = new ObjectSet[sizing.getGridColumns()][sizing.getGridRows()];

        for (int c = 0; c < cells.length; c++) {

            for (int r = 0; r < cells[c].length; r++) {
                cells[c][r] = new Sprite(pixelSprite);
                cells[c][r].setSize(cellSize, cellSize);
                cells[c][r].setPosition(c * cellSize + xOffset, r * cellSize + yOffset);
                cells[c][r].setColor(color);

                grid[c][r] = new ObjectSet<>();
            }
        }

        for (int c = 0; c < cLines.length; ) {
            cLines[c] = new Sprite(pixelSprite);
            cLines[c].setColor(lineColor);
            cLines[c].setSize(2f, sizing.getGridRows() * cellSize);
            cLines[c].setPosition(cellSize * ++c + xOffset, yOffset);
        }

        for (int r = 0; r < rLines.length; ) {
            rLines[r] = new Sprite(pixelSprite);
            rLines[r].setColor(lineColor);
            rLines[r].setSize(sizing.getGridColumns() * cellSize, 2f);
            rLines[r].setPosition(xOffset, cellSize * ++r + yOffset);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int c = 0; c < cells.length; c++) {
            for (int r = 0; r < cells[c].length; r++) {
                cells[c][r].draw(spriteBatch);
            }
        }

        for (int c = 0; c < cLines.length; c++) {
            cLines[c].draw(spriteBatch);
        }

        for (int r = 0; r < rLines.length; r++) {
            rLines[r].draw(spriteBatch);
        }
    }

    public void clear() {
        for (int c = 0; c < grid.length; c++) {
            for (int r = 0; r < grid[c].length; r++) {
                grid[c][r].clear();
            }
        }
    }

    public void insert(DSGObject dsgObject) {
        int col = (int) ((dsgObject.getCenterX() - xOffset) / cellSize);
        int row = (int) ((dsgObject.getCenterY() - yOffset) / cellSize);

        if(col >= 0 && col < grid.length && row >= 0 && row < grid[col].length)
            grid[col][row].add(dsgObject);
    }

    public ObjectSet<DSGObject> getDSGObjects(int col, int row) {
        return grid[col][row];
    }
}
