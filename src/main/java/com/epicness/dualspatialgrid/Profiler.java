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
        profiler.enable();
        BitmapFont font = new BitmapFont();
        profilerInfo = new ProfilerInfo(font);
    }

    public void update(float delta) {
        profilerInfo.drawCalls = profiler.getDrawCalls();
        profilerInfo.bindings = profiler.getTextureBindings();
        profiler.reset();
    }

    public void render(Batch batch) {
        profilerInfo.draw(batch);
    }

    public Profiler setFontColor(Color color) {
        profilerInfo.font.setColor(color);
        return this;
    }
    public static class ProfilerInfo {

        transient final BitmapFont font;
        public float x, y;
        public int drawCalls, bindings;

        public ProfilerInfo(BitmapFont font) {
            this.font = font;
            x = 8;
            y = 55;
        }

        public void draw(Batch spriteBatch) {
            font.draw(spriteBatch,
                "FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" +
                    "Draw calls: " + drawCalls + "\n" +
                    "Texture bindings: " + bindings,
                x, y);
        }
    }
}