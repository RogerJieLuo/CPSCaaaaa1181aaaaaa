package week7.lab;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DiscBouncer {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;
//    public static BallComponent component;

    public static void main(String[] args) {
        final int MILLISECS = 10;
        BallComponent component = new BallComponent();
        component.addMouseListener(new ClickeListener(component));
//        class MoveListener implements ActionListener {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                component.move();
//            }
//        }
//        Timer t = new Timer(10, new MoveListener());
//        t.start();


        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("bouncing disc");

        frame.add(component);
        frame.setVisible(true);
    }

    private static class ClickeListener implements MouseListener{

        private BallComponent component ;

        public ClickeListener(BallComponent component)
        {
            this.component = component;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            component.moveTo(e.getX(), e.getY());
            component.switchColor();
            component.repaint();
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
}
