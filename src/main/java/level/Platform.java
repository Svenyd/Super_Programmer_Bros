package level;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {
    private static Color PLATFORM_COLOR = Color.LIGHTGRAY;

    Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
        setFill(PLATFORM_COLOR);
    }
}
