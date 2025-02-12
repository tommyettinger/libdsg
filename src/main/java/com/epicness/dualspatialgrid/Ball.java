package com.epicness.dualspatialgrid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.epicness.dualspatialgrid.dsg.DSGCircle;
import com.epicness.dualspatialgrid.dsg.HasDSGObject;

public class Ball implements HasDSGObject {

    public final Sprite sprite;
    private final DSGCircle circle;

    public Ball(Sprite ballSprite, float size, Color color) {
        sprite = new Sprite(ballSprite);
        sprite.setSize(size, size);
        sprite.setColor(color);

        circle = new DSGCircle(size / 2f);
        circle.translate(size / 2f);
        setTranslationListener(this::translate);
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    private void translate(float xAmount, float yAmount) {
        sprite.translate(xAmount, yAmount);
    }

    @Override
    public DSGCircle getDSGObject() {
        return circle;
    }
}