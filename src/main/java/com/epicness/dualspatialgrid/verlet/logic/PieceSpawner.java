package com.epicness.dualspatialgrid.verlet.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.epicness.dualspatialgrid.utils.FloatFloatToObjBiFunction;
import com.epicness.dualspatialgrid.verlet.HasVerletCircleAndDSG;

import java.util.List;

public class PieceSpawner {

    private final List<HasVerletCircleAndDSG> pieces;
    private static final float SPAWN_VARIANCE = 50f;
    private final FloatFloatToObjBiFunction<? extends HasVerletCircleAndDSG> acquire;

    public PieceSpawner(List<HasVerletCircleAndDSG> pieces, FloatFloatToObjBiFunction<HasVerletCircleAndDSG> getPiece) {
        this.pieces = pieces;
        this.acquire = getPiece;
    }
//MathUtils.random(3f, 8f)
    public void spawnPieces(float x, float y) {
        for (int i = 0; i < 100; i++) {
            spawnPiece(
                x + MathUtils.random(-SPAWN_VARIANCE, SPAWN_VARIANCE),
                y + MathUtils.random(-SPAWN_VARIANCE, SPAWN_VARIANCE)
            );
        }
        System.out.println(pieces.size());
    }

    public void spawnPiece(float x, float y) {
        pieces.add(acquire.apply(x, y));
    }
}