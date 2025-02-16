package com.epicness.dualspatialgrid.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.epicness.dualspatialgrid.Constants;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.verlet.Input;
import com.epicness.dualspatialgrid.verlet.Renderer;
import com.epicness.dualspatialgrid.verlet.ColorfulBall;
import com.epicness.dualspatialgrid.verlet.logic.Logic;

import java.util.ArrayList;
import java.util.List;

import static com.epicness.dualspatialgrid.Constants.*;

public class VerletDemo extends Game {

    private Logic<ColorfulBall> logic;
    private Renderer<ColorfulBall> renderer;

    @Override
    public void create() {
        List<ColorfulBall> colorfulBalls = new ArrayList<>();
        Circle circle = new Circle(WINDOW_WIDTH * 0.5f, WINDOW_HEIGHT * 0.5f, WINDOW_HEIGHT * 0.5f);
        DualSpatialGrid dualSpatialGrid = new DualSpatialGrid(SIZING);
        logic = new Logic<>(dualSpatialGrid, colorfulBalls, (x, y) -> new ColorfulBall(x, y, MathUtils.random(3f, 8f)), circle);
        new Input(logic);
        renderer = new Renderer<>(colorfulBalls, circle);
    }

    @Override
    public void render() {
        logic.update(Gdx.graphics.getDeltaTime());
        renderer.render();
    }

    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Verlet Dual Spatial Grid");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        new Lwjgl3Application(new VerletDemo(), configuration);
    }
}