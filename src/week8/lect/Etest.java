package week8.lect;

import java.io.IOException;

public class Etest {
    public static void main(String[] args) throws Exception
    {
        callNoCatch();
//        System.out.println("main");
//        try
//        {
//            level1();
//        }
//        catch (Exception e)
//        {
//            System.out.println("execption in main");
//        }
    }

    public static void level1() throws Exception
    {
        System.out.println("level 1 beginning");
        try
        {
            level2();
        }
        catch (ArithmeticException e)
        {
            System.out.println("in catch of level 1:" + e.getLocalizedMessage());
            throw e;    // this throws the exception to upper level
        }
        System.out.println("level 1 ending");
    }
    public static void level2()
    {
        System.out.println("level 2 beginning");
        level3();
        System.out.println("level2 ending");
    }

    public static void level3()
    {
        System.out.println("level 3 beginning");
        int n = 5;
        int b = 0;
        int res = n / b;
        System.out.println("level 3 ending");
    }

    /**
     * try catch finally,
     * the finally block regardless of how the try block is exited
     *  - after last statement of the try block
     *  - after last statement catch block handle the exception
     *    if the try block caused an exception
     *  - when an exception was thrown and not caught
     *  - after a return statement that could have been in the
     *    try block or in any of the catch block
     *
     *  NOT if there is a system.exit()
     */

    public static void noCatch()
    {
        try{
            throw new IndexOutOfBoundsException();
        }
        finally {
            System.out.println("oooo inside of read oooo");
        }
    }

    public static void callNoCatch()
    {
        try{
            noCatch();  // need a exception that catch them because they are checked exeption
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("success to catch");
        }
        finally{
            System.out.println("goes to finally");
        }
    }





}
