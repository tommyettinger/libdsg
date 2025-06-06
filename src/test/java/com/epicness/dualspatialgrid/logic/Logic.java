package com.epicness.dualspatialgrid.logic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.epicness.dualspatialgrid.Ball;
import com.epicness.dualspatialgrid.dsg.DualSpatialGrid;
import com.epicness.dualspatialgrid.Sizing;

import static com.badlogic.gdx.graphics.Color.SALMON;
import static com.epicness.dualspatialgrid.Constants.BALL_SIZE;

public class Logic {

    public final BallMover ballMover;
    public final CollisionResolver collisionResolver;

    private final Sprite circle;

    private final Array<Ball> balls;
    private final Sizing sizing;


    public Logic(Array<Ball> balls, DualSpatialGrid dsg, Sprite circle) {
        ballMover = new BallMover(balls);
        sizing = dsg.sizing;
        collisionResolver = new CollisionResolver(dsg, balls);
        this.circle = circle;
        this.balls = balls;
    }

    public void spawnBalls() {
        for (int i = 0; i < 100; i++) {
            spawnBall();
        }
        System.out.println("#Balls: " + balls.size);
    }

    private void spawnBall() {
        Ball ball = new Ball(circle, BALL_SIZE, SALMON);
        ball.getDSGItem().setX(MathUtils.random(sizing.getOffsetX() + BALL_SIZE, sizing.getOffsetX() + sizing.getEffectiveWidth() - BALL_SIZE));
        ball.getDSGItem().setY(MathUtils.random(sizing.getOffsetY() + BALL_SIZE, sizing.getOffsetY() + sizing.getEffectiveHeight() - BALL_SIZE));
        balls.add(ball);
    }

    public void update(float delta) {
        ballMover.moveBalls(delta);
        collisionResolver.resolveAllCollisions();
    }
}