package week7.assign;
/**
 * Eyes objects, includes young and old style.
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Eyes
{
    public static final int T_WIDTH = 180;
    public static final int T_HEIGHT = 20;
    private int eyeWidth;
    private int eyeHeight;
    private Color color;
    private Pupil pupil;

    private int moveSpeed = 1;

    private int x;
    private int y;

    // original x and y
    private int oX;
    private int oY;
    // center point of the eyes
    private int cxl;
    private int cyl;
    private int cxr;
    private int cyr;


    // left eyes start point
    private int lx;
    private int ly;
    // right eyes start point
    private int rx;
    private int ry;

    // position of draw pupils
    private int pLeftX;
    private int pLeftY;
    private int pRightX;
    private int pRightY;

    private int R;

    // covers used to block pupils
    private int coverTopH;
    private int coverBottomY;
    private int coverBottomH;

    public Eyes(int x, int y)
    {
        this.x = x;
        this.y = y;
        oX = x;
        oY = y;
        eyeWidth = T_HEIGHT * 3;
        eyeHeight = T_HEIGHT * 2 ;

        lx = x;
        ly = y;
        rx = x + T_WIDTH * 2 / 3 ;
        ry = y;

        pLeftX = lx + (eyeWidth - Pupil.DIAMETER) / 2;
        pLeftY = ly + (eyeHeight - Pupil.DIAMETER) / 2;
        pRightX = rx + (eyeWidth - Pupil.DIAMETER) / 2;
        pRightY = ry + (eyeHeight - Pupil.DIAMETER) / 2;


        cxl = lx + eyeWidth / 2;
        cyl = ly + eyeHeight / 2;
        cxr = rx + eyeWidth / 2;
        cyr = ry + eyeHeight /2 ;
        R = eyeHeight / 2;

        coverTopH = 0;
        coverBottomY = ly + eyeHeight;
        coverBottomH = 0;
    }
    /**
     * the young style of eyes
     * @param g graphics class
     */
    public void paintYoung(Graphics g)
    {
        color = Color.BLACK;
        paint(g);
    }

    /**
     * painting the two eyes
     * @param g graphics class
     */
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        // set the eyes background to white color
        Ellipse2D.Double el = new Ellipse2D.Double(lx, ly, eyeWidth, eyeHeight);
        g2.setColor(Color.WHITE);
        g2.fill(el);

        Ellipse2D.Double er = new Ellipse2D.Double(rx, ry, eyeWidth, eyeHeight);
        g2.setColor(Color.WHITE);
        g2.fill(er);

        // add pupils
        pupil = new Pupil(pLeftX, pLeftY);
        pupil.paint(g);

        pupil = new Pupil(pRightX, pRightY);
        pupil.paint(g);

        // cover is for blink
        Rectangle coverTop = new Rectangle(lx, oY, T_WIDTH, coverTopH);
        Rectangle coverBottom = new Rectangle(lx, coverBottomY, T_WIDTH, coverBottomH);
        g2.setColor(Color.ORANGE);
        g2.fill(coverTop);
        g2.fill(coverBottom);

        // dra eye border
        g2.setColor(new Color(1f, 1f, 1f, 0f));
        g2.fill(el);
        g2.setColor(Color.black);
        g2.draw(el);

        g2.setColor(new Color(0, 0, 0, 0f));
        g2.fill(er);
        g2.setColor(Color.black);
        g2.draw(er);

//        Ellipse2D.Double test = new Ellipse2D.Double(lx, ly, eyeWidth, eyeWidth);
//        g2.setColor(Color.WHITE);
//        g2.draw(test);
    }

    /**
     * action blink eyes
     */
    public void blink()
    {
        if(ly + moveSpeed > oY + T_HEIGHT){
            moveSpeed = -1;
        }
        if( ly + moveSpeed < oY)
        {
            moveSpeed = 1;
        }

        ly += moveSpeed;
        ry += moveSpeed;
        eyeHeight -= 2 * moveSpeed;

        // the cover moves
        coverTopH += moveSpeed;
        coverBottomY -= moveSpeed;
        coverBottomH += moveSpeed;

    }

    /**
     * Ignore this.
     * action look at point (x2, y2) in a circle way
     * @param x2 target position x value
     * @param y2 target position y value
     */
    public void lookAt2(int x2, int y2)
    {

        double[] p = getCenterDiff(cxl, cyl, x2, y2, R);
        pLeftX = (int) p[0] - Pupil.DIAMETER / 2 ;
        pLeftY = (int) p[1] - Pupil.DIAMETER / 2 ;

        p = getCenterDiff(cxr, cyr, x2, y2, R);
        pRightX = (int) p[0] - Pupil.DIAMETER / 2 ;
        pRightY = (int) p[1] - Pupil.DIAMETER / 2 ;
    }

    /**
     * action look at point (x2, y2) in a ellipse way
     * @param x2 target position x value
     * @param y2 target position y value
     */
    public void lookAt(int x2, int y2)
    {
        double[] p = getCenterDiff2(cxl, cyl, x2, y2);
        pLeftX = (int) p[0] - Pupil.DIAMETER / 2 + cxl;
        pLeftY = (int) p[1] - Pupil.DIAMETER / 2 + cyl;

        p = getCenterDiff2(cxr, cyr, x2, y2);
        pRightX = (int) p[0] - Pupil.DIAMETER / 2 + cxr;
        pRightY = (int) p[1] - Pupil.DIAMETER / 2 + cyr;
    }

    /**
     * get the position of the pupil center
     * @param x1 x value of center of eyes
     * @param y1 y value of center of eyes
     * @param x2 x value of the target position
     * @param y2 y value of the target position
     * @param rad the distance of the pupil allowed to reach away from the center of the eye
     * @return the array contains the x and y value of the pupil center
     */
    private double[] getCenterDiff(int x1, int y1, int x2, int y2, int rad)
    {
        double R = Math.sqrt( Math.pow(x1 - x2, 2 ) + Math.pow( y1 - y2 , 2));

        double dx = (x2 - x1) / R * (rad - Pupil.DIAMETER/2) + x1;
        double dy = (y2 - y1) / R * (rad - Pupil.DIAMETER/2) + y1;

        return new double[]{dx, dy};
    }

    /**
     * get the position of the pupil center
     *  modify the ellipse that move it to the origin point, it's easy for calculation,
     *      ellipse: x^2 / a^2 + y ^ 2 / b^2 = 1
     *      line: y = kx;
     *
     *  and add the movement back to the final result in lookAt method
     *
     * @param x1 x value of center of eyes
     * @param y1 y value of center of eyes
     * @param x2 x value of the target position
     * @param y2 y value of the target position
     * @return the array contains the x and y value of intersects
     */
    private double[] getCenterDiff2(int x1, int y1, int x2, int y2)
    {
        // get the modified x and y value
        x2 -= x1;
        y2 -= y1;
        // line is: y = kx + con
        double k = 1.0 * y2 / x2;

        int a = eyeWidth / 2;
        int b = T_HEIGHT;
        double[] interPoint = calFormula(a, b, k, x2, y2);
        double interPx = interPoint[0];
        double interPy = interPoint[1];

        // the distance between the center of ellipse and the center of pupil
        double R1 = Math.sqrt( Math.pow(interPx, 2 ) + Math.pow(interPy , 2));

        double dx = (R1 - Pupil.DIAMETER/2)/R1 * (interPx - 0) ;
        double dy = (R1 - Pupil.DIAMETER/2)/R1 * (interPy - 0) ;
        return new double[]{dx, dy};
    }

    /**
     * use formula to calculate the intersection points.
     *  ellipse: x^2 / a^2 + y ^ 2 / b^2 = 1
     *  line: y = kx;
     *  refactor them to get the equation to find x
     *  there are 2 possible answer2 for x, use getCloserX() method to find right answer
     * @param a a in the formula
     * @param b b in the formula
     * @param k k in the formula
     * @param x2 the click position x value
     * @param y2 the click position y value
     * @return
     */
    private double[] calFormula(int a, int b, double k, int x2, int y2)
    {
        double xFirst = a * b / Math.sqrt((b * b + a * a * k * k ));
        double xSecond = -1 * a * b / Math.sqrt((b * b + a * a * k * k ));
        double xSolution = getCloserX(x2, y2, xFirst, k * xFirst, xSecond, k*xSecond );

        double ySolution = k * xSolution;
        return new double[]{xSolution, ySolution};
    }

    /**
     * find which point is between the origin and the click position (modified click position)
     *  if the distance of possible intersection to the click position is smaller than the
     *  position of click position to origin, then it's the intersection we need.
     * @param x2 click position x
     * @param y2 click position y
     * @param x3 possible intersection1 x value
     * @param y3 possible intersection1 y value
     * @param x4 possible intersection2 x value
     * @param y4 possible intersection2 y value
     * @return
     */
    private double getCloserX(double x2, double y2, double x3, double y3, double x4, double y4)
    {
        double r1 = Math.sqrt( Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2) );
        double r2 = Math.sqrt( Math.pow(x2 - x4, 2) + Math.pow(y2 - y4, 2) );

        if(r1 < r2)
        {
            return x3;
        }
        else
        {
            return x4;
        }
    }

    /**
     * reset pupils to the middle of eyes
     */
    public void resetPupils()
    {
        pLeftX = x + (T_HEIGHT * 3 - Pupil.DIAMETER) / 2;
        pLeftY = y + (T_HEIGHT * 2 - Pupil.DIAMETER) / 2;
        pRightX = x + T_WIDTH * 2 / 3 + (T_HEIGHT * 3 - Pupil.DIAMETER) / 2;
        pRightY = y + (T_HEIGHT * 2 - Pupil.DIAMETER) / 2;
    }

}
