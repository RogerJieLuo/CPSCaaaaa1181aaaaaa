/**
 * Frame that logs the server log
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */

import javax.swing.*;

public class ReversiServerFrame extends JFrame {
    private JTextArea logTextArea;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public ReversiServerFrame(){
        super("Server Log");
        setTitle("Server Log");
        setSize(WIDTH, HEIGHT);

        logTextArea = new JTextArea();
        logTextArea.append("Server Log: \n");

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void log(String msg){
        logTextArea.append(msg + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }
}
