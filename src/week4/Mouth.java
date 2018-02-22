package week4;
/**
 * mouth object, includes young and old style
 */

import java.awt.*;
import java.awt.geom.Arc2D;

public class Mouth
{

    private Color color = Color.BLACK;
    private final BasicStroke stroke = new BasicStroke(4.0f);

    public static final int T_WIDTH = 40;
    public static final int T_HEIGHT = 20;

    private int width;
    private int height;

    private int start;
    private int extent;

    /**
     * paint young style mouth
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paintYoung(Graphics g, int x, int y)
    {
        width = T_WIDTH + 10;
        height = T_HEIGHT + 10;
        start = 225;
        extent = 115;
        paint(g, x, y - 10);
    }

    /**
     * paint old style mouth
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paintOld(Graphics g, int x, int y)
    {
        width = T_WIDTH;
        height = T_HEIGHT;
        start = 225;
        extent = 90;
        paint(g, x, y);
    }

    /**
     * paint nose
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paint(Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(stroke);
        g2.draw(new Arc2D.Double(x, y, width, height, start, extent, Arc2D.OPEN));
    }
}
