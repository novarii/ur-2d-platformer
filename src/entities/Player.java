// Player.java

package entities;

import core.Game;
import levels.LevelManager;
import other.Animation;
import other.LoadAssets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static core.Game.GAME_SCALE;
import static core.Game.TILE_SIZE;
import static other.Collusion.*;
import static other.GameConstants.*;
import static other.GameConstants.Directions.*;

public class Player extends Entity {

    private LevelManager levelManager;
    private Animation animation;
    private BufferedImage[] img;
    private List<List<BufferedImage>> characterSprites;
    private List<Integer> pressedKeys = new ArrayList<>();
    private int playerAction;
    private int playerDirection;
    private int[][] lvlData;
    private boolean moving, attacking = false;
    private float moveSpeed = 1.5f;
    private float hitboxOffsetX = 40 * GAME_SCALE;
    private float hitboxOffsetY = 27 * GAME_SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.025f * GAME_SCALE;
    private float jumpSpeed = -2.25f * GAME_SCALE;
    private float fallSpeedAfterCollision = 0.5f * GAME_SCALE;
    private boolean inAir = false;
    private boolean jump = false;

    // Constructor for the Player class
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.levelManager = levelManager;
        animation = new Animation(this);
        loadAnimations();
        initHitbox(x, y  ,18*2, 34*2);
    }

    // Update the player's state and position
    public void update() {
        updatePosition();
        animation.updateAnimationTick(attacking);
        setAnimation();
        checkOutOfBounds();
    }

    // Render the player on the screen
    public void render(Graphics g) {
        if (characterSprites != null && playerAction >= 0 && playerAction < characterSprites.size()) {
            List<BufferedImage> sprites = null;
            if (playerDirection == RIGHT) sprites = characterSprites.get(playerAction);
            if (playerDirection == LEFT) sprites = characterSprites.get(playerAction + 6);

            if (animation.getAniFrame() >= 0 && animation.getAniFrame() < sprites.size()) {
                g.drawImage(sprites.get(animation.getAniFrame()), (int)(hitbox.x - hitboxOffsetX), (int)(hitbox.y - hitboxOffsetY), (int) (110), (int) (110), null);
            }
        }
    }

    // Set the player's spawn point
    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y - Game.TILE_SIZE; // Adjust the y-coordinate to be one tile above the spawn point
        hitbox.x = x;
        hitbox.y = y;
    }

    // Set the player's animation based on the current state
    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            playerAction = JUMP;
        }

        if (attacking) {
            playerAction = ATTACK3;
        }

        if (startAni != playerAction) {
            animation.resetAniTick();
        }
    }

    // Set the level data for collision detection
    public void setLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    // Set the attacking state of the player
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    // Get the current player action
    public int getPlayerAction() {
        return playerAction;
    }

    // Set the gravity (for debugging purposes)
    public void setGravity(float g) {
        gravity += g;
        System.out.println("gravity : " + gravity);
    }

    // Get the player's hitbox
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    // Handle key press events
    public void keyPressed(int keyCode) {
        if (!pressedKeys.contains(keyCode)) {
            pressedKeys.add(keyCode);
        }
        if (keyCode == KeyEvent.VK_K) {
            attacking = true;
        }
        if (keyCode == KeyEvent.VK_W) {
            jump = true;
        }
        // For debugging purposes
        if (keyCode == KeyEvent.VK_O) {
            setGravity(0.02f);
        }
        if (keyCode == KeyEvent.VK_P) {
            setGravity(-0.02f);
        }
    }

    // Handle key release events
    public void keyReleased(int keyCode) {
        pressedKeys.remove(Integer.valueOf(keyCode));
        if (keyCode == KeyEvent.VK_W) {
            jump = false;
        }
    }

    // Check if the player is out of bounds and reset the position
    public void checkOutOfBounds() {
        if (hitbox.y + TILE_SIZE * GAME_SCALE > Game.GAME_HEIGHT) {
            Point spawnPoint = Game.getInstance().getLevelManager().getLvl().getPlayerSpawn();
            hitbox.x = spawnPoint.x;
            hitbox.y = spawnPoint.y - 32;
        }
    }

    // Update the player's position based on input and collision detection
    private void updatePosition() {
        moving = false;

        if (jump)
            jump();

        float xSpeed = 0;

        if (pressedKeys.contains(KeyEvent.VK_D)) {
            xSpeed = moveSpeed;
            moving = true;
            if (!(playerDirection == RIGHT)) {
                playerDirection = RIGHT;
                hitboxOffsetX = 12;
            }
        } else if (pressedKeys.contains(KeyEvent.VK_A)) {
            xSpeed = -moveSpeed;
            moving = true;
            if (!(playerDirection == LEFT)) {
                playerDirection = LEFT;
                hitboxOffsetX = 60;
            }
        }

        if (!inAir && !IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }

        if (!inAir)
            if (!canEntityMove(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData))
                inAir = true;

        if (inAir) {
            if (canEntityMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = getEntityYPos(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = xSpeed != 0;
    }

    // Perform the jump action
    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    // Reset the in-air state and air speed
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    // Update the player's x-position based on collision detection
    private void updateXPos(float xSpeed) {
        if (canEntityMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getEntityXPos(hitbox, xSpeed);
        }
    }

    // Load the player's animations
    private void loadAnimations() {
        img = LoadAssets.getEntitySprite(12, "Cyborg_");

        characterSprites = new ArrayList<>();
        for (int i = 0; i < img.length; i++) {
            List<BufferedImage> sprites = new ArrayList<>();
            for (int j = 0; j < getSpriteAmount(i); j++) {
                if (i < 6) {
                    sprites.add(img[i].getSubimage(j * 48, 0, 48, 48));
                } else {
                    sprites.add(img[i].getSubimage((getSpriteAmount(i) * 48) - ((j + 1) * 48), 0, 48, 48));
                }
            }
            characterSprites.add(sprites);
        }
    }
}