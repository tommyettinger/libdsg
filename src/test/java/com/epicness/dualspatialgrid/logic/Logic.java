package com.epicness.dualspatialgrid.logic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.epicness.dualspatialgrid.Ball;
import com.epicness.dualspatialgrid.DualSpatialGridDemo;
import com.epicness.dualspatialgrid.PolyBall;
import com.epicness.dualspatialgrid.Sizing;

import static com.badlogic.gdx.graphics.Color.SALMON;

public class Logic {

    public final BallMover ballMover;
    public final CollisionResolver collisionResolver;

    private final Sprite circle;

    private final Array<Ball> balls;
    private final Array<PolyBall> polyBalls;

    private final Sizing sizing;


    public Logic(DualSpatialGridDemo demo) {
        sizing = demo.dualSpatialGrid.sizing;
        ballMover = new BallMover(demo.balls, demo.polyBalls);
        collisionResolver = new CollisionResolver(demo.dualSpatialGrid, demo.balls, demo.polyBalls);
        circle = demo.circle;
        balls = demo.balls;
        polyBalls = demo.polyBalls;
    }

    public void spawnBalls() {
        for (int i = 0; i < 50; i++) {
            spawnBall();
        }
        System.out.println("#Balls: " + balls.size);
    }

    private void spawnBall() {
        Ball ball = new Ball(circle, sizing.getBallSize(), SALMON);
        ball.getDSGObject().setX(MathUtils.random(sizing.getOffsetX() + sizing.getBallSize(),
                sizing.getOffsetX() + sizing.getEffectiveWidth() - sizing.getBallSize()));
        ball.getDSGObject().setY(MathUtils.random(sizing.getOffsetY() + sizing.getBallSize(),
                sizing.getOffsetY() + sizing.getEffectiveHeight() - sizing.getBallSize()));
        balls.add(ball);
    }

    public void spawnPolyBalls() {
        for (int i = 0; i < 50; i++) {
            spawnPolyBall();
        }
        System.out.println("#Poly balls: " + polyBalls.size);
    }

    private void spawnPolyBall() {
        PolyBall ball = new PolyBall(circle, sizing.BALL_SIZE, SALMON);
        ball.getDSGObject().setX(MathUtils.random(sizing.getOffsetX() + sizing.getBallSize(),
                sizing.getOffsetX() + sizing.getEffectiveWidth() - sizing.getBallSize()));
        ball.getDSGObject().setY(MathUtils.random(sizing.getOffsetY() + sizing.getBallSize(),
                sizing.getOffsetY() + sizing.getEffectiveHeight() - sizing.getBallSize()));
        polyBalls.add(ball);
    }

    public void update(float delta) {
        ballMover.moveBalls(delta);
        collisionResolver.resolveAllCollisions();
    }
}