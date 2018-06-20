package week7.lab;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball {
    private int x;
    private int y;
    public static final int D = 100;

    public Ball(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g2){
        Ellipse2D.Double e = new Ellipse2D.Double(x, y, D, D);
        g2.fill(e);
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
