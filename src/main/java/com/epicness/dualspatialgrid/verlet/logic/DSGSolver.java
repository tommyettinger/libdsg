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

    public <T extends HasDSGItem> void prepare(List<T> containers) {
        dualSpatialGrid.clear();
        for (int i = 0; i < containers.size(); i++) {
            dualSpatialGrid.insert(containers.get(i).getDSGItem());
        }
    }

    public void solveCollisions(HasDSGItem container) {
        OrderedSet<DSGItem> dSGItemSet = dualSpatialGrid.getNearby(container.getDSGItem());
        Array<DSGItem> dSGItemArray = dSGItemSet.orderedItems();
        for (int i = 0; i < dSGItemArray.size; i++) {
            resolveCirclesCollision((DSGCircle) container.getDSGItem(), (DSGCircle) dSGItemArray.get(i));
        }
    }

    public <T extends HasDSGItem> void solveCollisions(List<T> containers) {
        for (int i = 0; i < containers.size(); i++) {
            solveCollisions(containers.get(i));
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