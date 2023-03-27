package Items;

import java.awt.image.BufferedImage;
import Principal.Entity;
import java.text.DecimalFormat;


public class Item extends Entity {

    private static double flutuar = 0, flut_dir = 1, yy;
    private static DecimalFormat df = new DecimalFormat("#,###.00");

    public Item(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        yy = y;
    }

    public void tick() {
        //Animação de flutuar
        if (df.format(flutuar).equals(df.format(flut_dir*2)) == false) {
            flutuar += (.1 * flut_dir);
        } else {
            flut_dir *= (-1);
        }
        System.out.println(flutuar + " : " + flut_dir + "\n");
        y = (int) (yy + flutuar);
    }
}
