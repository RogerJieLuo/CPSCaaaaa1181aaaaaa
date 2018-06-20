package project.board;

import project.client.ReversiClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class BoardComponent extends JComponent {
    private static final int CELL_SIDE = 40;
    private static final int BLACK = 1;
    private static final int WHITE = -1;

    private int x;
    private int y;
    private Color backgroundColor;
    private int r_cell;

//    private GameController game;
    ReversiClient client;
    private boolean enabled;

    public BoardComponent(int x, int y){
        this.x = x;
        this.y = y;
        backgroundColor = Color.YELLOW;
        enabled = false;

        setPreferredSize(new Dimension(r_cell * CELL_SIDE,r_cell * CELL_SIDE));

        addMouseListener(new ClickAction());
    }

    public BoardComponent(int x, int y, ReversiClient client){

        this(x, y);
        this.client = client;

    }

    @Override
    public void paintComponent(Graphics g){
        drawBoard((Graphics2D) g);
    }

    public void drawBoard(Graphics2D g2){
        // Draw background
        g2.setColor(backgroundColor);
        Rectangle background = new Rectangle(x, y, r_cell * CELL_SIDE, r_cell * CELL_SIDE);
        g2.fill(background);

        // draw board lines
        g2.setColor(Color.BLACK);
        for(int i = 0; i <= r_cell; i ++ ){
            g2.drawLine(x + i * CELL_SIDE, y, x + i * CELL_SIDE, y + r_cell * CELL_SIDE);
            g2.drawLine(x, y + i * CELL_SIDE, x + r_cell * CELL_SIDE, y + i * CELL_SIDE );
        }

        // draw stones
        for(int i = 0; i < r_cell; i++){
            for(int j = 0; j < r_cell; j++){
                // TODO: check part should
//                if(game.getColorOnBoard(i, j) == BLACK){
//                    drawStone(g2, i, j, Color.BLACK);
//                }
//                if(game.getColorOnBoard(i, j) == WHITE){
//                    drawStone(g2, i, j, Color.WHITE);
//                }
            }
        }

    }

    private void drawStone(Graphics2D g2, int x_, int y_, Color color){
        Ellipse2D.Double e;
        g2.setColor(color);
        e = new Ellipse2D.Double(x_ * CELL_SIDE + 1, y_ * CELL_SIDE + 1, CELL_SIDE - 2, CELL_SIDE - 2);
        g2.fill(e);
        g2.setColor(Color.BLACK);
        g2.draw(e);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * get the total side length of this the background board
     * @return
     */
    public int getBoardSideLength(){
        return CELL_SIDE * r_cell;
    }

    /**
     * change enabled
     * @param enabled
     */
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    /**
     * click action.
     *  when click, the new stone will be checked and played
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
//            if(!enabled) return;
            int px = e.getX() / CELL_SIDE;
            int py = e.getY() /CELL_SIDE;

            client.play(px, py);

            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
