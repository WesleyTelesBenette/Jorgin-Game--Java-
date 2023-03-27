package Principal;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public int W = 0, A = 0, S = 0, D = 0, movX = 0, movY = 0;
    private double vel = 3;
    private static BufferedImage[] sprites;
    private static int frame;
    private static int dir;

    public Player(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite); //Manda para o retangulo de colisão, as possições do jogador
        sprites = new BufferedImage[12]; //Lista de sprites do jogador
        //Coletar e armazenar todos os sprites
        for (int c = 0; c < 12;c++) {
            if (c >= 10) {
                sprites[c] = SpriteSheet.getSprite(SpriteSheet.spriteSheet, 0+((c-10)*9), 24, width, height);
            } else {
                if (c >= 5) {
                    sprites[c] = SpriteSheet.getSprite(SpriteSheet.spriteSheet, 0+((c-5)*9), 12, width, height);
                } else {
                    sprites[c] = SpriteSheet.getSprite(SpriteSheet.spriteSheet, 0+(c*9), 0, width, height);
                }
            }
        }
    }

    //Lógica
    public void tick() {
        //Movimentação
        movX = (D - A);
        movY = (S - W);
        if ((movX != 0)) {
            x += (movX * vel);
        }
        if ((movY != 0)) {
            y += (movY * vel);
        }

        if ((movX != 0)) {
            frame++;
        } else {
            frame = 0;
        }
    }
    
    //Gráficos
    public void render(Graphics g) {
        if (movX != 0) { //Andando
            if (frame >= 20) {
                frame = 0;
            }
            dir = movX;
            g.drawImage(sprites[(int)frame/10+(5*(((movX*(-1))+1)/2))], getX(), getY(), getWidth()*Game.SCALE, getHeight()*Game.SCALE, null);
        } else {
            g.drawImage(sprites[1+(5*(((dir*(-1))+1)/2))], getX(), getY(), getWidth()*Game.SCALE, getHeight()*Game.SCALE, null);
        }
    }
}
