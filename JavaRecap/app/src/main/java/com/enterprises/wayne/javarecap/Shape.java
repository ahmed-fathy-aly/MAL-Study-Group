package com.enterprises.wayne.javarecap;

/**
 * Created by ahmed on 7/15/2016.
 * a shape has a position(x and y)
 * it can be moved in the x and y directions
 * it can calculate its area
 * it can check if it contains a certain point
 */
public abstract class Shape
{
    /* attributes */
    private int centerX, centerY;

    /* constructor */
    public Shape(int centerX, int centerY)
    {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /* getters and setters */
    public int getCenterX()
    {
        return centerX;
    }

    public int getCenterY()
    {
        return centerY;
    }

    /* methods */

    /**
     * moves the center of the shape
     * @param dx the change in x
     * @param dy the change in y
     */
    public void move(int dx, int dy)
    {
        this.centerX += dx;
        this.centerY += dy;
    }

    /**
     * calculates the area of the shape
     */
    abstract double getArea();

    /**
     * checks if the shape contains a certain point
     */
    abstract boolean contains(int x, int y);
}
