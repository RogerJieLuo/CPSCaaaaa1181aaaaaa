package week7.lecture;

import javax.swing.JFrame;
import javax.swing.JButton;
// awt, abstract window tool kit
import java.awt.event.ActionListener;

public class ButtonListener {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("Click to end program");

        // for clarity of the code, we put ActionListener object type
        ActionListener buttonEar = new EndingListener();
        button.addActionListener(buttonEar);

        frame.add(button);
        frame.setVisible(true);
    }
}
