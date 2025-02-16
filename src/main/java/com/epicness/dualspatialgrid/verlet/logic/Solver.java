package com.epicness.dualspatialgrid.verlet.logic;

import com.epicness.dualspatialgrid.verlet.HasVerletCircleAndDSG;

import java.util.List;

public class Solver {

    private final VerletIntegrator verletIntegrator;
    private final DSGSolver dsgSolver;
    private final BruteForceSolver bruteForceSolver;

    private final List<HasVerletCircleAndDSG> pieces;
    private HasVerletCircleAndDSG piece;

    public Solver(VerletIntegrator verletIntegrator, DSGSolver dsgSolver, BruteForceSolver bruteForceSolver, List<HasVerletCircleAndDSG> pieces) {
        this.verletIntegrator = verletIntegrator;
        this.dsgSolver = dsgSolver;
        this.bruteForceSolver = bruteForceSolver;
        this.pieces = pieces;
        piece = null;
    }

    public void solve(float delta) {
        solveDSG(delta);
    }

    private void solveDSG(float delta) {
        dsgSolver.prepare(pieces);
        dsgSolver.solveCollisions(pieces);
        for (int i = 0; i < pieces.size(); i++) {
            piece = pieces.get(i);
            syncVerlet(piece);
            verletIntegrator.integrate(piece.getVerletCircle(), delta);
            verletIntegrator.applyCircleConstraint();
            syncDSG(piece);
        }
    }

    private void solveBruteForce(float delta) {
        for (int i = 0; i < pieces.size(); i++) {
            piece = pieces.get(i);
            verletIntegrator.integrate(piece.getVerletCircle(), delta);
            bruteForceSolver.solveCollisions(pieces, i);
            verletIntegrator.applyCircleConstraint();
        }
    }

    private void syncDSG(HasVerletCircleAndDSG piece) {
        piece.getDSGItem().setPositionCentered(piece.getVerletCircle().currentPos);
    }

    private void syncVerlet(HasVerletCircleAndDSG piece) {
        piece.getVerletCircle().currentPos.set(piece.getDSGItem().getCenter());
    }
}