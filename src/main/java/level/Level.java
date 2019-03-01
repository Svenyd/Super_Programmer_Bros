package level;

import entity.Character;
import entity.Coin;
import entity.Collectable;
import entity.Enemy;
import javafx.scene.Group;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level extends Group {
    private static final String LEVEL_FILE_PREFIX = "levels/level";
    private static final String LEVEL_FILE_POSTFIX = ".txt";
    private static final double PLATFORM_WIDTH_RATIO = 1;

    private static final int FREE_ID = 0;
    private static final int PLATFORM_ID = 1;
    private static final int ENEMY_ID = 2;
    private static final int COIN_ID = 3;

    private int levelNumber;
    private double levelWidth, levelHeight;
    private Character character;
    private List<Platform> platformList;
    private List<Enemy> enemyList;
    private List<Collectable> collectableList;

    public Level(int levelNum, double height, Character character) {
        this.character = character;

        levelNumber = levelNum;
        String filename = LEVEL_FILE_PREFIX + levelNum + LEVEL_FILE_POSTFIX;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        Scanner input = new Scanner(stream);

        platformList = new ArrayList<Platform>();
        enemyList = new ArrayList<Enemy>();
        collectableList = new ArrayList<Collectable>();

        int levelBlockWidth = input.nextInt();
        int levelBlockHeight = input.nextInt();
        levelHeight = height;
        levelWidth = (double) (levelBlockWidth) / levelBlockHeight * levelHeight * PLATFORM_WIDTH_RATIO;
        double blockHeight = levelHeight / levelBlockHeight;
        double blockWidth = blockHeight * PLATFORM_WIDTH_RATIO;

        for (int y = 0; y < levelBlockHeight; y++) {
            for (int x = 0; x < levelBlockWidth; x++) {
                int id = input.nextInt();
                if (id == PLATFORM_ID) {
                    Platform platform = new Platform(x * blockWidth, y * blockHeight, blockWidth, blockHeight);
                    getChildren().addAll(platform);
                    platformList.add(platform);
                } else if (id == ENEMY_ID) {
                    double xpos = (x * blockWidth) + blockWidth / 2.0;
                    double ypos = y * blockHeight;
                    Enemy enemy = new Enemy(xpos, ypos, 75);
                    getChildren().add(enemy);
                    enemyList.add(enemy);
                } else if (id == COIN_ID) {
                    double xpos = (x * blockWidth) + blockWidth / 2.0;
                    double ypos = y * blockHeight;
                    Coin coin = new Coin(xpos, ypos);
                    getChildren().add(coin);
                    collectableList.add(coin);
                }
            }
        }
    }

    public void update(double elapsedTime) {
        for (Enemy enemy: enemyList) {
            enemy.update(this, elapsedTime);
        }
    }

    public double getHeight() {
        return levelHeight;
    }

    public double getWidth() {
        return levelWidth;
    }

    public Character getCharacter() {
        return character;
    }

    public List<Platform> getPlatformList() {
        return platformList;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}