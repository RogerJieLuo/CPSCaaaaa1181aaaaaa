package week7.assign;
/**
 * Object painting of ear
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ear {
    private static final int T_WIDTH = 50;
    private static final int T_HEIGHT = 100;

    private static final int CHANGE_BUFF = 10;
    private int moveSpeed = 1;
    private int innerWidth = 20;
    private int innerHeight = 40;
    private int outterWidth;
    private int outterHeight;

    private int x;
    private int y;
    private int oX;
    private int oY;

    public Ear(int x, int y){
        this.x = x;
        this.y = y;
        oX = x;
        oY = y;
        outterWidth = T_WIDTH;
        outterHeight = T_HEIGHT;
    }

    /**
     * paint ear
     * @param g graphics class
     */
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D.Double e = new Ellipse2D.Double(x, y, outterWidth, outterHeight);
        g2.setColor(Color.ORANGE);
        g2.fill(e);
        g2.setColor(Color.BLACK);
        g2.draw(e);

        e = new Ellipse2D.Double(x + 15, y + 30, innerWidth, innerHeight);
        g2.setColor(Color.WHITE);
        g2.fill(e);
        g2.setColor(Color.BLACK);
        g2.draw(e);
    }

    /**
     * action flap ear
     */
    public void flap()
    {
        if(x + moveSpeed > oX + CHANGE_BUFF)
        {
            moveSpeed = -1;
        }
        if(x + moveSpeed < oX )
        {
            moveSpeed = 1;
        }
        x += moveSpeed;
        outterWidth -= 2 * moveSpeed;
        innerWidth -= 2 * moveSpeed;
    }

}
