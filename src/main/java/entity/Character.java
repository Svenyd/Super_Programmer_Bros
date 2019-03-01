package entity;

import game.Game;
import javafx.geometry.Bounds;
import level.Level;
import level.Platform;

public class Character extends Default_Entity {

    private static final double DEFAULT_WIDTH = 20;
    private static final double DEFAULT_HEIGHT = 75;
    private static final int STARTING_LIVES = 1;

    private int lives;

    public Character(double x, double y) {
        super(x, y);

        lives = STARTING_LIVES;
        setWidth(DEFAULT_WIDTH);
        setHeight(DEFAULT_HEIGHT);
    }

    @Override
    public void resolveCollisionVertical(Level level) {
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            blockIfIntersectVertical(bounds);
        }
        for (Enemy enemy : level.getEnemyList()) {
            Bounds bounds = enemy.getLayoutBounds();
            if (intersects(bounds)) {
                takeDamage(level);
            }
            blockIfIntersectVertical(bounds);
        }
    }

    @Override
    public void resolveCollisionHorizontal(Level level) {
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            blockIfIntersectHorizontal(bounds);
        }
        for (Enemy enemy : level.getEnemyList()) {
            Bounds bounds = enemy.getLayoutBounds();
            if (intersects(bounds)) {
                takeDamage(level);
            }
            blockIfIntersectHorizontal(bounds);
        }
        for (Collectable collectable : level.getCollectableList()) {
            Bounds bounds = collectable.getLayoutBounds();
            if (intersects(bounds)) {
                javafx.application.Platform.runLater(() -> {
                    level.getChildren().remove(collectable);
                    level.getCollectableList().remove(collectable);
                    collectable.collect(level);
                });
            }
        }
    }
    

    private void takeDamage(Level level) {
        this.lives--;
        if (this.lives <= 0) {
            Game.getInstance().skipToLevel(level.getLevelNumber());
        }
    }
}
