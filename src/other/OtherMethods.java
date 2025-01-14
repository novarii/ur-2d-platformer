// OtherMethods.java

package other;

import core.Game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class OtherMethods {
    // Get the special points from the image based on the red value
    public static Point GetSpecialPoints(BufferedImage img, int redValue) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value == redValue)
                    return new Point(i * Game.TILE_SIZE, j * Game.TILE_SIZE);
            }
        return new Point(1 * Game.TILE_SIZE, 1 * Game.TILE_SIZE);
    }
}