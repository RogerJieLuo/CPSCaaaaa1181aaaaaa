package project.server;

import java.net.ServerSocket;
import java.net.Socket;

public class ReversiServer extends Thread{
    private static int PORT = 8888;
    private GameController game;

    public ReversiServer(){
        game = new GameController();
    }
    @Override
    public void run()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("The server is on... listening to port 8888");

            while (true)
            {
                Socket accept = serverSocket.accept();
                ReversiService service = new ReversiService(accept, game);
                new Thread(service).start();
                System.out.println("Client connected");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ReversiServer().start();
    }
}
