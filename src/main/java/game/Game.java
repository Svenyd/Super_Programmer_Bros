package game;

import entity.Character;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import level.Level;
import screen.EndScreen;
import screen.StartScreen;

import java.util.HashSet;

public class Game {
    private static final int NUM_LEVELS = 1;
    private static final double DEFAULT_SCORE = 100;

    private Scene scene;
    private Group sceneRoot;
    private int sceneWidth, sceneHeight;

    private Character character;
    private StartScreen startScreen;
    private EndScreen endScreen;

    private Level currentLevel;
    private boolean finishedGame;

    private HashSet<KeyCode> pressedKeys;

    private double score;
    private Text txtScore;

    private static Game instance;

    public static Game getInstance() {
        return instance;
    }

    public Game(int width, int height) {
        instance = this;

        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        startScreen = new StartScreen(sceneWidth, sceneHeight);
        endScreen = new EndScreen(sceneWidth, sceneHeight);
        sceneRoot.getChildren().add(startScreen);

        finishedGame = false;

        pressedKeys = new HashSet<KeyCode>();
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    public Scene getScene() {
        return scene;
    }

    public void update(double elapsedTime) {
        if (currentLevel == null) {
            if (!finishedGame) {
                resolveStartOptions();
            } else {
                resolveEndOptions();
                endScreen.update(elapsedTime);
            }
            return;
        }

        resolveKeyPresses();
        updateGame(elapsedTime);
        scrollLevel();
        if (currentLevel != null && character.getX() + character.getWidth() >= currentLevel.getWidth()) {
            advanceLevels();
        }
    }

    private void updateGame(double elapsedTime) {
        if (currentLevel == null) {
            return;
        }
        character.update(currentLevel, elapsedTime);
        currentLevel.update(elapsedTime);
        updateScore(elapsedTime);
    }

    private void updateScore(double elapsedTime) {
        score -= elapsedTime;
        txtScore.setText("Score: " + Math.round(score));
        txtScore.setX(-1 * sceneRoot.getLayoutX() + 10);
    }

    private void resolveStartOptions() {
        if (pressedKeys.contains(KeyCode.SPACE)) {
            advanceLevels();

            showScore();
        }
    }

    private void resolveEndOptions() {
        if (pressedKeys.contains(KeyCode.SPACE)) {
            System.exit(0);
        }
    }

    private void resolveKeyPresses() {
        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            character.moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            character.moveLeft();
        } else {
            character.stall();
        }

        if (pressedKeys.contains(KeyCode.W)) {
            character.jump();
        }
    }

    private void scrollLevel() {
        double offset = sceneWidth * 1.0 / 3;
        if (character.getX() < offset) {
            return;
        }
        sceneRoot.setLayoutX(-1 * character.getX() + offset);
        double minLayout = -1 * currentLevel.getWidth() + sceneWidth;
        if (sceneRoot.getLayoutX() < minLayout) {
            sceneRoot.setLayoutX(minLayout);
        }
    }

    private void advanceLevels() {
        sceneRoot.getChildren().clear();
        sceneRoot.setLayoutX(0);

        int levelNum = 0;
        if (currentLevel != null) {
            levelNum = currentLevel.getLevelNumber();
        }
        if (levelNum == NUM_LEVELS) {
            currentLevel = null;
            finishedGame = true;
            sceneRoot.getChildren().add(endScreen);
            return;
        }

        character = new Character(0, 0);
        currentLevel = new Level(levelNum + 1, sceneHeight, character);

        sceneRoot.getChildren().add(currentLevel);
        sceneRoot.getChildren().add(character);
    }

    public void skipToLevel(int levelNum) {
        sceneRoot.getChildren().clear();
        sceneRoot.setLayoutX(0);
        currentLevel = null;

        if (levelNum <= 0) {
            sceneRoot.getChildren().add(startScreen);
            finishedGame = false;
        } else if (levelNum > NUM_LEVELS) {
            sceneRoot.getChildren().add(endScreen);
            finishedGame = true;
        } else {
            showScore();
            character = new Character(0, 0);
            currentLevel = new Level(levelNum, sceneHeight, character);

            sceneRoot.getChildren().add(currentLevel);
            sceneRoot.getChildren().add(character);
        }
    }

    private void showScore() {
        score = DEFAULT_SCORE;
        txtScore = new Text("Score: " + Math.round(score));
        txtScore.setY(10);
        txtScore.setX(10);
        sceneRoot.getChildren().add(txtScore);
    }

    public void addScore(double score) {
        this.score += score;
    }

}