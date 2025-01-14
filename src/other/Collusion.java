// Collusion.java

package other;

import core.Game;
//import objects.Spike;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Collusion {

    // Check if the entity can move to the specified position without colliding with solid tiles
    public static boolean canEntityMove(float x, float y, float width, float height, int[][] lvlData) {
        // Get the bounding box of the player's desired position
        float leftTile = x / Game.TILE_SIZE;
        float rightTile = (x + width) / Game.TILE_SIZE;
        float topTile = y / Game.TILE_SIZE;
        float bottomTile = (y + height) / Game.TILE_SIZE;

        // Check if any of the tiles in the bounding box are solid
        for (int i = (int) leftTile; i <= (int) rightTile; i++) {
            for (int j = (int) topTile; j <= (int) bottomTile; j++) {
                if (IsSolid(i * Game.TILE_SIZE, j * Game.TILE_SIZE, lvlData)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if the specified position is a solid tile
    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        int xIndex = (int) (x / Game.TILE_SIZE);
        int yIndex = (int) (y / Game.TILE_SIZE);
        int value = lvlData[yIndex][xIndex];

        if(value != 65 && value != 62) return true;
        return false;
    }

    // Get the Y position of the entity based on its hitbox and air speed
    public static float getEntityYPos(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILE_SIZE;
            int yOffset = (int) (hitbox.height % Game.TILE_SIZE);
            return tileYPos + Game.TILE_SIZE - yOffset - 1;
        } else {
            // Jumping
            return currentTile * Game.TILE_SIZE;
        }
    }

    // Get the X position of the entity based on its hitbox and speed
    public static float getEntityXPos(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILE_SIZE);
        if (xSpeed > 0) {
            // Moving right
            int tileXPos = currentTile * Game.TILE_SIZE;
            int xOffset = (int) (hitbox.width % Game.TILE_SIZE);
            return tileXPos + Game.TILE_SIZE - xOffset - 1;
        } else {
            // Moving left
            return currentTile * Game.TILE_SIZE;
        }
    }

    // Check if the entity is on the floor based on its hitbox and level data
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;
    }
}