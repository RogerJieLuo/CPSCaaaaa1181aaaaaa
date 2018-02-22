package week4;
/**
 * image that contains the head and the string.
 *  includes young and old style
 */

import java.awt.*;

public class HeadImg
{

    public static final int T_WIDTH = 150;
    public static final int T_HEIGHT = 250;

    /**
     * paint the old head and draw the string
     * @param g graphics class
     * @param x the position x value
     * @param y the position y value
     */
    public void paintOld(Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        Head head = new Head();
        head.drawOld(g, x, y);
        Label l = new Label();
        l.paint(g,x + T_WIDTH / 2 - 20, y + T_HEIGHT - 10, "Now" );
//        g2.setColor(Color.RED);
//        g2.drawString("Now", x + T_WIDTH / 2 - 20, y + T_HEIGHT - 10);
    }

    /**
     * paint the young head and draw the string
     * @param g graphics class
     * @param x the position x value
     * @param y the position y value
     */
    public void paintYoung(Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        Head head = new Head();
        head.drawYoung(g, x, y);

        Label l = new Label();
        l.paint(g,x + T_WIDTH / 2 - 10, y + T_HEIGHT - 10, "Young" );

//        g2.setColor(Color.RED);
//        g2.drawString("Young", x + T_WIDTH / 2 - 10, y + T_HEIGHT - 10);
    }
}
