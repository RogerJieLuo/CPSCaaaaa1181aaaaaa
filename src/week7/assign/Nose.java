package week7.assign;
/**
 * nose object, painting nose
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Arc2D;

public class Nose
{

    private final BasicStroke stroke = new BasicStroke(3.0f);

    public static final int T_WIDTH =40;
    public static final int T_HEIGHT =40;

    private int width;
    private int height;
    private Color color;

    private final int heightYoung = T_HEIGHT;
    private final int heightOld = 10;

    private static final int CHANGE_BUFF = 5;
    private int moveSpeed;
    private int x;
    private int y;
    private int oX;
    private int oY;

    public Nose(int x, int y)
    {
        this.height = heightYoung;
        this.width = T_WIDTH;
        this.color = Color.BLACK;

        moveSpeed = -1;
        this.x = x;
        this.y = y;
        oX = x;
        oY = y;
    }

    /**
     * paint young style nose
     * @param g graphics class
     */
    public void paintYoung(Graphics g)
    {
        this.height = heightYoung;
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

    /**
     * action nose twitch
     */
    public void twitch()
    {
        if( x + moveSpeed > oX + CHANGE_BUFF )
        {
            moveSpeed = -1;
        }
        if( x + moveSpeed < oX - CHANGE_BUFF)
        {
            moveSpeed = 1;
        }

        x += moveSpeed;

        width -= 2 * moveSpeed;
    }

}
