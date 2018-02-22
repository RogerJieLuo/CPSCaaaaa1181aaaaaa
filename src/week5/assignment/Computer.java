package week5.assignment;
/**
 * abstract Computer class, represents general computers picture,
 * it has attributes of position x, position y, picture width, picture height and color
 *
 * @author Jie Luo
 * @version 1.0 2018-02-12
 */

import java.awt.*;

public abstract class Computer {
    private int px;
    private int py;
    private int width;
    private int height;
    private Color color;

    public Computer(int px, int py, int width, int height, Color color)
    {
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * abstract method
     * @param g Graphics2D class
     */
    public abstract void draw(Graphics2D g);

    /**
     * draw the border of the computer image
     * @param g Graphics2D class
     */
    public void drawBorder(Graphics2D g)
    {
        g.draw(getBorder());
    }

    /**
     *
     * @return a rectangle object which is the border of the computer image
     */
    public Rectangle getBorder()
    {
        return new Rectangle(px, py, width, height);
    }

    /**
     * get position x value
     * @return position x
     */
    public int getPx() {
        return px;
    }

    /**
     * get position y value
     * @return position y
     */
    public int getPy() {
        return py;
    }

    /**
     * set up position x value
     * @param px new x value
     */
    public void setPx(int px) {
        this.px = px;
    }

    /**
     * set up position y value
     * @param py new y value
     */
    public void setPy(int py) {
        this.py = py;
    }

    /**
     * set up color value
     * @param color new color value
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * get color
     * @return color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * override equals function, if the object type is the same then we say they are equal
     * @param obj object used to compare
     * @return true/false
     */
    @Override
    public boolean equals(Object obj)
    {
        return (obj != null && getClass().getName().equals(obj.getClass().getName()));
    }

    /**
     * override toString function
     * print out the class content value
     * @return string of class content
     */
    @Override
    public String toString()
    {
        return "\nPosition X: " + px +
                "\nPosition Y: " + py +
                "\nColor: " + color;
    }
}
