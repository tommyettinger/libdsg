package com.epicness.dualspatialgrid.verlet.logic;

import com.badlogic.gdx.math.Vector2;
import com.epicness.dualspatialgrid.verlet.HasVerletCircle;
import com.epicness.dualspatialgrid.verlet.VerletCircle;

import java.util.List;

public class BruteForceSolver {

    public <T extends HasVerletCircle> void solveCollisions(List<T> circles, int index) {
        VerletCircle circle1 = circles.get(index).getVerletCircle();
        for (int i = index + 1; i < circles.size(); i++) {
            VerletCircle circle2 = circles.get(i).getVerletCircle();
            Vector2 collisionAxis = new Vector2().set(circle1.currentPos).sub(circle2.currentPos);
            float dist = collisionAxis.len();
            if (dist < circle1.radius + circle2.radius) {
                float delta = circle1.radius + circle2.radius - dist;
                circle1.currentPos.add(collisionAxis.nor().scl(delta).scl(0.5f));
                circle2.currentPos.sub(collisionAxis);
            }
        }
    }
}