package com.epicness.dualspatialgrid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.epicness.dualspatialgrid.dsg.DSGCircle;
import com.epicness.dualspatialgrid.dsg.HasDSGItem;

/**
 * An example class, though still usable in real code, handling a circular DSGItem and its appearance.
 */
public class Ball implements HasDSGItem {

    public final Sprite sprite;
    private final DSGCircle circle;

    public Ball(Sprite ballSprite, float size, Color color) {
        sprite = new Sprite(ballSprite);
        sprite.setSize(size, size);
        sprite.setColor(color);

        circle = new DSGCircle(size / 2f);
        circle.translate(size / 2f);
        setTranslationListener(sprite::translate);
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    public void draw(SpriteBatch spriteBatch, float parentAlpha) {
        sprite.draw(spriteBatch, parentAlpha);
    }

    @Override
    public DSGCircle getDSGItem() {
        return circle;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Ball)) return false;

        Ball ball = (Ball) o;
        return sprite.equals(ball.sprite) && circle.equals(ball.circle);
    }

    @Override
    public int hashCode() {
        int result = sprite.hashCode();
        result = 31 * result + circle.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "sprite.bounds=" + sprite.getBoundingRectangle() +
                ", sprite.texture=" + sprite.getTexture() +
                ", sprite.color=" + sprite.getColor() +
                ", circle=" + circle +
                '}';
    }
}