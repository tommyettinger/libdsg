package com.epicness.dualspatialgrid;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.graphics.Color.TAN;
import static com.epicness.dualspatialgrid.Constants.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
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

        logic = new Logic(this);
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
}