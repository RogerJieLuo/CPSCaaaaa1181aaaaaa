package week1;

/**
 * javac Welcome.java
 * java -cp . Welcome
 *
 * java file  is compiled by javac command
 * and it generates class file with bytecode
 * java virtual machine (JVM) produce results
 *
 * java applet is out of date because its security issue
 *
 *
 * All uppercase, underscore for constants
 *      private final int NUM_STARTS = 4;
 *
 *
 */

import javax.swing.JOptionPane;

public class Welcome
{
    public static void main(String[] args)
    {
        JOptionPane.showMessageDialog(null, "hello");
        System.out.println(getGallons());
    }

    private static double getGallons()
    {
        String in = JOptionPane.showInputDialog(null, " how many gallons of milk",
                                    "milk jars",JOptionPane.QUESTION_MESSAGE);

        return Double.parseDouble(in);
    }
}
