package week4;
/**
 * The window that views 2 head images
 */

import javax.swing.*;

public class HeadView
{
    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        HeadComponent hC = new HeadComponent();
        fr.setSize(500, 500);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(hC);
        fr.setVisible(true);
    }

}
