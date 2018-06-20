package project.client;

public class Game {
    private static final int SIZE = 6;

    private int[][] board;
    private boolean turn;
    private int cur;

    public Game(){
        board = new int[SIZE][SIZE];
        turn = false;
    }
}
