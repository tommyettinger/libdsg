package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.NumberUtils;

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
    public void translate(float xAmount, float yAmount) {
        circle.x += xAmount;
        circle.y += yAmount;
        if(translationListener != null) translationListener.accept(xAmount, yAmount);
    }

    @Override
    public void translate(float amount) {
        translate(amount, amount);
    }

    @Override
    public void translate(Vector2 amount) {
        translate(amount.x, amount.y);
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof DSGCircle)) return false;

        DSGCircle dsgCircle = (DSGCircle) o;
        return circle.equals(dsgCircle.circle);
    }

    @Override
    public int hashCode() {
        int r = NumberUtils.floatToRawIntBits(circle.radius);
        int x = NumberUtils.floatToRawIntBits(circle.x);
        int y = NumberUtils.floatToRawIntBits(circle.y);
        return r ^ (x << 10 | x >>> 22) ^ (y << 21 | y >>> 11);
    }

    @Override
    public String toString() {
        return "DSGCircle{" +
                "col=" + col +
                ", row=" + row +
                ", kinematic=" + kinematic +
                ", circle=" + circle +
                '}';
    }
}
