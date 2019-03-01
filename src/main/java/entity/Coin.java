package entity;

import game.Game;
import javafx.scene.paint.Color;
import level.Level;

public class Coin extends Collectable {

    public Coin(double x, double y) {
        super(x, y);

        setY(y + 50);
        setWidth(10);
        setHeight(10);
        setFill(Color.GOLD);
    }

    @Override
    public void collect(Level level) {
        Game.getInstance().addScore(10);
    }
}
