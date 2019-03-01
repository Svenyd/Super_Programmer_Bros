package entity;

import javafx.geometry.Bounds;
import level.Level;
import level.Platform;

public abstract class Collectable extends Default_Entity {

    Collectable(double x, double y) {
        super(x, y);
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

    public abstract void collect(Level level);
}
