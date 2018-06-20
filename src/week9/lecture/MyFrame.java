package week9.lecture;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JButton btnClick;

    public MyFrame(){
        setTitle("title");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("test");

//        setLayout(new FlowLayout());

//        BorderLayout borderLayout = new BorderLayout();// top, bottom, left, right and center
        // if there are 2 northern position, only second shows

//        add(button, borderLayout.NORTH);
        JTextArea textArea = new JTextArea(30, 20);

        JScrollPane scrollPane = new JScrollPane();

        JRadioButton a = new JRadioButton("a");
        JRadioButton b = new JRadioButton("b");
        ButtonGroup group = new ButtonGroup();
        group.add(a);
        group.add(b);

        if(a.isSelected()){
            // TODO:
        }

        // no group for checkbox
        JCheckBox box1 = new JCheckBox("box1");
        if(box1.isSelected()){

        }

        JComboBox<String> combo = new JComboBox<>();


        GridLayout gridLayout = new GridLayout(2, 3); // 2 rows, 3 cols, it will auto add by order
        setLayout(gridLayout);

        setVisible(true);
    }

    private void createButton(){
        btnClick = new JButton("click");
        // create action listener class and add the listener to the button here

    }

    public static void main(String[] args) {
        new MyFrame();
    }

}
