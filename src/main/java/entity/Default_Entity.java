package entity;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import level.Level;
import level.Platform;

public abstract class Default_Entity extends Rectangle {

    private static final double DEFAULT_SPEED = 300;
    private static final double DEFAULT_JUMP_HEIGHT = 700;
    private static final double FALL_ACCELERATION = 2000;

    private boolean isMovingRight, isMovingLeft;
    private boolean canJump;
    private double jumpHeight;
    private double xVelocity;
    private double yVelocity;

    Default_Entity(double x, double y) {
        setX(x);
        setY(y);

        setWidth(10);
        setHeight(10);

        xVelocity = DEFAULT_SPEED;
        jumpHeight = DEFAULT_JUMP_HEIGHT;
    }

    public void update(Level level, double elapsedTime) {
        updateMovementX(level, elapsedTime);
        updateMovementY(level, elapsedTime);
    }

    public void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
    }

    public void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
    }

    public void stall() {
        isMovingLeft = false;
        isMovingRight = false;
    }

    public void jump() {
        if (canJump) {
            yVelocity -= jumpHeight;
        }
        canJump = false;
    }

    void updateMovementX(Level level, double elapsedTime) {
        if (isMovingRight) {
            setX(getX() + xVelocity * elapsedTime);
        }
        if (isMovingLeft) {
            setX(getX() - xVelocity * elapsedTime);
        }
        if (getX() < 0) {
            setX(0);
        }
        resolveCollisionHorizontal(level);
    }

    void updateMovementY(Level level, double elapsedTime) {
        yVelocity += FALL_ACCELERATION * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);

        resolveCollisionVertical(level);
    }

    public void resolveCollisionVertical(Level level) {
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            blockIfIntersectVertical(bounds);
        }
    }

    public void resolveCollisionHorizontal(Level level) {
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            blockIfIntersectHorizontal(bounds);
        }
    }

    void blockIfIntersectVertical(Bounds bounds) {
        if (intersects(bounds)) {
            setY(bounds.getMinY() - getHeight() - 0.01);
            canJump = true;
            yVelocity = 0;
        }
    }

    void blockIfIntersectHorizontal(Bounds bounds) {
        if (intersects(bounds)) {
            if (isMovingRight) {
                setX(bounds.getMinX() - getWidth() - 0.01);
            }
            if (isMovingLeft) {
                setX(bounds.getMaxX() + 0.01);
            }
        }
    }

    void setSpeed(double speed) {
        xVelocity = speed;
    }
}
