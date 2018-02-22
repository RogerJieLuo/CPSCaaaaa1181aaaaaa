package week5.assignment;
/**
 * ComputerComponent generates the list of computers and do the painting job.
 *
 *  In order to make a even chance to display 3 types of computers, I design the program generate
 *  computers based on its index. 0, 1, 2 represents desktop, laptop and pad. If intersection happens,
 *  then the latest created computer will reset a different position.
 *
 *  If the latest generated computer intersects with existed computers, then it will be reset position
 *  again. If after 10 times of reset position, the intersection still happens, then will move over to
 *  next type of computer.
 *      i.e. when generate a desktop, it intersects with other existed computers, then randomly change
 *      the position, and it intersects with existed computers again. After 10 times trying, program
 *      will try to generate a laptop. If laptop doesn't find a position in 10 times, then program will
 *      try to paint a pad.
 *
 *  Painting stop:
 *    1. if all the required computers display out, then program will stop painting
 *    2. if pad object doesn't find a fit position in 10 times, program will stop painting, when change
 *    the size of the window, program will keep painting the rest until paint all or pad doesn't find
 *    position again.
 *
 * @author Jie Luo
 * @version 1.0 2018-02-12
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ComputerComponent extends JComponent
{
    private int num;
    private ArrayList<Computer> computers;
    private Random rand;

    private static int CHECKTIME = 10;  // limit time of checking position
    private static int COMPUTER_TYPE = 3;   // computer types

    public ComputerComponent(int num)
    {
        this.num = num;
        if(this.num < 0)
            this.num = 0;
        computers = new ArrayList<Computer>();
        rand = new Random();
    }

    /**
     * override paintComponent function
     * @param g Graphics class
     */
    @Override
    public void paintComponent(Graphics g)
    {
        generateComputerList(num - computers.size());
        for(Computer c : computers)
        {
            c.draw((Graphics2D) g);
        }
    }

    /**
     * generate the list of computers
     * @param n how many number of computers are supposed to add to the list
     */
    private void generateComputerList(int n)
    {
        for(int i = 0; i < n; i++)
        {
            boolean valid = false;
            int type = i % COMPUTER_TYPE, count = 0;
            int[] bounds = getBounds(type);
            Computer c = generateComputer(type, 0, 0);
            while(!valid && count < CHECKTIME)
            {
                c.setPx(genRandInt(bounds[0]));
                c.setPy(genRandInt(bounds[1]));
                if(checkIntersect(c))
                {
                    valid = true;
                }
                count++;
            }
            if(valid)
                computers.add(c);
            else if(type == COMPUTER_TYPE - 1)
                return;
        }
    }

    /**
     * get the random bound based on the computer type
     * this bound is used for generate random position x, y value
     * @param type type of computer
     * @return array with x, y value, i.e. {x, y}
     */
    private int[] getBounds(int type)
    {
        int x, y;
        if (type == 0) {
            x = getWidth() - Desktop.WIDTH;
            y = getHeight() - Desktop.HEIGHT;
        } else if (type == 1) {
            x = getWidth() - Laptop.WIDTH;
            y = getHeight() - Laptop.HEIGHT;
        } else {
            x = getWidth() - Pad.WIDTH;
            y = getHeight() - Pad.HEIGHT;
        }
        return new int[]{x, y};
    }

    /**
     * generate computer based on type
     * @param type computer type
     * @param x position x
     * @param y position y
     * @return a computer object
     */
    private Computer generateComputer(int type, int x, int y)
    {
        Color color = genColor();
        if (type == 0)
        {
            return new Desktop(x, y, color);
        }
        else if (type == 1)
        {
            return new Laptop(x, y, color);
        }
        else
        {
            return new Pad(x, y, color);
        }
    }

    /**
     * check if the new computer intersects with existed computers
     * @param newC new computer
     * @return true/false
     */
    private boolean checkIntersect(Computer newC)
    {
        for(Computer c : computers)
        {
            if(c.getBorder().intersects(newC.getBorder()))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * generate random color
     * @return color object
     */
    private Color genColor()
    {
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    /**
     * create random number based on the bound
     * @param bound random bound
     * @return random generated number
     */
    private int genRandInt(int bound)
    {
        return rand.nextInt(bound);
    }
}
