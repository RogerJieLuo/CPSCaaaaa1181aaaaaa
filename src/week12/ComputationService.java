package week12;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ComputationService implements Runnable, Protocol {

    private Socket socket;
    private JTextField textField;
    private DataInputStream fromClient;
    private DataOutputStream toClient;


    public ComputationService(Socket socket, JTextField ta){
        this.socket = socket;
        this.textField = ta;
    }

    @Override
    public void run() {
        try{
            try{
                fromClient = new DataInputStream(socket.getInputStream());
                toClient = new DataOutputStream(socket.getOutputStream());

                executeCmds();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                socket.close();
            }
        }catch (IOException | NullPointerException e){
            // socket.close() may throw exception
            e.printStackTrace();
            report(e.getCause().getMessage());
        }
    }

    private void executeCmds() throws IOException{
        int cmd;
        do{
            cmd = fromClient.readInt();
            if(cmd == QUIT){
                toClient.writeInt(DONE);
                toClient.flush();
            }else if(cmd == RADIUS){
                // computeAread();
            }else{
                // this block should not be happening
                // the protocol doesn't allow anything else
                // it's just moral for testing here
                System.out.println("There are some other thing breaks protocol: " + cmd);
            }

        }while (cmd != QUIT);

    }

    private void computeArea() throws IOException{
        double r = fromClient.readDouble();
        double area = r * r*Math.PI;

        toClient.writeInt(AREA);
        toClient.writeInt((int) area);
        toClient.flush();
        
    }

    private void report(String msg){
        System.out.println(msg);
    }
}
