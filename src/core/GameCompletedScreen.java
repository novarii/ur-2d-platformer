package core;

import core.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameCompletedScreen {
    private int totalScore;

    public GameCompletedScreen(int totalScore) {
        this.totalScore = totalScore;
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String text = "Game Completed!";
        int textWidth = g.getFontMetrics().stringWidth(text);
        int textX = (Game.GAME_WIDTH - textWidth) / 2;
        int textY = Game.GAME_HEIGHT / 2 - 50;
        g.drawString(text, textX, textY);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        String scoreText = "Total Score: " + totalScore;
        int scoreTextWidth = g.getFontMetrics().stringWidth(scoreText);
        int scoreTextX = (Game.GAME_WIDTH - scoreTextWidth) / 2;
        int scoreTextY = Game.GAME_HEIGHT / 2 + 20;
        g.drawString(scoreText, scoreTextX, scoreTextY);
    }
}