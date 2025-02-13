package com.epicness.dualspatialgrid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.logic.Logic;

import static com.badlogic.gdx.graphics.Color.TAN;

public class DualSpatialGridDemo extends Game {

    public Sprite circle;

    public DualSpatialGrid dualSpatialGrid;
    // Our shapes
    public Array<Ball> balls;
    public Array<PolyBall> polyBalls;

    private SpriteBatch spriteBatch;

    private Logic logic;
    private Profiler profiler;

    @Override
    public void create() {
        Sprite pixel = new Sprite(new Texture("pixel.png"));
        circle = new Sprite(new Texture("circle.png"));

        dualSpatialGrid = new DualSpatialGrid(new Sizing(), pixel);
        balls = new Array<>();
        polyBalls = new Array<>();
        spriteBatch = new SpriteBatch();

        logic = new Logic(this);
        profiler = new Profiler();
        new Input(logic);
    }

    @Override
    public void render() {
        logic.update(Gdx.graphics.getDeltaTime());
        profiler.update(Gdx.graphics.getDeltaTime());
        ScreenUtils.clear(TAN);
        spriteBatch.begin();
        dualSpatialGrid.draw(spriteBatch);
        renderBalls();
        renderPolyBalls();
        profiler.render(spriteBatch);
        spriteBatch.end();
    }

    private void renderBalls() {
        for (int i = 0; i < balls.size; i++) {
            balls.get(i).draw(spriteBatch);
        }
    }

    private void renderPolyBalls() {
        for (int i = 0; i < polyBalls.size; i++) {
            polyBalls.get(i).draw(spriteBatch);
        }
    }
}