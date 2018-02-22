package week4;
/**
 * Eyes objects, includes young and old styld.
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Eyes
{
    public static final int T_WIDTH = 60;
    public static final int T_HEIGHT = 10;
    private int eyeWidth;
    private int eyeHeight;
    private Color color;

    /**
     * the old style of eyes
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paintOld(Graphics g, int x, int y)
    {
        eyeWidth = T_HEIGHT * 2;
        eyeHeight = T_HEIGHT;
        color = Color.GRAY;
        paint(g, x, y);
    }

    /**
     * the young style of eyes
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paintYoung(Graphics g, int x, int y)
    {
        eyeWidth = T_HEIGHT * 2;
        eyeHeight = T_HEIGHT * 2;
        color = Color.BLACK;
        paint(g, x, y);
    }

    /**
     * painting the two eyes
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paint(Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;

        int lx = x;
        int ly = y;
        int rx = x + T_WIDTH * 2 / 3 ;
        int ry = y;

        Ellipse2D.Double e = new Ellipse2D.Double(lx, ly, eyeWidth, eyeHeight);
        g2.setColor(color);
        g2.fill(e);
        g2.setColor(Color.black);
        g2.draw(e);

        e = new Ellipse2D.Double(rx, ry, eyeWidth, eyeHeight);
        g2.setColor(color);
        g2.fill(e);
        g2.setColor(Color.black);
        g2.draw(e);

    }

}
