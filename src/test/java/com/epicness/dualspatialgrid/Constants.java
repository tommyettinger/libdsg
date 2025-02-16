package com.epicness.dualspatialgrid;

public class Constants {

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 920;

    public static final int GRID_X = 40;
    public static final int GRID_Y = 40;

    public static final int EFFECTIVE_WIDTH = WINDOW_WIDTH - GRID_X * 2;
    public static final int EFFECTIVE_HEIGHT = WINDOW_HEIGHT - GRID_Y * 2;

    public static final int CELL_SIZE = 40;
    public static final int BALL_SIZE = CELL_SIZE / 2;

    public static final Sizing SIZING = new Sizing(WINDOW_WIDTH, WINDOW_HEIGHT, GRID_X, GRID_Y, CELL_SIZE);
}