package week4;
/**
 * nose object, includes young and old style
 */

import java.awt.*;
import java.awt.geom.Arc2D;

public class Nose
{

    private final BasicStroke stroke = new BasicStroke(3.0f);

    public static final int T_WIDTH =20;
    public static final int T_HEIGHT =20;

    private int width;
    private int height;
    private Color color;

    private final int heightYoung = T_HEIGHT;
    private final int heightOld = 10;

    public Nose()
    {
        this.height = heightYoung;
        this.width = T_WIDTH;
        this.color = Color.BLACK;
    }

    /**
     * paint young style nose
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paintYoung(Graphics g, int x, int y)
    {
        this.height = heightYoung;
        paint(g, x, y);
    }

    /**
     * paint old style nose
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void paintOld(Graphics g, int x, int y)
    {
        this.height = heightOld;
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

        g2.draw(new Arc2D.Double(x, y, width, height, -30, 240, Arc2D.OPEN));
    }
}
