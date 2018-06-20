package project.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ReversiService implements Runnable{

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private GameController game;

    public ReversiService(Socket socket, GameController game){
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        String msg = "";
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            msg = in.next();
            if(getCommand(msg).equals("PLAY")){
                out.print(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeCommand(String command){
        String msg = "";
        int x = 0, y = 0;
        if(command.equals("PLAY")){
            msg = normalize(in.next());
            x = Integer.parseInt(msg.split(",")[0]);
            y = Integer.parseInt(msg.split(",")[1]);
            game.play(x, y);
        }else if(command.equals("")){

        }
    }

    private String getCommand(String str){
        return str.split(" ")[0];
    }

    public static String normalize(String str){
        str = str.replace("[","");
        str = str.replace("]","");
        return str;
    }
}
