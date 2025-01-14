package levels;

import core.Game;
//import objects.Spike;
import other.GameConstants;
import other.LoadAssets;
import other.OtherMethods;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static other.OtherMethods.GetSpecialPoints;

public class Level {

    private final int levelIndex;
    private BufferedImage img;
    private int[][] lvlData;
//    private ArrayList<Spike> spikes;
    private Point playerSpawn;
    private Point levelComplete;

    private long timer;
    private long timeTaken;

    private long startTime;
    private long completionTime;
    private int score;
    private long endTime;
    private long remainingTime;

    public Level(BufferedImage img, int levelIndex) {
        this.img = img;
        this.levelIndex = levelIndex;
        initLvlData();
        //initSpikeData(); // Add this line
        setSpecialPoints();
        timer = GameConstants.LEVEL_TIMERS[levelIndex];
        startTime = 0;
        remainingTime = 0;
    }


    public void startTimer() {
        startTime = System.currentTimeMillis();
        remainingTime = GameConstants.LEVEL_TIMERS[levelIndex];
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }


    public boolean isLevelCompleted(Rectangle2D.Float playerHitbox) {
        Rectangle2D.Float completingRect = new Rectangle2D.Float(levelComplete.x, levelComplete.y, Game.TILE_SIZE, Game.TILE_SIZE);
        return playerHitbox.intersects(completingRect);
    }

    private void setSpecialPoints() {
        playerSpawn = GetSpecialPoints(img, 90);
        Point completingTilePoint = GetSpecialPoints(img, 100);
        if (completingTilePoint != null) {
            levelComplete = new Point(completingTilePoint.x , completingTilePoint.y);
        }


    }

    private void initLvlData() {
        lvlData = LoadAssets.getLevelData(img);
    }

//    private void createSpikes() {
//        spikes = OtherMethods.GetSpikes(img);
//    }
    public void update() {
        if (startTime != 0) {
            remainingTime = (int) ((startTime + GameConstants.LEVEL_TIMERS[levelIndex] * 1000 - System.currentTimeMillis()) / 1000);
            if (remainingTime < 0) {
                remainingTime = 0;
            }
        }
    }
    public void renderTimer(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Time: " + remainingTime, 10, 30);
    }


    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public void stopTimer() {
        remainingTime = (int) ((startTime + GameConstants.LEVEL_TIMERS[levelIndex] * 1000 - System.currentTimeMillis()) / 1000);
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }


    private void calculateRemainingTime() {
        long elapsedTime = (endTime - startTime) / 1000; // Convert milliseconds to seconds
        remainingTime = Math.max(0, timer - (int) elapsedTime);
    }

    public int getRemainingTime() {
        return (int) remainingTime;
    }

//    public ArrayList<Spike> getSpikes() {
//        return spikes;
//    }
    public Point getPlayerSpawn() {
        return playerSpawn;
    }


}
