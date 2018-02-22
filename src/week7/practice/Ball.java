package week7.practice;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball {
    public static final int DIAMETER = 50;
    private int x;
    private int y;
    private int xt = 1;
    private int yt = 1;

    public Ball(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void paint(Graphics2D g2)
    {
        g2.setColor(Color.BLACK);
        Ellipse2D.Double e = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
        g2.fill(e);
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public int getXt()
    {
        return xt;
    }
    public int getYt()
    {
        return yt;
    }

    public void setXt(int x)
    {
        this.xt = x;
    }
    public void setYt(int y)
    {
        this.yt = y;
    }
}
