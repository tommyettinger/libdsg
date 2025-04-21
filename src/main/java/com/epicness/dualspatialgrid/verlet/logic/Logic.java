package com.epicness.dualspatialgrid.verlet.logic;

import com.badlogic.gdx.math.Circle;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.verlet.HasVerletCircleAndDSG;

import java.util.List;

public class Logic<P extends HasVerletCircleAndDSG> {

    private final Solver<P> solver;

    private static final float MAX_FRAME_TIME = 1f / 30f;
    private static final float TIME_STEP = 1f / 60f;
    private float accumulator;

    public Logic(DualSpatialGrid dsg, List<P> pieces, Circle bounds) {
        VerletIntegrator verletIntegrator = new VerletIntegrator(bounds);
        solver = new Solver<>(verletIntegrator, new DSGSolver(dsg), pieces);
    }

    public void update(float delta) {
        if (delta > MAX_FRAME_TIME) delta = MAX_FRAME_TIME;
        accumulator += delta;

        while (accumulator >= TIME_STEP) {
            step();
            accumulator -= TIME_STEP;
        }
    }

    private void step() {
        int subSteps = 3;
        float subDelta = TIME_STEP / subSteps;
        for (int i = 0; i < subSteps; i++) {
            solver.solve(subDelta);
        }
    }
}