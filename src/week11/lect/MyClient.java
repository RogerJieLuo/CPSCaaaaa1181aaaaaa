package week11.lect;

public class MyClient {
    public void aMethod(){
        MyThread task = new MyThread();
        // construct a thread (an object)
        Thread thread = new Thread(task);
        // start new thread
        thread.start();// it will call the run() method in the task object

    }
}
