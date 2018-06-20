package week12;

// multiclient server

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private JTextField ta;
    public Server(){

        buildTextAreaLog();

        try( ServerSocket serverSocket = new ServerSocket(8888)){
            String nowStr = reportStatsOnServer();
            report("the server, port" +  serverSocket.getLocalPort()  + "started on " + nowStr);
            int numOfClient = 0;
            while(true){
                numOfClient++;
                Socket socket = serverSocket.accept();
                reportStatsOnClient(socket, numOfClient);

                Runnable service = new ComputationService(socket, ta);
                new Thread(service).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reportStatsOnClient(Socket socket, int n){
        InetAddress addr =socket.getInetAddress();
        report("Client " + n + "'s hostname is " + addr.getHostName());
        report("client " +n +"'s IP address is " +addr.getHostAddress());
    }

    private void report(String str){

    }
    private String reportStatsOnServer(){
        return "";
    }

    public static void main(String[] args) {
        new Server();
    }

    private void buildTextAreaLog(){
        ta = new JTextField();
    }
}
