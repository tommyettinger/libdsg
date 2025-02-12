package com.epicness.dualspatialgrid;

public class Constants {

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 920;

    public static final int GRID_MARGIN = 40;
    public static final int GRID_X = GRID_MARGIN;
    public static final int GRID_Y = GRID_MARGIN;

    public static final int EFFECTIVE_WIDTH = WINDOW_WIDTH - GRID_MARGIN * 2;
    public static final int EFFECTIVE_HEIGHT = WINDOW_HEIGHT - GRID_MARGIN * 2;

    public static final int CELL_SIZE = 40;
    public static final int BALL_SIZE = CELL_SIZE / 2;
    public static final int BALL_RADIUS = BALL_SIZE / 2;
    public static final int GRID_COLUMNS = EFFECTIVE_WIDTH / CELL_SIZE;
    public static final int GRID_ROWS = EFFECTIVE_HEIGHT / CELL_SIZE;
}