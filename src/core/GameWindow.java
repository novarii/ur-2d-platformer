package core;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

       /* jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // TODO Auto-generated method stub
            }
        }); */

    }
}
