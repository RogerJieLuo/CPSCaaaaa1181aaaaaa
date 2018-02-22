package week7.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BallComponent extends JComponent{
    private int x;
    private int y;
    private Ball ball;
    private int speed;

    public BallComponent(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.speed = 1;
        ball = new Ball(this.x, this.y);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        });
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        ball.paint(g2);
    }

    public void move()
    {
        ball.setX(ball.getX() + speed * ball.getXt());
        ball.setY(ball.getY() + speed * ball.getYt());
        if(ball.getX() >= getWidth() - Ball.DIAMETER)
        {
            ball.setXt(-1);
        }
        else if(ball.getY() >= getHeight() - Ball.DIAMETER)
        {
            ball.setYt(-1);
        }
        else if(ball.getX() <= 0)
        {
            ball.setXt(1);
        }
        else if(ball.getY() <= 0)
        {
            ball.setYt(1);
        }
        System.out.println(ball.getX()+" " + ball.getY());
        repaint();
    }

}
