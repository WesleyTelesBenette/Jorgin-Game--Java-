package Principal;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Tiles.Tile;
import Tiles.TileSolid;

public class World {

    private Tile[] tiles;
    private int WIDTH;
    private int HEIGHT;


    public World(int index) {
        BufferedImage map = SpriteSheet.maps[index]; //Coleta a imagem do mapa
        int[] pixels = new int[map.getWidth() * map.getHeight()]; //Array do tamanho de todos os pixels do mapa
        tiles = new Tile[map.getWidth() * map.getHeight()];
        map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth()); //Cor de todos os pixels na variável "pixels"
        
        WIDTH = map.getWidth();
        HEIGHT = map.getHeight();

        for (int xx = 0; xx < map.getWidth(); xx++) {
            for (int yy = 0; yy < map.getHeight(); yy++) {
                int pixelAtual = pixels[xx + (yy * map.getWidth())];

                //0xFF"Cor em hex"
                if (pixelAtual == 0xFF004C25) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 0, 0, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF00FF80) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 9, 0, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF00E573) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 18, 0, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF006632) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 0, 9, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFFAA4F00) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 9, 9, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF00CC66) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 18, 9, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF007F3F) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 0, 18, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF00994C) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 9, 18, 9, 9), Game.SCALE);
                }
                if (pixelAtual == 0xFF00B259) {
                    tiles[xx + (yy * map.getWidth())] = new TileSolid(xx, yy, SpriteSheet.getSprite(SpriteSheet.tileSet, 18, 18, 9, 9), Game.SCALE);
                }
            }
        }
    }

    public void tick() {

    }

    public void render (Graphics g) {

        int xstart = (Camera.getX() >> 3) / Game.SCALE;
        int ystart = (Camera.getY() >> 3) / Game.SCALE;

        int xfinal = xstart + ((Game.WIDTH >> 3) / Game.SCALE);
        int yfinal = ystart + ((Game.HEIGHT >> 3) / Game.SCALE);

        for (int xx = xstart; xx <= xfinal; xx++) {
            for (int yy = ystart; yy <= yfinal; yy++) {
                if ((xx < 0) || (yy < 0) || (xx >= WIDTH) || (yy >= HEIGHT)) {
                    continue;
                }
                
                Tile tile = tiles[xx + (yy * WIDTH)];
                if (tile != null) tile.render(g);
            }
        }
    }

}
