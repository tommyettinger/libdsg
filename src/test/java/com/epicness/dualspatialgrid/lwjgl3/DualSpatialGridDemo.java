package com.epicness.dualspatialgrid.lwjgl3;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.graphics.Color.TAN;
import static com.epicness.dualspatialgrid.Constants.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.epicness.dualspatialgrid.Ball;
import com.epicness.dualspatialgrid.Constants;
import com.epicness.dualspatialgrid.Input;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.logic.Logic;

public class DualSpatialGridDemo extends Game {

    public Sprite circle;

    public DualSpatialGrid dualSpatialGrid;
    // Our shapes
    public Array<Ball> balls;

    private SpriteBatch spriteBatch;

    private Logic logic;
//    private Profiler profiler;

    @Override
    public void create() {
        circle = new Sprite(new Texture("circle.png"));

        dualSpatialGrid = new DualSpatialGrid(SIZING);
        balls = new Array<>();
        spriteBatch = new SpriteBatch();

        logic = new Logic(balls, dualSpatialGrid, circle);
        //profiler = new Profiler();
        new Input(logic);
    }

    @Override
    public void render() {
        logic.update(Gdx.graphics.getDeltaTime());
        //profiler.update(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(TAN);
        spriteBatch.begin();
        renderBalls();
        //profiler.render(spriteBatch);
        spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(L)) {
            System.out.println(Gdx.graphics.getFramesPerSecond());
        }
    }

    private void renderBalls() {
        for (int i = 0; i < balls.size; i++) {
            balls.get(i).draw(spriteBatch);
        }
    }

    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Dual Spatial Grid");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        new Lwjgl3Application(new DualSpatialGridDemo(), configuration);
    }

}