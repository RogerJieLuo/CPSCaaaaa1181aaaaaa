package week7.assign;
/**
 * head object, painting head
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import java.awt.*;

public class Head
{
    public static final int T_WIDTH = 300;
    public static final int T_HEIGHT = 400;

    private int x;
    private int y;

    public Face face;
    public Eyebrow eyebrow;
    public Eyes eyes;
    public Hair hair;
    public Nose nose;
    public Mouth mouth;
    public Label label;
    public Ear leftEar;
    public Ear rightEar;


    public Head(int x, int y)
    {
        this.x = x;
        this.y = y;

        face = new Face(x, y);
        eyebrow = new Eyebrow( x + (T_WIDTH - Eyebrow.T_WIDTH) / 2 , y + Hair.T_HEIGHT / 2 - 10);
        eyes = new Eyes( x + (T_WIDTH - Eyes.T_WIDTH) / 2, y + Hair.T_HEIGHT / 2 + 23);
        hair = new Hair(x, y, T_WIDTH);
        nose = new Nose(x + (T_WIDTH - Nose.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3 - Nose.T_HEIGHT - 10);
        mouth = new Mouth(x + (T_WIDTH - Mouth.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3 - 10);
        label = new Label(x + T_WIDTH / 2 - 10, y + T_HEIGHT + 50);
        leftEar = new Ear(x - 25, y + Hair.T_HEIGHT / 2 + 23);
        rightEar = new Ear(x - 25 + T_WIDTH, y + Hair.T_HEIGHT / 2 + 23);
    }
    /**
     * paint young style head
     * @param g graphics class
     */
    public void drawYoung(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        leftEar.paint(g);

        rightEar.paint(g);

        face.paintYoung(g2, T_WIDTH, T_HEIGHT);

        eyebrow.paint(g);

        eyes.paintYoung(g);

        hair.paintYoung(g);

        nose.paintYoung(g);

        mouth.paintYoung(g);

        label.paint(g,"Normal" );

    }

    /**
     * action twitch eyebrow
     */
    public void eyebrowTwitch()
    {
        eyebrow.twitch();
    }

    /**
     * action flap ear
     */
    public void flapEar()
    {
        leftEar.flap();
        rightEar.flap();
    }

    /**
     * action twitch nose
     */
    public void noseTwitch()
    {
        nose.twitch();
    }

    /**
     * action blink
     */
    public void blink()
    {
        eyes.blink();
    }

    /**
     * action change smile size
     */
    public void changeSmile()
    {
        mouth.changeSmmile();
    }

    /**
     * action change hair size
     */
    public void changeHair()
    {
        hair.changeHair();
    }

    /**
     * action pupils look at a direction
     * @param x2 x value of the target position
     * @param y2 y value of the target position
     */
    public void lookAt(int x2, int y2)
    {
        eyes.lookAt(x2, y2);
    }

    /**
     * reset all the drawing to the new position
     * @param x position x value
     * @param y position y value
     */
    public void setXAndY(int x, int y)
    {
        face = new Face(x, y);
        eyebrow = new Eyebrow( x + (T_WIDTH - Eyebrow.T_WIDTH) / 2 , y + Hair.T_HEIGHT / 2 - 10);
        eyes = new Eyes( x + (T_WIDTH - Eyes.T_WIDTH) / 2, y + Hair.T_HEIGHT / 2 + 23);
        hair = new Hair(x, y, T_WIDTH);
        nose = new Nose(x + (T_WIDTH - Nose.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3 - Nose.T_HEIGHT - 10);
        mouth = new Mouth(x + (T_WIDTH - Mouth.T_WIDTH) / 2, y + T_HEIGHT * 2 / 3 - 10);
        label = new Label(x + T_WIDTH / 2 - 10, y + T_HEIGHT + 50);
        leftEar = new Ear(x - 25, y + Hair.T_HEIGHT / 2 + 23);
        rightEar = new Ear(x - 25 + T_WIDTH, y + Hair.T_HEIGHT / 2 + 23);
    }

    /**
     * reset pupils position
     */
    public void resetPupils()
    {
        eyes.resetPupils();
    }
}
