package week7.assign;
/**
 * face object, painting face
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Face
{
    public static final int T_WIDTH = 300;
    public static final int T_HEIGHT = 400;

    private int x;
    private int y;
    private int width;
    private int height;
    public Face(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = T_WIDTH;
        this.height = T_HEIGHT;
    }
    /**
     * young style face
     * @param g graphic class
     * @param width the face width
     * @param height the face height
     */
    public void paintYoung(Graphics g, int width, int height)
    {
        paint(g, x, y, width, height);
    }

    /**
     *
     * @param g graphic class
     * @param x position x value
     * @param y position y value
     * @param width the face width
     * @param height the face height
     */
    public void paint(Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2 = (Graphics2D) g;
        Color color = Color.ORANGE;

        Ellipse2D.Double e = new Ellipse2D.Double(x, y,
                width, height);
        g2.setColor(color);
        g2.fill(e);

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(Color.black);
        g2.draw(e);

    }

}
