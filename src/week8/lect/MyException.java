package week8.lect;

/**
 * Throwable
 * Error    Exception
 *          RuntimeException
 *          MyException
 *
 */

public class MyException extends Exception { // RuntimeException is fine, too

    private double amt;

    public MyException(String msg)
    {
        super(msg);

    }

    public MyException()
    {
        super("Not enough founds exception");
    }

    public MyException(String msg, double x)
    {
        super(msg);
        amt = x;
    }

    public double getAmt()
    {
        return amt;
    }



    public void withdraw(double amt) throws MyException
    {
        int balance = 0;
        if(amt > balance)
        {
            throw new MyException("trying to overdraw by", amt - balance);

        }
        balance -= amt;
    }
}
