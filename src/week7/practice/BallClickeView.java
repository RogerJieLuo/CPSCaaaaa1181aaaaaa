package week7.practice;

import javax.swing.*;

public class BallClickeView {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        BallClickComponent bcc = new BallClickComponent(0,0);
        frame.addMouseListener(bcc);
        frame.add(bcc);
        frame.setVisible(true);
    }
}
