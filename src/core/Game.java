package core;

import entities.Player;
import levels.Level;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private static final int FPS = 120;
    private static final int TICK_RATE = 200;
    private static Game instance;
    private Player player;
    private LevelManager levelManager;
    private GameCompletedScreen gameCompletedScreen;
    private boolean gameCompleted;


    public final static int TILE_DEFAULT_SIZE = 32;
    public final static float GAME_SCALE = 1.5f;
    public final static int TILE_AS_WIDTH = 36;
    public final static int TILE_AS_HEIGHT = 22;
    public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * GAME_SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILE_AS_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILE_AS_HEIGHT;



    public Game() {
        instance = this;
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        start();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(0, 0, (int) (48 * GAME_SCALE), (int) (48 * GAME_SCALE));
        player.setLvlData(levelManager.getLvl().getLvlData());
        Point spawnPoint = levelManager.getLvl().getPlayerSpawn();
        player.setSpawn(spawnPoint);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (!gameCompleted) {
            levelManager.update();
            player.update();
            if (levelManager.getLvl().isLevelCompleted(player.getHitbox())) {
                levelManager.getLvl().stopTimer(); // Stop the timer for the current level
                if (levelManager.getLvlIndex() == levelManager.getLevels().size() - 1) {
                    // Game completed, handle end game logic
                    gameCompleted = true;
                    int totalScore = calculateTotalScore();
                    gameCompletedScreen = new GameCompletedScreen(totalScore);
                    // Perform any necessary actions for game completion
                    return;
                }
                levelManager.loadNextLevel();
                player.setLvlData(levelManager.getLvl().getLvlData());
                Point spawnPoint = levelManager.getLvl().getPlayerSpawn();
                player.setSpawn(spawnPoint);
            }

        }
    }

    private int calculateTotalScore() {
        int totalScore = 0;
        for (Level level : levelManager.getLevels()) {
            totalScore += level.getRemainingTime();
        }
        return totalScore;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public static Game getInstance() {
        return instance;
    }


    public void render(Graphics g) {
        if (!gameCompleted) {
            levelManager.draw(g);
            player.render(g);
            levelManager.getLvl().renderTimer(g); // Render the level timer
            if (levelManager.getLvl().isLevelCompleted(player.getHitbox())) {
                levelManager.loadNextLevel();
                player.setLvlData(levelManager.getLvl().getLvlData());
                Point spawnPoint = levelManager.getLvl().getPlayerSpawn();
                player.setSpawn(spawnPoint);
            }
        } else {
            gameCompletedScreen.render(g);
        }
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double tickInterval = 1_000_000_000.0 / TICK_RATE;
        double renderInterval = 1_000_000_000.0 / FPS; // we use nanoseconds (1E-9 s) since it is the most precise value available in java.
        double deltaTick = 0;
        double deltaRender = 0;
        long timer = System.currentTimeMillis();
        int tickCount = 0;
        int frameCount = 0;

        while (true) {
            long now = System.nanoTime();
            deltaTick += (now - lastTime) / tickInterval;
            deltaRender += (now - lastTime) / renderInterval;
            lastTime = now;

            if (deltaTick >= 1) {
                update();
                tickCount++;
                deltaTick--;
            }

            if (deltaRender >= 1) {
                gamePanel.repaint();
                frameCount++;
                deltaRender--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                System.out.println("FPS: " + frameCount + ", Ticks: " + tickCount);
                frameCount = 0;
                tickCount = 0;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}