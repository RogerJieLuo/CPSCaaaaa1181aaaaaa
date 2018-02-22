package week4;
/**
 * head object, includes young and old style
 */

import java.awt.*;

public class Head
{
    public static final int T_WIDTH = 150;
    public static final int T_HEIGHT = 200;

    /**
     * paint old style head
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void drawOld(Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        Face face = new Face();
        face.paintOld(g2, x, y, T_WIDTH, T_HEIGHT);

        Eyes eyes = new Eyes();
        eyes.paintOld(g2, x + (T_WIDTH - Eyes.T_WIDTH) / 2, y + Hair.T_HEIGHT / 2 + 3);

        Hair hair = new Hair();
        hair.paintOld(g2, x, y, T_WIDTH);

        Nose nose = new Nose();
        nose.paintOld(g2, x + (T_WIDTH - Nose.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3 - Nose.T_HEIGHT - 10);

        Mouth mouth = new Mouth();
        mouth.paintOld(g2, x + (T_WIDTH - Mouth.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3);
    }

    /**
     * paint young style head
     * @param g graphics class
     * @param x position x value
     * @param y position y value
     */
    public void drawYoung(Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        Face face = new Face();
        face.paintYoung(g2, x, y, T_WIDTH, T_HEIGHT);

        Eyes eyes = new Eyes();
        eyes.paintYoung(g2, x + (T_WIDTH - Eyes.T_WIDTH) / 2, y + Hair.T_HEIGHT / 2 + 3);

        Hair hair = new Hair();
        hair.paintYoung(g2, x, y, T_WIDTH);

        Nose nose = new Nose();
        nose.paintYoung(g2, x + (T_WIDTH - Nose.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3 - Nose.T_HEIGHT - 10);

        Mouth mouth = new Mouth();
        mouth.paintYoung(g2, x + (T_WIDTH - Mouth.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3);
    }
}
