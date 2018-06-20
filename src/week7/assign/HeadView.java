package week7.assign;
/**
 * The window that views one head images
 * @author Jie Luo
 * @version 1.0 2018-02-28
 */

import javax.swing.*;

public class HeadView
{
    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        HeadComponent hC = new HeadComponent();
        fr.setSize(800, 800);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(hC);
        fr.setVisible(true);
    }

}
