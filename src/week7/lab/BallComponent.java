package week7.lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BallComponent extends JComponent {
    Ball ball;
    int dx;
    int dy;
    private Color color;

    public BallComponent()
    {
        ball = new Ball(1, 1);
        dx = 1;
        dy = 1;
        color = Color.BLACK;
        addKeyListener(new ArrowKeyListner());
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(color);
        ball.paint(g2);
    }

    public void move(){
        ball.setX(ball.getX() + dx);
        ball.setY(ball.getY() + dy);
        if(ball.getX() >= getWidth() - ball.D){
            dx = -1;
        }
        if(ball.getX() <= 0){
            dx = 1;
        }
        if(ball.getY() >= getHeight() - ball.D){
            dy = -1;
        }
        if(ball.getY() <= 0){
            dy = 1;
        }
        repaint();
    }

    public void switchColor(){
        if(color.equals(Color.BLACK))
            color = Color.YELLOW;
        else
            color = Color.BLACK;
    }

    public void moveTo(int x, int y)
    {
        ball.setX(x);
        ball.setY(y);
    }

    public void moveBy(int x, int y)
    {
        if(ball.getX() + x >= 0 && ball.getX() + x <= getWidth() - ball.D)
            ball.setX(ball.getX() + x);
        if(ball.getY() + y >= 0 && ball.getY() + y <= getHeight() - ball.D)
            ball.setY(ball.getY() + y);

    }

    private class ArrowKeyListner implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyStroke.getKeyStrokeForEvent(e).toString();
            key = key.replace("pressed ","");
            int x = ball.getX();
            int y = ball.getY();
            if(key.equals("DOWN")){
                moveBy(0, 10);
            }else if(key.equals("UP")){
                moveBy(0, -10);
            }else if(key.equals("LEFT")){
                moveBy(-10, 0);
            }else if(key.equals("RIGHT")){
                moveBy(10, 0);
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
