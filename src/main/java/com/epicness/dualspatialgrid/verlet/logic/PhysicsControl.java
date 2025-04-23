package com.epicness.dualspatialgrid.verlet.logic;

import com.badlogic.gdx.math.Circle;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.verlet.HasVerletCircleAndDSG;

import java.util.List;

public class PhysicsControl<P extends HasVerletCircleAndDSG> {

    public final Solver<P> solver;

    public final float maxFrameTime;
    public final float timeStep;
    private float accumulator;

    public PhysicsControl(DualSpatialGrid dsg, List<P> pieces, Circle bounds) {
        this(dsg, pieces, bounds, 1f / 30f, 1f / 60f);
    }
    public PhysicsControl(DualSpatialGrid dsg, List<P> pieces, Circle bounds, float maxFrameTime, float timeStep) {
        this.maxFrameTime = maxFrameTime;
        this.timeStep = timeStep;
        VerletIntegrator verletIntegrator = new VerletIntegrator(bounds);
        solver = new Solver<>(verletIntegrator, new DSGSolver(dsg), pieces);
    }

    public void update(float delta) {
        if (delta > maxFrameTime) delta = maxFrameTime;
        accumulator += delta;

        while (accumulator >= timeStep) {
            step();
            accumulator -= timeStep;
        }
    }

    protected void step() {
        int subSteps = 3;
        float subDelta = timeStep / subSteps;
        for (int i = 0; i < subSteps; i++) {
            solver.solve(subDelta);
        }
    }
}