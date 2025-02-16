package com.epicness.dualspatialgrid.verlet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.epicness.dualspatialgrid.dsg.DSGCircle;
import com.epicness.dualspatialgrid.dsg.HasDSGItem;

public class Soldier implements HasVerletCircleAndDSG, HasColor {

    private final VerletCircle verletCircle;
    private final Color color;
    private final DSGCircle dsgCircle;

    public Soldier(float x, float y, float radius) {
        verletCircle = new VerletCircle(x, y, radius);
        color = new Color().fromHsv(MathUtils.random(360f), 1f, 1f);
        dsgCircle = new DSGCircle(radius);
        dsgCircle.setPositionCentered(x, y);
    }

    @Override
    public DSGCircle getDSGItem() {
        return dsgCircle;
    }

    @Override
    public VerletCircle getVerletCircle() {
        return verletCircle;
    }

    @Override
    public Color getColor() {
        return color;
    }
}