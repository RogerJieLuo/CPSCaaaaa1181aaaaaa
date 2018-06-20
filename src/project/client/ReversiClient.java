package project.client;

import project.board.MainFrame;
import project.server.GameController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ReversiClient {
    private static final int PORT = 8888;
    private static final String HOST = "localhost";

    private static final int SIZE = 6;
    private int[][] board;
    private boolean turn;
    private int cur;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    private int color;

    public ReversiClient(){
        board = new int[SIZE][SIZE];
        MainFrame frame = new MainFrame(this);
        connect();
    }

    public void run() {

        String msg;
        if(!in.hasNextLine()) return;

        {
            msg = in.next();
            switch (msg) {
                case "[ASSIGN]":
                    assignColor(in.next());
                    break;
                case "[PLAY]":

                    break;
                case "[INVALID]":

                    break;
                case "[SKIPPED]":

                    break;

            }
        }
    }

    private void assignColor(String color){
        if(color.equals("BLACK")){
            this.color = 1;
        }
        if(color.equals("WHITE")){
            this.color = -1;
        }
    }

    private void connect(){
        try {
            socket = new Socket(HOST, PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void changeTurn(){

    }

    public void play(int x, int y){
        out.println("[PLAY] ["+x + ","+y+"]");
    }

    public void setCur(int color){
        cur = color;
    }
    /**
     * return board side length
     * @return side length
     */
    public int getBoardSideSize(){
        return board.length;
    }

}
