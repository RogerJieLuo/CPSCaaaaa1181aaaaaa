package project.server;

import project.board.MainFrame;
import java.util.ArrayList;

public class GameController{
    private static final int SIZE = 6;
    private static final int BLACK = 1;
    private static final int WHITE = -1;

    private int[][] board;
    private boolean end;
    private int cur;
    private int moveCount;
    private ArrayList<Integer> valPattern;

    public GameController(){
        board = new int[SIZE][SIZE];
        int tx = (SIZE - 1) / 2;
        board[tx][tx] = BLACK;
        board[tx+1][tx+1] = BLACK;
        board[tx][tx+1] = WHITE;
        board[tx+1][tx] = WHITE;
        end = false;
        cur = 1;
        moveCount = 4;
    }


    /**
     * play stone
     * @param i the stone position x value
     * @param j the stone position y value
     */
    public void play(int i, int j){
        if(end) return;

        if(getColorOnBoard(i, j) != 0) return;
        if(!chkValidMove(i, j)) return;

        setColorOnBoard(i, j);
        updateBoardStone(i, j);

        changePlayer();

        moveCount ++;
        if(moveCount == SIZE * SIZE){
            int win = calScore();
            System.out.println("Winner is: " + win);
            end = true;
        }

        // if there is no valid move
        if(!chkValidMove()){
            System.out.println("The player " + cur + " doesn't have a valid move, skipped");
            changePlayer();
        }
    }

    /**
     * check if there are validated move exist
     * @return true/false
     */
    private boolean chkValidMove(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] != 0) continue;
                if(chkValidMove(i, j)) return true;
            }
        }
        return false;
    }

    /**
     * check if the move is validated
     * @param i index x in the board
     * @param j index y in the board
     * @return true/false
     */
    private boolean chkValidMove(int i, int j){
        valPattern = new ArrayList<>();
        boolean valid = false;
        // move pattern is either 0 - stay, -1 - left or top, 1 - right or bottom
        int[] pattern = new int[]{0, -1, 1};
        int pi, pj;
        for(int m = 0; m < pattern.length; m++){
            for(int n = 0; n < pattern.length; n++){
                pi = pattern[m];
                pj = pattern[n];
                if(pi == 0 && pj == 0) continue;
                if(checkWithPattern(i, j, pi, pj, 0, cur)){
                    valid = true;
                    valPattern.add( pi );
                    valPattern.add( pj );
                }
            }
        }

        return valid;
    }

    /**
     * check if there are other color stones in between via tracking the path followed by the move pattern
     *  example:
     *      pi = 0, pj = 1 => means the tracking path is straight up, will check if there are opponent's
     *      color in between with current color c
     *
     * @param i index x in the board
     * @param j index y in the board
     * @param pi pattern of x move
     * @param pj pattern of y move
     * @param n identify if there are different color before find the same color
     * @param c current player's color
     * @return true/false
     */
    private boolean checkWithPattern(int i, int j, int pi, int pj, int n, int c) {
        i = i + pi;
        j = j + pj;
        if (i < 0 || j < 0 || i > SIZE - 1 || j > SIZE - 1) {
            return false;
        }

        if (board[i][j] == c) {
            return n != 0; // if there are different color in between, n+1
        }else if(board[i][j] == 0){
            return false;
        }else{
            return checkWithPattern(i, j, pi, pj, n+1, c);
        }
    }

    /**
     * update the color in the board that are in between after play a stone
     * @param i index x in the board
     * @param j index y in the board
     */
    private void updateBoardStone(int i, int j){
        int pi, pj;
        for(int m = 0; m < valPattern.size(); m += 2 ){
            pi = valPattern.get(m);
            pj = valPattern.get(m + 1);
            updateStone(i + pi, j + pj, pi, pj);
        }
        // TODO: check if the game is finished - if all the cells are filled
    }

    /**
     * update each stone color before find the same color
     * @param i index x in the board
     * @param j index y in the board
     * @param pi pattern of x move
     * @param pj pattern of y move
     */
    private void updateStone(int i, int j, int pi, int pj){
        if(board[i][j] == cur){
            return;
        }
        board[i][j] = cur;
        updateStone(i + pi, j + pj, pi, pj);
    }

    /**
     * calculate the stone for each color and return the winner
     * @return 1/-1/0 - BLACK/WHITE/TIE
     */
    private int calScore(){
        int cBlack = 0;
        int cWhite = 0;
        for (int i = 0 ;i< board.length;i++){
            for(int j = 0; j< board[i].length; j++){
                if(board[i][j] == BLACK){
                    cBlack++;
                }
                if(board[i][j] == WHITE){
                    cWhite++;
                }
            }
        }

        return Integer.compare(cBlack, cWhite);
    }

    /**
     * return the stone color on the position i, j
     * @param i index x on the board
     * @param j index y on the board
     * @return 0/1/-1
     */
    public int getColorOnBoard(int i, int j){
        return board[i][j];
    }

    /**
     * set stone color
     * @param i index x on the board
     * @param j index y on the board
     */
    private void setColorOnBoard(int i, int j){
        board[i][j] = cur;
    }

    /**
     * return board side length
     * @return side length
     */
    public int getBoardSideSize(){
        return board.length;
    }

    /**
     * switch to another play
     */
    private void changePlayer(){
        cur = 0 - cur;
    }

    /**
     * pass, skip this turn and switch to another player
     */
    public void pass(){
        changePlayer();
    }

}
