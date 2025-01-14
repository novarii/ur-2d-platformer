package levels;

import core.Game;
import other.LoadAssets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private final ArrayList<Level> levels;
    //private ArrayList<Spike> spikes;
    private Game game;
    private BufferedImage BGImage;
    private BufferedImage[] levelSprite;
    private int lvlIndex = 0;


    public LevelManager(Game game) {
        this.game = game;
        loadLevelSprite();
        levels = new ArrayList<>();
       // spikes = new ArrayList<>();
        createAllLevels(); // Call createAllLevels() before initializing levels
        loadLevelBG();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("No more levels! Game Completed!");
        }
        Level newLevel = levels.get(lvlIndex);
        newLevel.startTimer(); // Start the timer for the new level
        game.getPlayer().setSpawn(newLevel.getPlayerSpawn());
        game.getPlayer().setLvlData(newLevel.getLvlData());
    }





    private void createAllLevels() {
        BufferedImage[] allLevels = LoadAssets.getAllLevels();
        for (int i = 0; i < allLevels.length; i++) {
            BufferedImage img = allLevels[i];
            Level level = new Level(img, i);
            levels.add(level);
        }
    }


    private void loadLevelSprite() {
        BufferedImage img = LoadAssets.getGeneralSprite("Tileset.png");
        levelSprite = new BufferedImage[72];
        for (int j = 0; j < 9; j++)
            for (int i = 0; i< 8; i++) {
                int index = j * 8 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

    private void loadLevelBG() {
        BGImage = LoadAssets.getGeneralSprite("city_7.png");
    }

    public void draw(Graphics g) {
        g.drawImage(BGImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        for (int j = 0; j < Game.TILE_AS_HEIGHT; j++)
            for (int i = 0; i < Game.TILE_AS_WIDTH; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILE_SIZE * i, Game.TILE_SIZE * j, Game.TILE_SIZE, Game.TILE_SIZE, null);
            }
    }

//    public void loadObjects(Level newLevel) {
//        spikes = newLevel.getSpikes();
//    }
    public void update() {
        levels.get(lvlIndex).update();
    }

    public Level getLvl() {
        if (lvlIndex >= 0 && lvlIndex < levels.size()) {
            return levels.get(lvlIndex);
        } else {
            // Handle the case when the level index is out of bounds
            // You can return a default level or throw an exception
            return null; // or throw an exception
        }
    }
    public int getLvlIndex() {
        return lvlIndex;
    }


    public ArrayList<Level> getLevels() {
        return levels;
    }

//    public ArrayList<Spike> getSpikes() {
//        return spikes;
//    }
}
