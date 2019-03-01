package entity;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import level.Level;
import level.Platform;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Default_Entity {

    private int randomNum = 0;
    private int movementCounter = 0;

    public Enemy(double x, double y, double size) {
        super(x, y);

        setWidth(20);
        setHeight(size);
        setFill(Color.RED);

        setSpeed(150);
    }

    @Override
    public void update(Level level, double elapsedTime) {
        randomMovements();

        updateMovementX(level, elapsedTime);
        updateMovementY(level, elapsedTime);
    }

    private void randomMovements() {
        if (movementCounter % 10 == 0) {
            randomNum = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
        }
        movementCounter++;
        if (randomNum == -1) {
            this.moveLeft();
        } else if (randomNum == 1) {
            this.moveRight();
        } else if (randomNum == 0) {
            this.stall();
        }
    }

    @Override
    public void resolveCollisionVertical(Level level) {
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            blockIfIntersectVertical(bounds);
        }

        Bounds bounds = level.getCharacter().getLayoutBounds();
        blockIfIntersectVertical(bounds);
    }

    @Override
    public void resolveCollisionHorizontal(Level level) {
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            blockIfIntersectHorizontal(bounds);
        }

        Bounds bounds = level.getCharacter().getLayoutBounds();
        blockIfIntersectHorizontal(bounds);
    }
}
