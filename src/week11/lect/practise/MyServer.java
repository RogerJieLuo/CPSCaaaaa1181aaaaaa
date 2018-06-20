package week11.lect.practise;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server is listening to port 8888...");

            while(true) {
                Socket socket = server.accept();

                MyService service = new MyService(socket);
                new Thread(service).start();
                System.out.println("Connected.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
