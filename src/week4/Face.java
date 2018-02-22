package week4;
/**
 * face object, includes young and old style
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Face
{

    /**
     * young style face
     * @param g graphic class
     * @param x position x value
     * @param y position y value
     * @param width the face width
     * @param height the face height
     */
    public void paintYoung(Graphics g, int x, int y, int width, int height)
    {
        paint(g, x, y, width, height);
    }

    /**
     * old style face
     * @param g graphic class
     * @param x position x value
     * @param y position y value
     * @param width the face width
     * @param height the face height
     */
    public void paintOld(Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2 = (Graphics2D) g;
        paint(g, x, y, width, height);

        g2.draw(new Line2D.Double(x + (width - Mouth.T_WIDTH) / 2, y + height * 2 / 3,
                x + (width - Mouth.T_WIDTH) / 2 - 10, y + height * 4 / 5) );
        g2.draw(new Line2D.Double(x + (width + Mouth.T_WIDTH) / 2, y + height * 2 / 3,
                x + (width + Mouth.T_WIDTH) / 2 + 10, y + height * 4 / 5) );
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
