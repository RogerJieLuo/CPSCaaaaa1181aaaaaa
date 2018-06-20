package week7.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BallClickComponent extends JComponent implements MouseListener{
    private int x;
    private int y;

    private int bx;
    private int by;

    private Ball ball;

    public BallClickComponent(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.bx = 0;
        this.by = 0;
        ball = new Ball(bx, by);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        ball.paint(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ball.setX(e.getX());
        ball.setY(e.getY());
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
