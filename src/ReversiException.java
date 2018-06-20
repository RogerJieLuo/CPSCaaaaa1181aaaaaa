/**
 * Customized Exception handler
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-03-30
 *
 */

public class ReversiException extends Exception {

    // default constructor
    public ReversiException(){
        super("Exception in Reversi game");
    }

    // constructor take the message
    public ReversiException(String msg){
        super(msg);
    }

}
