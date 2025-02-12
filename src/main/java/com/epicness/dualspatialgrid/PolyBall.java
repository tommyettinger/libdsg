package com.epicness.dualspatialgrid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.epicness.dualspatialgrid.dsg.DSGPolygon;
import com.epicness.dualspatialgrid.dsg.HasDSGObject;

/**
 * Balls with polygonal bounds, noticeable less efficient than circle bounds
 */
public class PolyBall implements HasDSGObject {

    public final Sprite sprite;
    private final DSGPolygon polygon;

    public PolyBall(Sprite ballSprite, float size, Color color) {
        sprite = new Sprite(ballSprite);
        sprite.setSize(size, size);
        sprite.setColor(color);

        float[] vertices = new float[12];
        float angle = 0f;
        for (int i = 0; i < vertices.length; i += 2, angle += 360f / (vertices.length / 2f)) {
            vertices[i] = MathUtils.cosDeg(angle) * size / 2f;
            vertices[i + 1] = MathUtils.sinDeg(angle) * size / 2f;
        }
        polygon = new DSGPolygon(vertices);
        polygon.translate(size / 2f);
        setTranslationListener(this::translate);
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    private void translate(float xAmount, float yAmount) {
        sprite.translate(xAmount, yAmount);
    }

    @Override
    public DSGPolygon getDSGObject() {
        return polygon;
    }
}