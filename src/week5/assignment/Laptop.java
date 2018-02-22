package week5.assignment;
/**
 * inherits from Computer class,
 * Drawing a picture of Laptop.
 * Since there is no requirement about the dynamic size, all the data are static
 *
 * @author Jie Luo
 * @version 1.0 2018-02-12
 */

import java.awt.*;

public class Laptop extends Computer
{
    public static int WIDTH = 140;
    public static int HEIGHT = 80;

    public Laptop(int px, int py, Color color)
    {
        super(px, py, WIDTH, HEIGHT, color);
    }

    /**
     * override the abstract function from Computer class
     * @param g2 Graphisc2D class
     */
    public void draw(Graphics2D g2)
    {
//        drawBorder(g2);
        int x = getPx();
        int y = getPy();
        Color color = getColor();

        // top
        int tx = x + 20, ty = y;
        int tw = 100, th =60;
        Rectangle rec = new Rectangle(tx, ty, tw, th);
        g2.setColor(color);
        g2.fill(rec);
        g2.setColor(Color.BLACK);
        g2.draw(rec);

        // screen
        int sx = tx + 5, sy = ty + 5;
        int sw = 90, sh = 50;
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle(sx, sy, sw, sh));


        // bottom
        int pLen = 4;
        int[] xs = {x + 20, x + 120, x + WIDTH, x};
        int[] ys = {y + 60, y + 60, y + HEIGHT, y + HEIGHT};
        Polygon polygon = new Polygon(xs, ys, pLen);
        g2.setColor(color);
        g2.fill(polygon);
        g2.setColor(Color.BLACK);
        g2.draw(polygon);

        // keyboard
        int[] xs2 = {x + 28, x + 112, x + 123, x + 17};
        int[] ys2 = {y + 63, y + 63, y + HEIGHT - 5, y + HEIGHT - 5};
        polygon = new Polygon(xs2, ys2, pLen);
        g2.setColor(Color.GRAY);
        g2.fill(polygon);
    }

    @Override
    public String toString()
    {
        return super.toString() +
                "\nType: " + getClass().getName();
    }
}
