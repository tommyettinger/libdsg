package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.math.Circle;

public class DSGCircle extends DSGItem {

    private final Circle circle;

    public DSGCircle(float radius) {
        circle = new Circle();
        circle.setRadius(radius);
    }

    @Override
    public float getCenterX() {
        return circle.x;
    }

    @Override
    public float getCenterY() {
        return circle.y;
    }

    @Override
    public float getX() {
        return circle.x - circle.radius;
    }

    @Override
    public void translateX(float amount) {
        circle.x += amount;
        if (translationListener != null) translationListener.accept(amount, 0f);
    }

    @Override
    public float getY() {
        return circle.y - circle.radius;
    }

    @Override
    public void translateY(float amount) {
        circle.y += amount;
        if (translationListener != null) translationListener.accept(0f, amount);
    }

    @Override
    public void setPositionCentered(float x, float y) {
        setPosition(x - circle.radius, y - circle.radius);
    }

    public float getRadius() {
        return circle.radius;
    }

    public Circle getCircle() {
        return circle;
    }
}
