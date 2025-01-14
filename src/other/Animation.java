// Animation.java

package other;

import entities.Player;

public class Animation {
    private int aniTick, aniFrame, aniSpeed;
    private Player player;

    // Constructor for the Animation class
    public Animation(Player player) {
        this.player = player;
        aniTick = 0;
        aniFrame = 0;
        aniSpeed = 30;
    }

    // Update the animation tick and frame based on the player's attacking state
    public void updateAnimationTick(boolean attacking) {
        if (attacking) {
            aniSpeed = 15;
        } else {
            aniSpeed = 30;
        }

        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniFrame++;
            if (aniFrame >= GameConstants.getSpriteAmount(player.getPlayerAction())) {
                aniFrame = 0;
                player.setAttacking(false);
            }
        }
    }

    // Get the current animation frame
    public int getAniFrame() {
        return aniFrame;
    }

    // Reset the animation tick and frame
    public void resetAniTick() {
        aniTick = 0;
        aniFrame = 0;
    }
}