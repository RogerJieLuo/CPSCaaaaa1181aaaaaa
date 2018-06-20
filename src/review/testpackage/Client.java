package review.testpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    public Client(){
        try {
            socket = new Socket(HOST, PORT);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
