package com.epicness.dualspatialgrid.dsg;

import com.badlogic.gdx.math.Polygon;

public class DSGPolygon extends DSGItem {

    private final Polygon polygon;

    public DSGPolygon(float[] vertices) {
        polygon = new Polygon(vertices);
    }

    @Override
    public float getCenterX() {
        return polygon.getX() + polygon.getBoundingRectangle().width / 2f;
    }

    @Override
    public float getCenterY() {
        return polygon.getY() + polygon.getBoundingRectangle().height / 2f;
    }

    @Override
    public float getX() {
        return polygon.getX();
    }

    @Override
    public void translateX(float amount) {
        polygon.translate(amount, 0f);
        if (translationListener != null) translationListener.accept(amount, 0f);
    }

    @Override
    public float getY() {
        return polygon.getY();
    }

    @Override
    public void translateY(float amount) {
        polygon.translate(0f, amount);
        if (translationListener != null) translationListener.accept(0f, amount);
    }

    @Override
    public void setPositionCentered(float x, float y) {
        setPosition(x - polygon.getBoundingRectangle().width / 2f, y - polygon.getBoundingRectangle().height / 2f);
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
