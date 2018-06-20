/*
 * Copyright (c) 2018.
 * $name
 *
 */

package review.testpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Service implements Runnable {
    private DataInputStream fromClient;
    private DataOutputStream toClient;
    private Socket socket;

    public Service(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            fromClient = new DataInputStream(socket.getInputStream());
            toClient = new DataOutputStream(socket.getOutputStream());

            while(true){
                doService();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void doService() throws IOException{

    }

}
