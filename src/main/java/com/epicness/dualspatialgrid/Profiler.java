package com.epicness.dualspatialgrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

@SuppressWarnings("GDXJavaProfilingCode")
public class Profiler {

    private final GLProfiler profiler;
    private final ProfilerInfo profilerInfo;

    public Profiler() {
        profiler = new GLProfiler(Gdx.graphics);
        BitmapFont font = new BitmapFont();
        profilerInfo = new ProfilerInfo(font);
    }

    public void startGLProfiling() {
        profiler.enable();
    }

    public void stopGLProfiling() {
        profiler.disable();
    }

    public void update(float delta) {
        if(profiler.isEnabled()) {
            profilerInfo.drawCalls = profiler.getDrawCalls();
            profilerInfo.bindings = profiler.getTextureBindings();
            profiler.reset();
        }
    }

    public void render(Batch batch) {
        profilerInfo.draw(batch);
    }

    public Profiler setFontColor(Color color) {
        profilerInfo.font.setColor(color);
        return this;
    }

    public void setBallCount(int count) {
        profilerInfo.balls = count;
    }
    public void setPolyBallCount(int count) {
        profilerInfo.polyBalls = count;
    }

    public static class ProfilerInfo {

        transient final BitmapFont font;
        public float x, y;
        public int drawCalls, bindings;
        public int balls, polyBalls;

        public ProfilerInfo(BitmapFont font) {
            this.font = font;
            x = 8;
            y = 90;
        }

        public void draw(Batch spriteBatch) {
            font.draw(spriteBatch,
                "FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" +
                    "Draw calls: " + (drawCalls < 0 ? "???\n" : drawCalls + "\n")  +
                    "Texture bindings: " + (bindings < 0 ? "???\n" : bindings + "\n")  +
                    "Balls: " + balls + "\n" +
                    "PolyBalls: " + polyBalls,
                x, y);
        }
    }
}