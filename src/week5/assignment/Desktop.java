package week5.assignment;
/**
 * inherits from Computer class,
 * Drawing a picture of Desktop.
 * Since there is no requirement about the dynamic size, all the data are static
 *
 * @author Jie Luo
 * @version 1.0 2018-02-12
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Desktop extends Computer
{
    public static int WIDTH = 200;
    public static int HEIGHT = 140;

    public Desktop(int px, int py, Color color)
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

        // monitor x, y, width, height
        int mx = getPx();
        int my = getPy();
        Color color = getColor();

        int mw = 120, mh = 100;
        Rectangle rec = new Rectangle(mx, my, mw, mh);
        g2.setColor(color);
        g2.fill(rec);
        g2.setColor(Color.BLACK);
        g2.draw(rec);


        // screen
        int sx = mx + 10, sy = my + 10;
        int sw = 100, sh = 80;
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle(sx, sy, sw, sh));

        //based
        int bx = mx + 55, by = my + 100;
        int bw = 10, bh = 35;
        rec = new Rectangle(bx, by, bw, bh);
        g2.setColor(color);
        g2.fill(rec);
        g2.setColor(Color.BLACK);
        g2.draw(rec);

        int pLen = 4;
        int[] xs = {mx + 55, mx + 65, mx + 100, mx + 20};
        int[] ys = {my + 125, my + 125, my + 140, my + 140};
        Polygon polygon = new Polygon(xs, ys, pLen);
        g2.setColor(color);
        g2.fill(polygon);
        g2.setColor(Color.BLACK);
        g2.draw(polygon);


        // case
        int cx = mx + 140, cy = my;
        int cw = 60, ch = 140;
        g2.setColor(color);
        rec = new Rectangle(cx, cy, cw, ch);
        g2.fill(rec);
        g2.setColor(Color.BLACK);
        g2.draw(rec);

        int dx = cx + 5, dy = cy + 5;
        int dw = 50, dh = 8;
        g2.setColor(Color.WHITE);
        g2.draw(new Rectangle(dx, dy, dw, dh));

        // button
        int btx = dx, bty = my + HEIGHT - 30;
        int btw = 10, bth = 10;
        Ellipse2D.Double e = new Ellipse2D.Double(btx, bty, btw, bth);
        g2.setColor(Color.WHITE);
        g2.draw(e);

    }

    @Override
    public String toString()
    {
        return super.toString() +
                "\nType: " + getClass().getName();
    }

}
