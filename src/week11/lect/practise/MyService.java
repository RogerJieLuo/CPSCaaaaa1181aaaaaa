package week11.lect.practise;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyService implements Runnable{
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public MyService (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            String msg = "";
            while(true) {
                if(!in.hasNextLine()) return;
                msg = in.nextLine();
                System.out.println("MSG from client: [" + msg + "]");
                out.println("MSG from client: [" + msg + "]");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            in.close();
            out.close();
        }

    }
}
