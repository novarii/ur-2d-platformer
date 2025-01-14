package core;

import inputs.KeyHandler;
import java.awt.*;
import java.sql.SQLOutput;
import javax.swing.*;

import static core.Game.GAME_HEIGHT;
import static core.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private KeyHandler keyHandler;
    private Game game;

    public GamePanel(Game game) {
        keyHandler = new KeyHandler(this);
        this.game = game;

        setPreferredSize();
        addKeyListener(keyHandler); // Add this line
        addMouseListener(keyHandler);
        addMouseMotionListener(keyHandler);
    }

    public void setPreferredSize() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        System.out.println("size : " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);

    }

    public Game getGame() {
        return game;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}