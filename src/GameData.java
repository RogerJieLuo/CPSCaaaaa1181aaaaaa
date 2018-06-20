/**
 * store some game data * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-04-1
 */

import java.util.ArrayList;
import java.util.Arrays;

public class GameData {
    private static final int PLAYER_SIZE = 2;
    private int[][] board;
    private String[] players;
    private ArrayList<ReversiService> attendants;


    public GameData(int i, int j){
        board = new int[i][j];
        players = new String[PLAYER_SIZE];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public ArrayList<ReversiService> getAttendants() {
        return attendants;
    }

    public void setAttendants(ArrayList<ReversiService> attendants) {
        this.attendants = attendants;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "board=" + Arrays.toString(board) +
                ", players=" + Arrays.toString(players) +
                '}';
    }
}
