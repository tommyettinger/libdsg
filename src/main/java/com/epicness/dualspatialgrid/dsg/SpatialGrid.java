package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;
import java.util.Set;

public class SpatialGrid {

    public final Sprite[][] cells;
    public final float cellSize, xOffset, yOffset;
    public final Sprite[] cLines, rLines;
    private final Set<DSGObject>[][] grid;

    @SuppressWarnings("unchecked")
    public SpatialGrid(int columns, int rows, float cellSize, float xOffset, float yOffset,
                       Sprite pixelSprite, Color color, Color lineColor) {
        cells = new Sprite[columns][];
        this.cellSize = cellSize;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        for (int c = 0; c < columns; c++) {
            cells[c] = new Sprite[rows];
            for (int r = 0; r < rows; r++) {
                cells[c][r] = new Sprite(pixelSprite);
                cells[c][r].setSize(cellSize, cellSize);
                cells[c][r].setPosition(c * cellSize + xOffset, r * cellSize + yOffset);
                cells[c][r].setColor(color);
            }
        }

        cLines = new Sprite[columns - 1];
        for (int c = 0; c < cLines.length; ) {
            cLines[c] = new Sprite(pixelSprite);
            cLines[c].setColor(lineColor);
            cLines[c].setSize(2f, rows * cellSize);
            cLines[c].setPosition(cellSize * ++c + xOffset, yOffset);
        }

        rLines = new Sprite[rows - 1];
        for (int r = 0; r < rLines.length; ) {
            rLines[r] = new Sprite(pixelSprite);
            rLines[r].setColor(lineColor);
            rLines[r].setSize(columns * cellSize, 2f);
            rLines[r].setPosition(xOffset, cellSize * ++r + yOffset);
        }

        grid = new HashSet[columns][rows];

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = new HashSet<>();
            }
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

        grid[col][row].add(dsgObject);
    }

    public Set<DSGObject> getDSGObjects(int col, int row) {
        return grid[col][row];
    }
}
