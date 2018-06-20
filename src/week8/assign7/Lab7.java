package week8.assign7;
/**
 * A practise to handle Exception
 *      with/without input file
 *      with/withou output file
 *      if expression is format with num1 operator num2
 *          +, -, *, /, %, ^
 *      divide by 0
 *      mod by 0
 * only one while loop
 * only one switch block, no if block
 * all expression exceptions are handled by InvalidExpressionException
 * unspecified exception are with statement "Something wrong did not catch here"
 *  or turn on DEBUG to track to help identify where the exception happens.
 *
 */

import java.io.*;
import java.util.Scanner;

public class Lab7 {
    // tracking the code, set it to true to see which path program goes
    private boolean DEBUG = false;

    public static void main(String[] args) {
        // test args
        args = new String[]{"src/week8/assign7/input.txt", "src/week8/assign7/output.txt"};
//        args = new String[]{"src/week8/assign7/input.txt"};
        new Lab7().run(args);
    }

    /**
     * run the program
     * @param args arguments from command line
     */
    private void run(String[] args){
        String inputFile;
        String outputFile;

        try{
            // read from file
            inputFile = args[0];
            try {
                // output file exists
                outputFile = args[1];
                readFromFile(inputFile, outputFile);
            }catch (ArrayIndexOutOfBoundsException e1){
                // no output parameter, then print on console
                if(DEBUG) log("DEBUG: No output parameter.");
                readFromFileToConsole(inputFile);
            }

        }catch (ArrayIndexOutOfBoundsException e1){
            // no input parameter, then read and print on console
            if(DEBUG) log("DEBUG: No input parameter.");
            readFromConsole();
        }

    }

    /**
     * read the expressions from input file
     * @param inputFile input file
     * @param outputFile output file
     */
    private void readFromFile(String inputFile, String outputFile) {
        try {
            Scanner in = new Scanner(new File(inputFile));
            PrintWriter writer = new PrintWriter(outputFile);
            process(in, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            // if file is not found, go console
            if(DEBUG) log("DEBUG: file is not found.");
            readFromConsole();
        } catch (Exception e){
            if(DEBUG) log("DEBUG: something wrong did not catch here 1.");
            readFromConsole();
        }
    }
    /**
     * read the expressions from input file
     * @param inputFile input file
     */
    private void readFromFileToConsole(String inputFile) {
        try {
            Scanner in = new Scanner(new File(inputFile));
            PrintWriter writer = new PrintWriter(System.out, true);
            process(in, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            // if file is not found, go console
            if(DEBUG) log("DEBUG: file is not found.");
            readFromConsole();
        } catch (Exception e){
            if(DEBUG) log("DEBUG: something wrong did not catch here 1.");
            readFromConsole();
        }
    }

    /**
     * read the expressions from console
     */
    private void readFromConsole() {
        Scanner in = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out, true);
        System.out.println("Enter expression:");
        process(in, writer);
        writer.flush(); // will never access
        writer.close(); // will never access
        in.close();     // will never access
    }

    /**
     * process the program by reading each line of the input
     * @param in input
     * @param writer output
     */
    private void process(Scanner in, PrintWriter writer){
        while(in.hasNextLine()){
            try {
                loopChecking(in, writer);
            } catch (InvalidExpressionException e){
                writer.println(e.getMessage());
            }
        }
    }

    /**
     * control the loop content, includes checking errors and calculation
     * @param in input
     * @param writer output
     * @throws InvalidExpressionException
     */
    private void loopChecking(Scanner in, PrintWriter writer) throws InvalidExpressionException{
        int res;
        String expression = in.nextLine();
        String line = expression;
        line = line.trim();
        String[] parts = line.split("\\s+");
        int f, l;
        String operator;
        try {
            f = Integer.parseInt(parts[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidExpressionException(expression + ": There is no expression input.");
        } catch (NumberFormatException e) {
            // the first string is not an integer
            throw new InvalidExpressionException(expression + ": The first operand is not an integer.");
        } catch (Exception e) {
            throw new InvalidExpressionException("Something wrong did not catch here 2");
        }

        try {
            operator = "" + Integer.parseInt(parts[1]);
            // pass the above statement means the second string is a number instead of operator
            throw new InvalidExpressionException(expression + ": The operator is missing.");
        } catch (NumberFormatException e3) {
            // it's confirmed it's not an integer
            // assume it's an operator
            operator = parts[1];
        } catch (ArrayIndexOutOfBoundsException e){
            throw new InvalidExpressionException(expression + ": The operator is missing.");
        } catch (InvalidExpressionException e) {
            // in order to skip the superclass Exception catch
            // duplicate the InvalidExpressionException throw
            throw new InvalidExpressionException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidExpressionException(expression + ": Something wrong did not catch here 3.");
        }

        try {
            l = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e3) {
            throw new InvalidExpressionException(expression + ": The second operand is not an integer.");
        } catch (ArrayIndexOutOfBoundsException e){
            throw new InvalidExpressionException(expression + ": The second operand is missing.");
        } catch (Exception e){
            throw new InvalidExpressionException(expression + ": Something wrong did not catch here 4.");
        }

        try {
            String testForthEle = parts[3];
            throw new InvalidExpressionException(expression + ": There are more than 2 operands or an operator");
        } catch (IndexOutOfBoundsException e) {
            // there is no forth element, continue
        } catch (InvalidExpressionException e){
            // in order to skip the superclass Exception catch
            // duplicate the InvalidExpressionException throw
            throw new InvalidExpressionException(e.getMessage());
        } catch (Exception e){
            throw new InvalidExpressionException(expression + ": Something wrong did not catch here 5.");
        }

        try {
            res = calculate(f, l, operator);
        } catch (InvalidExpressionException e){
            throw new InvalidExpressionException(expression + ": " + e.getMessage() );
        } catch (Exception e){
            throw new InvalidExpressionException(expression + ": Something wrong did not catch here 6.");
        }
        writer.println(f + " " + operator + " " + l + " = " + res );
    }

    /**
     * handle the expression of two operands and one operation
     * @param a first operand
     * @param b second operand
     * @param operator  operator
     * @return  return the result of the expression
     * @throws InvalidExpressionException
     */
    private int calculate(int a, int b, String operator) throws InvalidExpressionException {
        try {
            switch (operator) {
                case "+":
                    return a + b;
                case "-":
                    return a - b;
                case "*":
                    return a * b;
                case "/":
                    try {
                        return a / b;
                    }catch (ArithmeticException e){
                        throw new InvalidExpressionException("The operator \"/\" can not divide by zero.");
                    }
                case "%":
                    try {
                        return a % b;
                    }catch (ArithmeticException e){
                        throw new InvalidExpressionException("The operator \"%\" can not divide by zero.");
                    }
                case "^":
                    return (int) Math.pow(a, b);
                default:
                    throw new InvalidExpressionException("THe operator \"" + operator + "\" it's not an operator.");
            }
        } catch (InvalidExpressionException e){
            // in order to skip the superclass Exception catch
            // duplicate the InvalidExpressionException throw
            throw new InvalidExpressionException(e.getMessage());
        } catch (Exception e){
            throw new InvalidExpressionException("THe \"" + operator + "\" is not a recognized operator.");
        }
    }

    /**
     * print the message in the console
     * @param str message
     */
    private void log(String str){
        System.out.println(str);
    }

}
