package com.epicness.dualspatialgrid.logic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.epicness.dualspatialgrid.Ball;
import com.epicness.dualspatialgrid.DualSpatialGridDemo;
import com.epicness.dualspatialgrid.PolyBall;

import static com.badlogic.gdx.graphics.Color.SALMON;
import static com.epicness.dualspatialgrid.Constants.*;

public class Logic {

    public final BallMover ballMover;
    public final CollisionResolver collisionResolver;

    private final Sprite circle;

    private final Array<Ball> balls;
    private final Array<PolyBall> polyBalls;


    public Logic(DualSpatialGridDemo demo) {
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
        Ball ball = new Ball(circle, BALL_SIZE, SALMON);
        ball.getDSGObject().setX(MathUtils.random(GRID_X + BALL_SIZE, GRID_X + EFFECTIVE_WIDTH - BALL_SIZE));
        ball.getDSGObject().setY(MathUtils.random(GRID_Y + BALL_SIZE, GRID_Y + EFFECTIVE_HEIGHT - BALL_SIZE));
        balls.add(ball);
    }

    public void spawnPolyBalls() {
        for (int i = 0; i < 50; i++) {
            spawnPolyBall();
        }
        System.out.println("#Poly balls: " + polyBalls.size);
    }

    private void spawnPolyBall() {
        PolyBall ball = new PolyBall(circle, BALL_SIZE, SALMON);
        ball.getDSGObject().setX(MathUtils.random(GRID_X + BALL_SIZE, GRID_X + EFFECTIVE_WIDTH - BALL_SIZE));
        ball.getDSGObject().setY(MathUtils.random(GRID_Y + BALL_SIZE, GRID_Y + EFFECTIVE_HEIGHT - BALL_SIZE));
        polyBalls.add(ball);
    }

    public void update(float delta) {
        ballMover.moveBalls(delta);
        collisionResolver.resolveAllCollisions();
    }
}