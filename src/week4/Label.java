package week4;
/**
 * label class, paint the string
 */

import java.awt.*;

public class Label
{

    public void paint(Graphics g, int x, int y, String str)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.drawString(str, x, y);
    }
}
