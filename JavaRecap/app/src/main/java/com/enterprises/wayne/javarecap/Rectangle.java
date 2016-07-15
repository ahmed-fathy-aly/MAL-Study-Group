package com.enterprises.wayne.javarecap;

/**
 * Created by ahmed on 7/15/2016.
 * A rectangle is a shape
 * it has the additional attributes of a width and a height
 * it has the additional functionality of checking if it's a square
 */
public class Rectangle extends Shape
{
    /* attributes */
    private int width, height;

    /* constructor */
    public Rectangle(int centerX, int centerY, int width, int height)
    {
        // call the super constructor for the original fields
        super(centerX, centerY);

        // handle the fields related to the Circle
        this.width = width;
        this.height = height;
    }

    /* getters and setters */
    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    /* methods */
    @Override
    double getArea()
    {
        return width * height;
    }

    @Override
    boolean contains(int x, int y)
    {
        int distanceX = Math.abs(this.getCenterX()- x);
        int distanceY = Math.abs(this.getCenterY()- y);
        System.out.println(distanceX + " " + distanceY);
        return distanceX * 2 <= width && distanceY * 2 <= height;
    }

    /**
     * checks if it's a square(has equal sides)
     */
    public boolean isSquare()
    {
        return width == height;
    }

}
