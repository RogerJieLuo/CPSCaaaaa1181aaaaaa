/**
 * Class to open the server and listening to clients
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReversiServer {

    private Room room;
    private ReversiServerFrame serverFrame;

    public ReversiServer() {
        // initiate the room
        serverFrame = new ReversiServerFrame();
        room = new Room(serverFrame);

        // open the server
        try (ServerSocket serverSocket = new ServerSocket(8888)) {

            System.out.println("Server is listening to port 8888...");
            int numOfClient = 1;

            while (true) {
                // accept the client sockets
                Socket socket = serverSocket.accept();

                // create a service for each client
                Runnable service = new ReversiService(socket, room);
                new Thread(service).start();

                System.out.println("Client " + numOfClient + "th is connected");

                numOfClient ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new ReversiServer();
    }
}
