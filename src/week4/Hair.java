package week4;
/**
 * hair object, include young and old style
 */

import java.awt.*;
import java.awt.geom.Arc2D;

public class Hair
{
    public static int T_HEIGHT = 120;
    private Color color;

    /**
     * old style hair
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     * @param width hair width
     */
    public void paintOld(Graphics g, int x, int y, int width)
    {
        color = Color.GRAY;
        paint(g, x, y, width);
    }

    /**
     * young style hair
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     * @param width hair width
     */
    public void paintYoung(Graphics g, int x, int y, int width)
    {
        color = Color.BLACK;
        paint(g, x, y, width);
    }

    /**
     * paint the hair
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     * @param width hair width
     */
    public void paint(Graphics g, int x, int y, int width)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);

        Arc2D.Double a = new Arc2D.Double(x, y, width, T_HEIGHT - 20, 0, 180, Arc2D.OPEN);
        g2.fill(a);

    }

}
