package com.epicness.dualspatialgrid.logic;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.epicness.dualspatialgrid.Ball;
import com.epicness.dualspatialgrid.dsg.DSGCircle;
import com.epicness.dualspatialgrid.dsg.DSGItem;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.dsg.HasDSGItem;

public class CollisionResolver {

    private final DualSpatialGrid dualSpatialGrid;
    private final Array<Ball> balls;
    public int maxIterations = 1;

    public CollisionResolver(DualSpatialGrid dualSpatialGrid, Array<Ball> balls) {
        this.dualSpatialGrid = dualSpatialGrid;
        this.balls = balls;
    }

    public void resolveAllCollisions() {
        dualSpatialGrid.clear();
        for (int i = 0; i < balls.size; i++) {
            dualSpatialGrid.insert(balls.get(i));
        }
        for (int i = 0; i < balls.size; i++) {
            resolveAllCollisions(dualSpatialGrid.getNearby(balls.get(i)));
        }
    }

    private void resolveAllCollisions(OrderedSet<HasDSGItem> dsgItemSet) {
        Array<HasDSGItem> dsgItemArray = dsgItemSet.orderedItems();
        boolean collisionsExist;

        for (int i = 0; i < maxIterations; i++) {
            collisionsExist = false;
            for (int j = 0; j < dsgItemSet.size; j++) {
                DSGItem a = dsgItemArray.get(j).getDSGItem();
                for (int k = j + 1; k < dsgItemArray.size; k++) {
                    DSGItem b = dsgItemArray.get(k).getDSGItem();

                    collisionsExist |= resolveCirclesCollision((DSGCircle) a, (DSGCircle) b);
                }
            }

            if (!collisionsExist) break; // Exit early if no collisions remain
        }
    }

    private boolean resolveCirclesCollision(DSGCircle a, DSGCircle b) {
        float dx = b.getCenterX() - a.getCenterX();
        float dy = b.getCenterY() - a.getCenterY();
        float distSq = dx * dx + dy * dy;
        float minDist = a.getRadius() + b.getRadius();

        if (distSq < minDist * minDist) {
            float dist = (float) Math.sqrt(distSq);
            float overlap = minDist - dist;

            if (dist > 0) {
                float nx = dx / dist;
                float ny = dy / dist;
                float pushAmount = (a.kinematic || b.kinematic) ? overlap : overlap / 2f;
                if (!a.kinematic)
                    a.setPositionCentered(a.getCenterX() - nx * pushAmount, a.getCenterY() - ny * pushAmount);
                if (!b.kinematic)
                    b.setPositionCentered(b.getCenterX() + nx * pushAmount, b.getCenterY() + ny * pushAmount);
            }
            return true;
        }
        return false;
    }

    public void toggleIterations() {
        maxIterations = maxIterations == 1 ? 5 : maxIterations == 5 ? 10 : 1;
        System.out.println("Max iterations: " + maxIterations);
    }
}