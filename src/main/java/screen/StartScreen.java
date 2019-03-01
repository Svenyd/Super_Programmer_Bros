package screen;

import javafx.scene.Group;
import javafx.scene.text.Text;

public class StartScreen extends Group {

    private static final String START_MESSAGE = "Press Space to start the game!";

    public StartScreen(int width, int height) {
        Text startGame = new Text(START_MESSAGE);
        startGame.setX(width / 2 - startGame.getLayoutBounds().getWidth());
        startGame.setY(height / 2 - startGame.getLayoutBounds().getHeight());

        getChildren().add(startGame);
    }
}
