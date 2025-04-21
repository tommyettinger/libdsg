package com.epicness.dualspatialgrid.verlet.logic;

import com.epicness.dualspatialgrid.verlet.HasVerletCircleAndDSG;

import java.util.List;

public class Solver<P extends HasVerletCircleAndDSG> {

    private final VerletIntegrator verletIntegrator;
    private final DSGSolver dsgSolver;

    private final List<P> pieces;
    private P piece;

    public Solver(VerletIntegrator verletIntegrator, DSGSolver dsgSolver, List<P> pieces) {
        this.verletIntegrator = verletIntegrator;
        this.dsgSolver = dsgSolver;
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
            BruteForceSolver.solveCollisions(pieces, i);
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