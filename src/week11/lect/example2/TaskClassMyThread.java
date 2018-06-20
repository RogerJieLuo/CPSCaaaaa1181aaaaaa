package week11.lect.example2;

public class TaskClassMyThread extends Thread {
    private int n;
    public TaskClassMyThread(int n){
        this.n = n;
    }

    @Override
    public void run(){

    }

    /*
    {
        // the way to call thread
        Thread t = new TaskClassMyThread(5);
        t.start();
    }
     */
}
