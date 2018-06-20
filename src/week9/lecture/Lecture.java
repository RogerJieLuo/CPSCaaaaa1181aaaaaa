package week9.lecture;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class Lecture {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 800);

        JButton button = new JButton("Click me to give the time");
        button.setToolTipText("Time");  // set placeholder
        button.setFont(new Font("seif", Font.BOLD, 48));

        // anonymous class
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println(e.paramString() + " \n" + LocalDateTime.now());
//            }
//        });
        //lambda expression
        // passing code as parameter
        button.addActionListener(eas ->  System.out.println(eas.paramString() + " \n" + LocalDateTime.now()) );

        frame.add(button);
        frame.setVisible(true);
    }
}
