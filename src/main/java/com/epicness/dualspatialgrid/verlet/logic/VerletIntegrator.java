package com.epicness.dualspatialgrid.verlet.logic;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.epicness.dualspatialgrid.verlet.VerletCircle;

public class VerletIntegrator {

    private Vector2 currentPos, oldPos, acceleration;
    private float radius;
    private final Vector2 velocity, gravity, dir;
    private final Circle boundsCircle;

    public VerletIntegrator(Circle boundsCircle) {
        velocity = new Vector2();
        gravity = new Vector2(0f, -1000f);
        dir = new Vector2();
        this.boundsCircle = boundsCircle;
    }

    public void integrate(VerletCircle circle, float delta) {
        attachCircle(circle);
        applyGravity();
        moveCircle(delta);
    }

    public void attachCircle(VerletCircle circle) {
        currentPos = circle.currentPos;
        oldPos = circle.oldPos;
        acceleration = circle.acceleration;
        radius = circle.radius;
    }

    private void applyGravity() {
        acceleration.add(gravity);
    }

    private void moveCircle(float delta) {
        // Calculate velocity
        velocity.set(currentPos).sub(oldPos);
        // Save current position
        oldPos.set(currentPos);
        // clamp velocity (?)
        float iDelta = (1f/32f) / delta;
        velocity.set(Math.min(Math.max(velocity.x, -iDelta), iDelta),
                Math.min(Math.max(velocity.y, -iDelta), iDelta));
        // Perform Verlet integration
        currentPos.add(velocity).add(acceleration.scl(delta * delta));
        // Reset acceleration
        acceleration.setZero();
    }

    public void applyRectangleConstraint(float lowX, float highX, float lowY, float highY) {
        currentPos.x = Math.max(lowX + radius, Math.min(highX - radius, currentPos.x));
        currentPos.y = Math.max(lowY + radius, Math.min(highY - radius, currentPos.y));
    }

    public void applyCircleConstraint() {
        dir.set(currentPos).sub(boundsCircle.x, boundsCircle.y);
        if (dir.len() > boundsCircle.radius - radius) {
            currentPos.set(boundsCircle.x, boundsCircle.y).add(dir.setLength(boundsCircle.radius - radius));
        }
    }
}