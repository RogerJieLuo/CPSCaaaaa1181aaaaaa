package week7.assign;
/**
 * mouth object painting mouth
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Arc2D;

public class Mouth
{

    private Color color = Color.BLACK;
    private final BasicStroke stroke = new BasicStroke(4.0f);

    public static final int T_WIDTH = 80;
    public static final int T_HEIGHT = 40;

    private int width;
    private int height;

    private int start;
    private int extent;

    private static final int CHANGE_MAX_BUFF = 15;
    private static final int CHANGE_MIN_BUFF = 2;
    private int moveSpeed;
    private int oX;
    private int oY;
    private int x;
    private int y;

    public Mouth(int x, int y)
    {
        this.x = x;
        this.y = y;
        width = T_WIDTH;
        height = T_HEIGHT;

        moveSpeed = -1;
        oX = x;
        oY = y;
    }

    /**
     * paint young style mouth
     * @param g graphics class
     */
    public void paintYoung(Graphics g)
    {
        start = 225;
        extent = 115;
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

    /**
     * action change smile
     */
    public void changeSmmile()
    {
        if( x + moveSpeed > oX + CHANGE_MIN_BUFF )
        {
            moveSpeed = -1;
        }
        if( x + moveSpeed < oX - CHANGE_MAX_BUFF)
        {
            moveSpeed = 1;
        }

        x += moveSpeed;

        width -= 2 * moveSpeed;
    }

}
