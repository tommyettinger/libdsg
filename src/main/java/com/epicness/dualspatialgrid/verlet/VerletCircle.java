package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.math.Vector2;

public class VerletCircle {

    public final Vector2 currentPos;
    public final Vector2 oldPos;
    public final Vector2 acceleration;
    public final float radius;

    public VerletCircle(float x, float y, float radius) {
        currentPos = new Vector2(x, y);
        oldPos = new Vector2(x, y);
        acceleration = new Vector2();
        this.radius = radius;
    }
}