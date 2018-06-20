package project.board;

import project.client.ReversiClient;
import project.server.GameController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private int color;
    private GameController game;

    public MainFrame(ReversiClient client){
        super("Reversi");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new FlowLayout());


        BoardComponent boardComponent = new BoardComponent(0, 0, client);
        boardPanel.setSize(new Dimension(boardComponent.getBoardSideLength(), boardComponent.getBoardSideLength()));
        boardPanel.add(boardComponent);

        add(boardPanel);
        setVisible(true);
    }


}
