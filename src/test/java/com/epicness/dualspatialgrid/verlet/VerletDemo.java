package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.verlet.logic.Logic;

import java.util.ArrayList;
import java.util.List;

import static com.epicness.dualspatialgrid.Constants.*;

public class VerletDemo extends Game {

    private Logic<Soldier> logic;
    private Renderer<Soldier> renderer;

    @Override
    public void create() {
        List<Soldier> soldiers = new ArrayList<>();
        Circle circle = new Circle(WINDOW_WIDTH * 0.5f, WINDOW_HEIGHT * 0.5f, WINDOW_HEIGHT * 0.5f);
        DualSpatialGrid dualSpatialGrid = new DualSpatialGrid(SIZING);
        logic = new Logic<>(dualSpatialGrid, soldiers, (x, y) -> new Soldier(x, y, MathUtils.random(3f, 8f)), circle);
        new Input(logic);
        renderer = new Renderer<>(soldiers, circle);
    }

    @Override
    public void render() {
        logic.update(Gdx.graphics.getDeltaTime());
        renderer.render();
    }
}