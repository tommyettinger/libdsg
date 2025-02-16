package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.epicness.dualspatialgrid.HasColor;

import java.util.List;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line;

public class Renderer<P extends HasVerletCircleAndDSG & HasColor> {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;

    private final List<P> pieces;
    private final Circle circle;

    public Renderer(List<P> pieces, Circle bounds) {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        Gdx.gl.glLineWidth(2f);

        this.pieces = pieces;
        this.circle = bounds;
    }

    public void render() {
        ScreenUtils.clear(BLACK);

        spriteBatch.begin();
        //dualSpatialGrid.draw(spriteBatch);
        spriteBatch.end();

        shapeRenderer.begin();
        drawPieces();
        shapeRenderer.setColor(WHITE);
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        shapeRenderer.end();
    }

    private void drawPieces() {
        shapeRenderer.set(Filled);
        for (int i = 0; i < pieces.size(); i++) {
            P piece = pieces.get(i);
            shapeRenderer.setColor(piece.getColor());
            shapeRenderer.circle(piece.getVerletCircle().currentPos.x, piece.getVerletCircle().currentPos.y, piece.getVerletCircle().radius);
        }
        shapeRenderer.set(Line);
        for (int i = 0; i < pieces.size(); i++) {
            P piece = pieces.get(i);
            shapeRenderer.setColor(WHITE);
            shapeRenderer.circle(piece.getDSGItem().getCenterX(), piece.getDSGItem().getCenterY(), piece.getVerletCircle().radius);
        }
    }
}