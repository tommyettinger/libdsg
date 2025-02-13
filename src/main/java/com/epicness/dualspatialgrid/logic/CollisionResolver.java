package com.epicness.dualspatialgrid.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.epicness.dualspatialgrid.Ball;
import com.epicness.dualspatialgrid.PolyBall;
import com.epicness.dualspatialgrid.Sizing;
import com.epicness.dualspatialgrid.dsg.DSGCircle;
import com.epicness.dualspatialgrid.dsg.DSGItem;
import com.epicness.dualspatialgrid.dsg.DSGPolygon;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.utils.CollisionUtils;

public class CollisionResolver {

    private final DualSpatialGrid dualSpatialGrid;
    private final Sizing sizing;
    private final Array<Ball> balls;
    private final Array<PolyBall> polyBalls;
    public int maxIterations = 1;

    public CollisionResolver(DualSpatialGrid dualSpatialGrid, Array<Ball> balls, Array<PolyBall> polyBalls) {
        this.dualSpatialGrid = dualSpatialGrid;
        this.sizing = dualSpatialGrid.sizing;
        this.balls = balls;
        this.polyBalls = polyBalls;
    }

    public void resolveAllCollisions() {
        dualSpatialGrid.clear();
        for (int i = 0; i < balls.size; i++) {
            dualSpatialGrid.insert(balls.get(i).getDSGItem());
        }
        for (int i = 0; i < polyBalls.size; i++) {
            dualSpatialGrid.insert(polyBalls.get(i).getDSGItem());
        }
        for (int i = 0; i < balls.size; i++) {
            resolveAllCollisions(dualSpatialGrid.getNearby(balls.get(i).getDSGItem()));
        }
        for (int i = 0; i < polyBalls.size; i++) {
            resolveAllCollisions(dualSpatialGrid.getNearby(polyBalls.get(i).getDSGItem()));
        }
    }

    private void resolveAllCollisions(OrderedSet<DSGItem> dsgItemSet) {
        boolean collisionsExist;
        Array<DSGItem> dsgItemArray = dsgItemSet.orderedItems();
        for (int i = 0; i < maxIterations; i++) {
            collisionsExist = false;
            for (int j = 0; j < dsgItemSet.size; j++) {
                DSGItem a = dsgItemArray.get(j);
                if (j == dsgItemSet.size - 1) {
                    keepInBounds(a);
                }
                for (int k = j + 1; k < dsgItemSet.size; k++) {
                    DSGItem b = dsgItemArray.get(k);
                    if (a instanceof DSGCircle && b instanceof DSGCircle) {
                        collisionsExist |= resolveCirclesCollision((DSGCircle) a, (DSGCircle) b);
                    } else if (a instanceof DSGCircle && b instanceof DSGPolygon) {
                        collisionsExist |= resolveCirclePolygonCollision((DSGCircle) a, (DSGPolygon) b);
                    } else if (a instanceof DSGPolygon && b instanceof DSGCircle) {
                        collisionsExist |= resolveCirclePolygonCollision((DSGCircle) b, (DSGPolygon) a);
                    } else if (a instanceof DSGPolygon && b instanceof DSGPolygon) {
                        collisionsExist |= resolvePolygonsCollision((DSGPolygon) a, (DSGPolygon) b);
                    }
                    keepInBounds(a);
                    keepInBounds(b);
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

    private boolean resolveCirclePolygonCollision(DSGCircle a, DSGPolygon b) {
        boolean collision = false;
        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        if (CollisionUtils.overlapPolygonCircle(b.getPolygon(), a.getCircle(), mtv)) {
            collision = true;
        }
        if (b.getPolygon().contains(a.getCenterX(), a.getCenterY())) {
            Vector2 centerToEdge = new Vector2(a.getCenterX() - b.getCenterX(), a.getCenterY() - b.getCenterY());
            mtv.normal.set(centerToEdge).nor();
            if (mtv.normal.isZero()) {
                float angle = MathUtils.random(360f);
                mtv.normal.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            }
            mtv.depth = a.getRadius() * 2f;
            collision = true;
        }
        if (collision) {
            float nx = mtv.normal.x;
            float ny = mtv.normal.y;
            float pushAmount = (a.kinematic || b.kinematic) ? mtv.depth : mtv.depth / 2f;
            if (!a.kinematic)
                a.setPositionCentered(a.getCenterX() - nx * pushAmount, a.getCenterY() - ny * pushAmount);
            if (!b.kinematic)
                b.setPositionCentered(b.getCenterX() + nx * pushAmount, b.getCenterY() + ny * pushAmount);
        }

        return collision;
    }

    private boolean resolvePolygonsCollision(DSGPolygon a, DSGPolygon b) {
        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        if (Intersector.overlapConvexPolygons(a.getPolygon(), b.getPolygon(), mtv)) {
            float nx = mtv.normal.x;
            float ny = mtv.normal.y;
            float pushAmount = (a.kinematic || b.kinematic) ? mtv.depth : mtv.depth / 2f;
            if (!a.kinematic)
                a.setPositionCentered(a.getCenterX() + nx * pushAmount, a.getCenterY() + ny * pushAmount);
            if (!b.kinematic)
                b.setPositionCentered(b.getCenterX() - nx * pushAmount, b.getCenterY() - ny * pushAmount);
            return true;
        }
        return false;
    }

    private void keepInBounds(DSGItem dsgItem) {
        float ballRadius = sizing.getCellSize() * 0.25f;
        float x = MathUtils.clamp(dsgItem.getCenterX(), sizing.getOffsetX() + ballRadius,
                sizing.getOffsetX() + sizing.getEffectiveWidth() - ballRadius);
        float y = MathUtils.clamp(dsgItem.getCenterY(), sizing.getOffsetY() + ballRadius,
                sizing.getOffsetY() + sizing.getEffectiveHeight() - ballRadius);
        dsgItem.setPositionCentered(x, y);
    }

    public void toggleIterations() {
        maxIterations = maxIterations == 1 ? 5 : maxIterations == 5 ? 10 : 1;
        System.out.println("Max iterations: " + maxIterations);
    }
}