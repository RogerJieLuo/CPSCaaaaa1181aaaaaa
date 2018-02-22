package week5.assignment;
/**
 * Class to display the painting of different types of computers
 *   Program asking user input for the number of computers displayed.
 *   The number input is required to be greater than 0,
 *   if not, then it will leave a message and display nothing
 * @author Jie Luo
 * @version 1.0 2018-02-12
 */

import javax.swing.*;

public class ComputerView {

    public static void main(String[] args) {
        run();
    }

    /**
     * main program starts here
     */
    private static void run()
    {
        int num = getNum();
        if(num == 0)
        {
            if(inputAgain())
                run();
            else
                displayMsg();
        }
        else
        {
            displayPainting(num);
        }
    }

    /**
     * create a window and display the painting based on the number
     * @param num user's input about number of computers painting
     */
    private static void displayPainting(int num)
    {
        JFrame frame = new JFrame();
        ComputerComponent cc = new ComputerComponent(num);

        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cc);
        frame.setVisible(true);
    }

    /**
     * checking if it's a valid number for this program, if not, return 0
     * @return user's input or 0
     */
    private static int getNum()
    {
        try {
            int num = Integer.parseInt(getInput());
            if(num > 0)
                return num;
        }
        catch (Exception e)
        {
            System.out.println("The input is not an integer number");
        }
        return 0;
    }
    /**
     * get a number from user
     * @return an integer number
     */
    private static String getInput()
    {
        return JOptionPane.showInputDialog(null,
                                           "Input a number greater than 0",
                                           "Get number", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * a leaving message
     */
    private static void displayMsg()
    {
        JOptionPane.showMessageDialog(null, "Looking forward a valid number next time");
    }

    /**
     * This method is used to ask user if they want to redo the calculation with pair of data.
     * @return  return true if click "Yes".
     */
    private static boolean inputAgain() {
        int res = JOptionPane.showOptionDialog(null, "Invalid input, try again?", "Info",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
        return res == JOptionPane.YES_OPTION;
    }
}
