package week11.lect.example;

public class PrintChars implements Runnable{

    private int n;
    private char ch;
    public PrintChars(char charToPrint, int times){
        this.n = times;
        ch = charToPrint;
    }

    @Override
    public void run() {
        for(int i = 1; i <= n; i++){
            System.out.println(" " + ch );
        }
    }
}
