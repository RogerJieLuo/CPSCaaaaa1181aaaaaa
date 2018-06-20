package week7.assign;
/**
 * label class, paint the string
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;

public class Label
{
    private int x;
    private int y;

    public Label(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * paint a label
     * @param g graphics class
     * @param str the content be displayed
     */
    public void paint(Graphics g, String str)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.drawString(str, x, y);
    }
}
