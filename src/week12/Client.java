package week12;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame implements Runnable, Protocol{

    private JTextField rField;
    private JTextArea restsArea;
    private CalcAreaListener calcAreaListener;

    private Socket socket;
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    private boolean done;

    // process command/request from server
    @Override
    public void run() {
        // not event driven
        try {
            int msg;
            while(!done){

                msg = fromServer.readInt();
                if(msg == AREA){
                    double area = fromServer.readDouble();
                    report(String.format("area = %2.f \n", area));
                }else if( msg == DONE ){
                    done = true;
                    // disable the gui
                    freeze();
                    report("Done");
                }else {
                    throw new IOException("Unknown server message");
                }
            }
        } catch (IOException e) {
            report(e.getCause().getMessage() + "\n");
        } finally {
            closeConnection();
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        if(args.length > 0 ){
            host = args[0];
        }
        new Client(host);
    }

    public Client(String host){
        buildGui();
        openConnection(host);
        if (socket != null && !socket.isClosed()){
            new Thread( this ).start();
        }else{
            report("Cannot connect to host");
        }

    }

    private void buildGui(){
        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGHT = 400;

        final int LOC_X = 800;
        final int LOC_Y = 0;

        rField = new JTextField("1.0");
        calcAreaListener = new CalcAreaListener();
        rField.addActionListener(calcAreaListener);

        restsArea = new JTextArea();

        add(rField);
        add(restsArea);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(LOC_X, LOC_Y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void freeze(){
        rField.setText("No more input allowed");
        rField.setEditable(false);
        rField.setEnabled(false);
        rField.removeActionListener(calcAreaListener);
    }

    private void openConnection(String host){
        try {
            this.socket = new Socket(host, PORT);
            this.fromServer = new DataInputStream(socket.getInputStream());
            this.toServer = new DataOutputStream(socket.getOutputStream());

        } catch (SecurityException e){
            report("Connect is not allowed");
        } catch (UnknownHostException e) {
            report("the ip address is not found.");
        } catch (IOException e){
            report("Can not connect to the server\""+host+"\"");
        }
    }

    private void closeConnection(){
        try{
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void report(String s){
        restsArea.append(s + " \n");
    }

    // send from client to server
    class CalcAreaListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
