package week7.practice;

import javax.swing.*;

public class BallView {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BallComponent ballComponent = new BallComponent(1, 1);
        frame.add(ballComponent);
        frame.setVisible(true);
    }
}
