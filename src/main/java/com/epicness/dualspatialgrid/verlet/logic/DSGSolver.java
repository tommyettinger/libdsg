package com.epicness.dualspatialgrid.verlet.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.epicness.dualspatialgrid.dsg.DSGCircle;
import com.epicness.dualspatialgrid.dsg.DSGItem;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.dsg.HasDSGItem;

import java.util.List;

public class DSGSolver {

    private final DualSpatialGrid dualSpatialGrid;
    private final Vector2 collisionAxis;

    public DSGSolver(DualSpatialGrid dualSpatialGrid) {
        this.dualSpatialGrid = dualSpatialGrid;
        collisionAxis = new Vector2();
    }

    public <T extends HasDSGItem> void prepare(List<T> circles) {
        dualSpatialGrid.clear();
        for (int i = 0; i < circles.size(); i++) {
            dualSpatialGrid.insert(circles.get(i).getDSGItem());
        }
    }

    public void solveCollisions(HasDSGItem circle) {
        OrderedSet<DSGItem> dSGItemSet = dualSpatialGrid.getNearby(circle.getDSGItem());
        Array<DSGItem> dSGItemArray = dSGItemSet.orderedItems();
        for (int i = 0; i < dSGItemArray.size; i++) {
            resolveCirclesCollision((DSGCircle) circle.getDSGItem(), (DSGCircle) dSGItemArray.get(i));
        }
    }

    private void resolveCirclesCollision(DSGCircle a, DSGCircle b) {
        collisionAxis.set(a.getCenter()).sub(b.getCenter());
        float dist = collisionAxis.len();

        if (dist < a.getRadius() + b.getRadius()) {
            float overlap = a.getRadius() + b.getRadius() - dist;
            a.translate(collisionAxis.nor().scl(overlap).scl(0.5f));
            b.translate(collisionAxis.scl(-1f));
        }
    }
}