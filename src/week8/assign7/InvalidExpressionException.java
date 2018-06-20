package week8.assign7;

public class InvalidExpressionException extends Exception{

    public InvalidExpressionException()
    {
        super("Not enough founds exception");
    }

    public InvalidExpressionException(String msg)
    {
        super(msg);
    }
}
