package week7.assign;
/**
 * Head component used for drawing a completed head picture
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;

public class HeadComponent extends JComponent
{
    private Head head;

    private Timer tEyebrowTwitch;
    private Timer tNoseTwitch;
    private Timer tMouthSmile;
    private Timer tHair;
    private Timer tEar;
    private Timer tEyes;

    private int eyeBrowSpeed;
    private int hairSpeed;

    public HeadComponent()
    {
        head = new Head(0,0);
        addKeyListener(new KeyAction());
        setFocusable(true);

        eyeBrowSpeed = 20;
        hairSpeed = 40;

        tEyebrowTwitch = new Timer(eyeBrowSpeed, new EyebrowTwitchAction());
        new Timer(10, e->{});
        tNoseTwitch = new Timer(40, new NoseTwitchAction());
        tMouthSmile = new Timer(40, new MouthSmileAction());
        tHair = new Timer(hairSpeed, new HairAction());
        tEar = new Timer(40, new EarAction());
        tEyes = new Timer(40, new EyeAction());

        addMouseListener(new ClickAction());

        addComponentListener(new ResizeComponent());
    }

    @Override
    public void paintComponent(Graphics g) {
        head.drawYoung(g);
    }


    // actions

    /**
     * actions corresponding to the key pressed
     */
    class KeyAction implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println(e.getKeyChar());
        }

        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyStroke.getKeyStrokeForEvent(e).toString();
            key = key.replace("pressed ","");

            if(key.equals("1"))
            {
                if(tEyebrowTwitch.isRunning())
                {
                    tEyebrowTwitch.stop();
                }
                else
                {
                    tEyebrowTwitch.start();
                }
            }
            else if(key.equals("2"))
            {
                if(tEar.isRunning())
                {
                    tEar.stop();
                }
                else
                {
                    tEar.start();
                }
            }
            else if(key.equals("3"))
            {
                if(tNoseTwitch.isRunning())
                {
                    tNoseTwitch.stop();
                }
                else
                {
                    tNoseTwitch.start();
                }
            }
            else if(key.equals("4"))
            {
                if(tEyes.isRunning())
                {
                    tEyes.stop();
                }
                else
                {
                    tEyes.start();
                }
            }
            else if(key.equals("5"))
            {
                if(tMouthSmile.isRunning())
                {
                    tMouthSmile.stop();
                }
                else
                {
                    tMouthSmile.start();
                }
            }
            else if(key.equals("6"))
            {
                if(tHair.isRunning())
                {
                    tHair.stop();
                }
                else
                {
                    tHair.start();
                }
            }
            else if(key.equals("LEFT"))
            {
                eyeBrowSpeed -= 1;
                if(tEyebrowTwitch.isRunning())
                    tEyebrowTwitch.stop();
                tEyebrowTwitch = new Timer(eyeBrowSpeed, new EyebrowTwitchAction());
                tEyebrowTwitch.start();
            }
            else if(key.equals("RIGHT"))
            {
                eyeBrowSpeed += 1;
                if(tEyebrowTwitch.isRunning())
                    tEyebrowTwitch.stop();
                tEyebrowTwitch = new Timer(eyeBrowSpeed, new EyebrowTwitchAction());
                tEyebrowTwitch.start();
            }
            else if(key.equals("UP"))
            {
                hairSpeed -= 1;
                if(tHair.isRunning())
                {
                    tHair.stop();
                }
                tHair = new Timer(hairSpeed, new HairAction());
                tHair.start();
            }
            else if(key.equals("DOWN"))
            {
                hairSpeed += 1;
                if(tHair.isRunning())
                {
                    tHair.stop();
                }
                tHair = new Timer(hairSpeed, new HairAction());
                tHair.start();
            }
            else if(key.equals("R"))
            {
                head.resetPupils();
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * action corresponding to the mouse click
     */
    class ClickAction implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            head.lookAt(x, y);
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * action for eyebrow twitch
     */
    private class EyebrowTwitchAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            head.eyebrowTwitch();
            repaint();
        }
    }

    /**
     * action for ear flap
     */
    private class EarAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            head.flapEar();
            repaint();
        }
    }

    /**
     * action for nose twitch
     */
    private class NoseTwitchAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            head.noseTwitch();
            repaint();
        }
    }

    /**
     * action for eye blink
     */
    private class EyeAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            head.blink();
            repaint();
        }
    }

    /**
     * action for smile change
     */
    private class MouthSmileAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            head.changeSmile();
            repaint();
        }
    }

    /**
     * action for hair size change
     */
    private class HairAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            head.changeHair();
            repaint();
        }
    }

    /**
     * customize the event when resize
     */
    class ResizeComponent extends ComponentAdapter{
        public void componentResized(ComponentEvent e) {
            head.setXAndY(e.getComponent().getWidth() / 2 - Head.T_WIDTH / 2,
                            e.getComponent().getHeight() / 2 - Head.T_HEIGHT / 2);
            repaint();
        }
    }

}
