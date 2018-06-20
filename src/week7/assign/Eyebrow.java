package week7.assign;
/**
 * object painting eyebrow
 * @author Jie Luo
 * @version 1.0 2018-02-28
 *
 */

import java.awt.*;

public class Eyebrow
{
    private int xl1, xl2, xl3, xl4;
    private int xr1, xr2, xr3, xr4;
    private int yl1, yl2, yl3, yl4;
    private int yr1, yr2, yr3, yr4;

    public static final int T_WIDTH = 180;
    public static final int T_HEIGHT = 35;
    private static final int EYEBROW_THICKNESS = 5;

    private int eyebrowWidth;
    private int moveSpeed;
    private int x;
    private int y;

    public Eyebrow(int x, int y)
    {
        eyebrowWidth = Eyes.T_HEIGHT * 3;
        moveSpeed = -1;
        this.x = x;
        this.y = y;

        xl1 = x;
        xl2 = xl1 + eyebrowWidth;
        xl3 = xl2;
        xl4 = xl1;

        yl1 = y + (T_HEIGHT - EYEBROW_THICKNESS) / 2;
        yl2 = y + (T_HEIGHT - EYEBROW_THICKNESS);
        yl3 = yl2 + EYEBROW_THICKNESS;
        yl4 = yl1 + EYEBROW_THICKNESS;

        xr1 = x + T_WIDTH * 2 / 3 ;
        xr2 = xr1 + eyebrowWidth;
        xr3 = xr2;
        xr4 = xr1;

        yr1 = y + (T_HEIGHT - EYEBROW_THICKNESS);
        yr2 = y + (T_HEIGHT - EYEBROW_THICKNESS) / 2;
        yr3 = yr2 + EYEBROW_THICKNESS;
        yr4 = yr1 + EYEBROW_THICKNESS;
    }

    /**
     * paint 2 eyebrows
     * @param g graphics class
     */
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        Polygon polygon = new Polygon(new int[]{xl1, xl2, xl3, xl4}, new int[]{yl1, yl2, yl3, yl4}, 4);
        g2.fill(polygon);

        polygon = new Polygon(new int[]{xr1, xr2, xr3, xr4}, new int[]{yr1, yr2, yr3, yr4}, 4);
        g2.fill(polygon);

    }

    /**
     * action twitch eyebrow
     */
    public void twitch()
    {
        if(yl2 + moveSpeed < y)
            moveSpeed = 1;
        if(yl3 + moveSpeed > (y + T_HEIGHT))
            moveSpeed = -1;
        yl2 += moveSpeed;
        yl3 += moveSpeed;

        yr1 += moveSpeed;
        yr4 += moveSpeed;
    }

}
