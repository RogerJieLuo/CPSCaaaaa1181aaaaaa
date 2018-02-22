package week4;

import javax.swing.*;
import java.awt.*;

public class HeadComponent extends JComponent
{
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        HeadImg hi = new HeadImg();
        hi.paintOld(g, 0, 0);
        hi.paintYoung(g, getWidth() - HeadImg.T_WIDTH, getHeight() - HeadImg.T_HEIGHT);
    }
}
