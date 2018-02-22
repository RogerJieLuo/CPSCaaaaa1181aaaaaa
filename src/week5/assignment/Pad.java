package week5.assignment;
/**
 * inherits from Computer class,
 * Drawing a picture of Pad.
 * Since there is no requirement about the dynamic size, all the data are static
 *
 * @author Jie Luo
 * @version 1.0 2018-02-12
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pad extends Computer
{
    public static int WIDTH = 70;
    public static int HEIGHT = 50;

    public Pad(int px, int py, Color color)
    {
        super(px, py, WIDTH, HEIGHT, color);
    }

    /**
     * override the abstract function from Computer class
     * @param g2 Graphisc2D class
     */
    public void draw(Graphics2D g2)
    {
        int x = getPx();
        int y = getPy();
        Color color = getColor();

        // pad
        int px = x, py = y;
        int pw = WIDTH, ph = HEIGHT;
        Rectangle rec = new Rectangle(px, py, pw, ph);
        g2.setColor(color);
        g2.fill(rec);
        g2.setColor(Color.BLACK);
        g2.draw(rec);

        // screen
        int sx = px + 5, sy = py + 3;
        int sw = 58, sh = 45;
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle(sx, sy, sw, sh));

        // camera
        int cx = x + 2, cy = y + 24;
        int cw = 2, ch = 2;
        Ellipse2D.Double e = new Ellipse2D.Double(cx, cy, cw, ch);
        g2.setColor(Color.BLACK);
        g2.fill(e);

        // button
        int bx = x + 66, by = y + 24;
        int bw = 3, bh = 3;
        e = new Ellipse2D.Double(bx, by, bw, bh);
        g2.setColor(Color.BLACK);
        g2.draw(e);

    }

    @Override
    public String toString()
    {
        return super.toString() +
                "\nType: " + getClass().getName();
    }

}
