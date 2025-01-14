// Entity.java

package entities;

import core.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    // Constructor for the Entity class
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Initialize the hitbox for the entity
        this.hitbox = new Rectangle2D.Float(x, y, (float) width, (float) height);
    }

    // Draw the hitbox for debugging purposes
    protected void drawHitbox(Graphics g) {
        g.setColor(Color.ORANGE);
        g.drawRect((int) hitbox.x, (int)  hitbox.y, (int)  hitbox.width, (int)  hitbox.height);
    }

    // Initialize the hitbox with the given coordinates and dimensions
    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    // Getter method for the hitbox
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}