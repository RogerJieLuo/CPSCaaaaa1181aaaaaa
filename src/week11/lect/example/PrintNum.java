package week11.lect.example;

public class PrintNum implements Runnable{

    private int n;

    public PrintNum(int n){
        this.n = n;
    }

    @Override
    public void run() {
        for(int i = 1; i <= n; i++){
            System.out.println(" " + i);
        }
    }
}
