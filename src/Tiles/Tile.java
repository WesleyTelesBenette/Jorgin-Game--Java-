
package Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Principal.Camera;

public class Tile {

    private BufferedImage sprite;
    private int x, y, SCALE;
    
    public Tile(int x, int y, BufferedImage sprite, int SCALE) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.SCALE = SCALE;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x*9*SCALE - Camera.getX(), y*9*SCALE - Camera.getY(), 9*SCALE, 9*SCALE, null);
    }
}
