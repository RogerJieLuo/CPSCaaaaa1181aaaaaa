package week1.Assign1;

import javax.swing.JOptionPane;

/**
 * Class to calculate the inflation for last year
 * Solves CPSC1181 - 001 Assignment #1
 * Copyright @ 2018 Jie Luo. All Right Reserved.
 * @author Jie Luo
 * @version 1.0 2018-01-07
 *
 */
public class Assignment1
{
    /** The calculation will only need 2 numbers, so set the size to 2. */
    private static final int SIZE = 2;
    /** The errString is used to store the error message. */
    private static String errString;
    /** Store the numbers used for calculation. */
    private static double[] data;

    /** Two positions, since there are only 2 numbers will be used in the program. */
    private static int pos1 = 0;
    private static int pos2 = 1;


    public static void main(String[] args)
    {
        run();
    }

    /**
     * This method is used to encapsulate the main program procedure.
     *  It invokes getInput() method to get user input.
     *  Invokes isValid() method to validate the input.
     *  Invokes calInflation() to get inflation.
     *  Invokes display() to display the inflation or error message
     *  Check if user want to calculate inflation for another pair of data.
     */
    private static void run(){
        data = new double[ SIZE ];
        errString = "Warning: \n";
        String str = getInput();
        if( str != null )
        {
            if( isValid(str) )
            {
                display( String.format("The inflation is: %.2f%%", calInflation( data[pos1], data[pos2] ) * 100) );
            }
            else
            {
                display( errString );
            }
        }

        if( inputAgain() )
        {
            run();
        }
    }

    /**
     * This method is used to get the input from user via dialog window
     * @return the content user input or CANCEL action
     */
    private static String getInput()
    {
        return JOptionPane.showInputDialog(null,
                                           "Input phone price today and one year ago separate by comma",
                                           "Calculate inflation for past year", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * This method is checking the validation of user input text,
     * It invokes checkPattern() and checkTwoNumbers() methods
     * @param str, the user input
     * @return true/false
     */
    private static boolean isValid(String str)
    {
        str = str.trim();
        return checkPattern(str) && checkTwoNumbers(str);
    }

    /**
     * This method is used to check the pattern of input string str. If the string is not combined with
     * two substrings which separated by a comma, it will return false;
     * @param str   input string from user
     * @return true/false
     */
    private static boolean checkPattern(String str)
    {
        String[] inputs = str.split(",");

        if (str.indexOf(',') == -1)  // no comma
        {
            errString += "You need to input two numbers separated by comma \n";
        }
        else if (inputs.length < 2) // if there is an empty value
        {
            errString += "Second number is empty\n";
        }
        else if (inputs.length > 2) // more than 1 comma
        {
            errString += "Only two numbers allowed \n";
        }
        else if (inputs[0].equals(""))
        {
            errString += "First number is empty \n";
        }
        else
        {
            return true;
        }
        return false;
    }

    /**
     * This method is used to check the two substrings are valid numbers.
     * @param str   input string from user
     * @return  true/false
     */
    private static boolean checkTwoNumbers(String str){
        String[] subStrs = str.split(",");
        String str1 = subStrs[pos1].trim();
        String str2 = subStrs[pos2].trim();

        return checkNumRange(str1, pos1) && checkNumRange(str2, pos2);
    }

    /**
     * /**
     * This method is used to check if the string is a number and the number is in a good range.
     * @param subStr   substring
     * @param pos   the string's position
     * @return true/false
     */
    private static boolean checkNumRange(String subStr, int pos)
    {
        String position = "";
        if(pos == 0)
        {
            position = "first";
        }
        if(pos == 1)
        {
            position = "second";
        }
        if( isNumeric(subStr) )
        {
            double num = Double.parseDouble(subStr);
            if (num < 0)
            {
                errString += "The " + position + " number can not be less than 0 \n";
            }
            else if(num == 0 && pos == 0)
            {
                errString += "The " + position + " number can not be 0 \n";
            }
            else
            {
                data[pos] = num;
                return true;
            }
        }
        else
        {
            errString += "The " + position + " number is not a decimal number \n";
        }
        return false;
    }

    /**
     * This method is used to check if the substring is a number
     * @param subStr    substring of the input string
     * @return  true/false
     */
    private static boolean isNumeric (String subStr)
    {
        if(subStr.equals("."))
        {
            return false;
        }
        subStr = subStr.trim();
        // eliminate the sign
        if(subStr.charAt(0) == '-' || subStr.charAt(0) == '+')
        {
            subStr = subStr.substring(1);
        }
        // if there are 2 or more dots, return false
        if(subStr.indexOf('.') != subStr.lastIndexOf('.'))
        {
            return false;
        }
        // check dot position
        if(subStr.charAt(subStr.length()-1) == '.')
        {
            subStr = subStr.substring(0, subStr.length()-1);
        }
        else if(subStr.charAt(0) == '.')
        {
            subStr = "0"+subStr;
        }
        // check the others are digit number
        for(int i = subStr.length()-1; i>=0; i--)
        {
            char n = subStr.charAt(i);
            if( (n < '0' || n > '9') && n != '.' )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to calculate the inflation
     * @param old   value from last year
     * @param cur   value from this year
     * @return  inflation calculated
     */
    private static double calInflation(double old, double cur){
        return (cur - old) / old;
    }

    /**
     * This method is used to display the message via dialog window
     * @param msg   message
     */
    private static void display(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }

    /**
     * This method is used to ask user if they want to redo the calculation with pair of data.
     * @return  return true if click "Yes".
     */
    private static boolean inputAgain() {
        int res = JOptionPane.showOptionDialog(null, "Do you want to input new data?", "Info",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                null, null, null);
        return res == JOptionPane.YES_OPTION;
    }
}
