package week7.assign;
/**
 * hair object, include young and old style
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Arc2D;

public class Hair
{
    public static int T_HEIGHT = 240;
    private Color color;

    private static final int CHANGE_MAX_BUFF = 10;
    private static final int CHANGE_MIN_BUFF = 10;
    private int moveSpeed;
    private int oX;
    private int oY;
    private int x;
    private int y;
    private int width;
    private int height;

    public Hair(int x, int y, int width)
    {
        this.x = x;
        this.y = y;
        oX = x;
        oY = y;
        moveSpeed = -1;
        this.width = width;
        height = T_HEIGHT - 20;
    }

    /**
     * young style hair
     * @param g graphics class
     */
    public void paintYoung(Graphics g)
    {
        color = Color.BLACK;
        paint(g);
    }

    /**
     * paint the hair
     * @param g graphics class
     */
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);

        Arc2D.Double a = new Arc2D.Double(x, y, width, height, 0, 180, Arc2D.OPEN);
        g2.fill(a);

    }

    /**
     * action that change hair size
     */
    public void changeHair()
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
        height -= moveSpeed;
    }

}
