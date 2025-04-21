package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.math.MathUtils;
import com.epicness.dualspatialgrid.utils.FloatFloatToObjBiFunction;

import java.util.List;

public class PieceSpawner<P extends HasVerletCircleAndDSG> {

    private final List<P> pieces;
    private static final float SPAWN_VARIANCE = 50f;
    private final FloatFloatToObjBiFunction<P> acquire;

    public PieceSpawner(List<P> pieces, FloatFloatToObjBiFunction<P> getPiece) {
        this.pieces = pieces;
        this.acquire = getPiece;
    }

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