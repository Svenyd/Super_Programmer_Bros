package screen;

import javafx.scene.Group;

public class EndScreen extends Group {

    private int screenWidth, screenHeight;

    public EndScreen(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public int getWidth() {
        return screenWidth;
    }

    public int getHeight() {
        return screenHeight;
    }

    public void update(double elapsedTime) {

    }
}
