package Principal;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    
    public static BufferedImage spriteSheet;
    public static BufferedImage tileSet;
    public static BufferedImage[] maps;
    private String path_sprite = "../res/Sprites.png"; //Caminho da folha de sprites que contém todos os gráficos do jogo
    private String path_tile = "../res/Tileset.png"; //Caminho dos tilesets
    private String path_map_01 = "../res/Map_01.png"; //Caminho do mapa_01

    public SpriteSheet() {
        try {
            spriteSheet = ImageIO.read(getClass().getResource(path_sprite)); //Coleta o arquivo de imagem
            tileSet = ImageIO.read(getClass().getResource(path_tile)); //Coleta o arquivo de imagem
            maps = new BufferedImage[1];
            maps[0] = ImageIO.read(getClass().getResource(path_map_01)); //Mapa base
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getSprite(BufferedImage sprite, int x, int y, int width, int height) {
        return sprite.getSubimage(x, y, width, height); //Retorna o sprite
    }
}
