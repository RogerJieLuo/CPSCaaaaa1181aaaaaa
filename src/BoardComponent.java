/**
 * Class to create the Reversi Game board and draw stones
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;


public class BoardComponent extends JComponent implements ReversiProtocol{

    private static final int CELL_SIDE = 40;

    private boolean enabled = false;

    private Client client;

    private int[][] board;

    private ClickAction clickAction;

    public BoardComponent(Client client){
        this.client = client;
        setPreferredSize(new Dimension(SIDE * CELL_SIDE,SIDE * CELL_SIDE));

        clickAction = new ClickAction();
        addMouseListener(clickAction);

        initiate();
    }

    /**
     * initiate board
     */
    public void initiate(){
        board = ReversiProtocol.initBoard();
    }

    @Override
    public void paintComponent(Graphics g){
        drawBoard((Graphics2D) g);
    }

    /**
     * draw board and stones
     * @param g2 Graphic2D
     */
    public void drawBoard(Graphics2D g2){
        // Draw background
        g2.setColor(Color.YELLOW);
        Rectangle background = new Rectangle(0,0, SIDE * CELL_SIDE, SIDE * CELL_SIDE);
        g2.fill(background);

        // draw board lines
        g2.setColor(Color.BLACK);
        for(int i = 0; i <= SIDE; i ++ ){
            g2.drawLine(i * CELL_SIDE, 0, i * CELL_SIDE, SIDE * CELL_SIDE);
            g2.drawLine(0,  i * CELL_SIDE, SIDE * CELL_SIDE, i * CELL_SIDE );
        }

        // draw stones
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                // TODO: check part should
                if(board[i][j] == BLACK){
                    drawStone(g2, j, i, Color.BLACK);
                }
                if(board[i][j] == WHITE){
                    drawStone(g2, j, i, Color.WHITE);
                }
            }
        }

    }

    /**
     * draw stones
     * @param g2 Graphics2D
     * @param x_ value x in board
     * @param y_ value y in board
     * @param color color
     */
    private void drawStone(Graphics2D g2, int x_, int y_, Color color){
        Ellipse2D.Double e;
        g2.setColor(color);
        e = new Ellipse2D.Double(x_ * CELL_SIDE + 1, y_ * CELL_SIDE + 1, CELL_SIDE - 2, CELL_SIDE - 2);
        g2.fill(e);
        g2.setColor(Color.BLACK);
        g2.draw(e);
    }

    /**
     * draw black stones, actually change the value in the board
     * @param i value x in board
     * @param j value y in board
     */
    public void drawBlack(int i, int j){
        board[i][j] = BLACK;
        repaint();
    }

    /**
     * draw a list of black points
     * @param points points
     */
    public void drawBlack(String points){
        drawAndSwitch(points, BLACK);
        repaint();
    }

    /**
     * draw white stones, actually change the value in the board
     * @param i value x in board
     * @param j value y in board
     */
    public void drawWhite(int i, int j){
        board[i][j] = WHITE;
        repaint();
    }

    /**
     * draw a list of white points
     * @param points points
     */
    public void drawWhite(String points){
        drawAndSwitch(points, WHITE);
        repaint();
    }

    /**
     * draw a list of points
     * @param points string of points
     * @param color color
     */
    public void drawAndSwitch(String points, int color){
        String[] ps = points.split(POINTS_DELIMITER);
        int i;
        int j;
        for(int m = 0; m < ps.length; m++){
            i = Integer.parseInt(ps[m].split(XY_DELIMITER)[0]);
            j = Integer.parseInt(ps[m].split(XY_DELIMITER)[1]);
            board[i][j] = color;
        }
    }

    /**
     * set if click action enabled
     * @param enabled
     */
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public int[][] getBoard(){
        return board;
    }

    public void setBoard(int[][] board){
        this.board = board;
    }
    /**
     * click action on the board
     */
    class ClickAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!enabled) {
                client.setMsgText("You can't play right now.");
            }else {
                int px = e.getY() / CELL_SIDE;
                int py = e.getX() / CELL_SIDE;
                try {
                    client.play(px, py);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
