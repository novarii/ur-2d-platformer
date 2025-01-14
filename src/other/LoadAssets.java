// LoadAssets.java

package other;

import core.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import static other.GameConstants.*;

public class LoadAssets {

    // Get the entity sprite images based on the sprite amount and file name
    public static BufferedImage[] getEntitySprite(int spriteAmount, String fileName) {
        BufferedImage[] img = new BufferedImage[spriteAmount];
        int add = 0;
        String imgState = null;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                if (i == 0) imgState = "";
                if (i == 1) {
                    imgState = "inv_";
                    add = 6;
                }
                InputStream is = LoadAssets.class.getResourceAsStream("/" + fileName + imgState + getActionName(j) + ".png");
                try {
                    img[j + add] = ImageIO.read(is);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return img;
    }

    // Get a general sprite image based on the file name
    public static BufferedImage getGeneralSprite(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadAssets.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    // Get all the level images
    public static BufferedImage[] getAllLevels() {
        URL url = LoadAssets.class.getResource("/lvls");

        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] files = file.listFiles();
        File[] sortedFiles = new File[files.length];


        for (int i = 0; i < sortedFiles.length; i++) {
            for (File fs : files) {
                if (fs.getName().equals("level_" + (i + 1) + ".png")) {
                    sortedFiles[i] = fs;
                    break;
                }
            }
        }

        BufferedImage[] images = new BufferedImage[sortedFiles.length];

        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(sortedFiles[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    // Get the level data from the image
    public static int[][] getLevelData(BufferedImage img) {
        int[][] lvlData = new int[Game.TILE_AS_HEIGHT][Game.TILE_AS_WIDTH];
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 66) {
                    value = 62;
                }
                lvlData[j][i] = value;
            }
        return lvlData;
    }
}