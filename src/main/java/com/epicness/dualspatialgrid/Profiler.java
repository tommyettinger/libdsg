package com.epicness.dualspatialgrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

@SuppressWarnings("GDXJavaProfilingCode")
public class Profiler {

    private final GLProfiler profiler;
    public final ProfilerInfo profilerInfo;

    public Profiler() {
        this(new BitmapFont());
    }

    public Profiler(BitmapFont font) {
        profiler = new GLProfiler(Gdx.graphics);
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

    public static class ProfilerInfo {

        public transient BitmapFont font;
        public float x, y;
        public int drawCalls, bindings;

        public ProfilerInfo(BitmapFont font) {
            this.font = font;
            x = 8;
            y = 90;
        }

        public void draw(Batch spriteBatch) {
            font.draw(spriteBatch,
                "FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" +
                    "Draw calls: " + (drawCalls < 0 ? "???\n" : drawCalls + "\n")  +
                    "Texture bindings: " + (bindings < 0 ? "???" : bindings),
                x, y);
        }
    }
}