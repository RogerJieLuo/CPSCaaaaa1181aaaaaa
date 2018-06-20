package week11.lect.example;

public class TaskThreadDemo {
    public static void main(String[] args) {
        final int TIMES = 10;
        Runnable printNum = new PrintNum(TIMES);
        Runnable printA = new PrintChars('a', TIMES);
        Runnable printB = new PrintChars('b', TIMES);

        Thread thread = new Thread(printNum);
        Thread thread1 = new Thread(printA);

        thread.start();
        thread1.start();
        new Thread(printB).start();


        // below will print in order
//        thread.run(); // same as printNum.run();
//        thread1.run();

    }
}
