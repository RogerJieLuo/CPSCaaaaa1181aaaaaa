/*
 * Copyright (c) 2018.
 * $name
 *
 */

package review.testpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private static final int PORT = 8888;
    private ServerSocket serverSocket;

    public Server(){
        try {
            serverSocket = new ServerSocket(PORT);

            while(true){
                Socket socket = serverSocket.accept();
                Service service = new Service(socket);
                new Thread(service).run();

            }


        }catch (IOException e){

        }
    }

}
