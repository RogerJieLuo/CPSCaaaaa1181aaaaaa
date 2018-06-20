package week7.assign;
/**
 * object painting pupils
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pupil {

    public static final int DIAMETER = 20;
    private static final int INNER_DIAMETER = 4;

    private int x;
    private int y;


    public Pupil(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * paint pupil
     * @param g graphics class
     */
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D.Double e = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
        g2.setColor(Color.BLACK);
        g2.fill(e);
        e = new Ellipse2D.Double(x + (DIAMETER - INNER_DIAMETER) / 2, y + (DIAMETER - INNER_DIAMETER) / 2,
                INNER_DIAMETER, INNER_DIAMETER);
        g2.setColor(Color.WHITE);
        g2.fill(e);
    }

}
