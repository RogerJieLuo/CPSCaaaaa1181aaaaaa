package week11.lect.practise;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static final String HOST = "localhost";
    public static final int PORT = 8888;
    private InputStream inStream;
    private OutputStream outStream;
    private Scanner in;
    private PrintWriter out;

    private Socket socket;

    public MyClient(){
        connect();
    }

    private void connect(){
        try {
            socket = new Socket(HOST, PORT);
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();

            in = new Scanner(inStream);
            out = new PrintWriter(outStream);

            out.println("test.");
            out.flush();

            System.out.println(in.nextLine());

            out.println("test222.");
            out.flush();

            System.out.println(in.nextLine());

            Scanner t = new Scanner(System.in);
            String msg = t.nextLine();
            System.out.println(msg);
            out.println(msg);
            out.flush();
            System.out.println(in.nextLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyClient();
    }
}
