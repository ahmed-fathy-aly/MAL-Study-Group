package com.enterprises.wayne.javarecap;

/**
 * Created by ahmed on 7/15/2016.
 * A circle is a shape
 * it has the additional attributes of a radius
 */
public class Circle extends Shape
{
    /* attributes */
    private int radius;

    /* constructor */
    public Circle(int centerX, int centerY, int radius)
    {
        // call the super constructor for the original fields
        super(centerX, centerY);

        // handle the fields related to the Circle
        this.radius = radius;
    }

    /* getters and setters */
    public int getRadius()
    {
        return radius;
    }

    /* methods */
    @Override
    double getArea()
    {
        return Math.PI * radius * radius;
    }

    @Override
    boolean contains(int x, int y)
    {
        int dx = this.getCenterX() - x;
        int dy = this.getCenterY() - y;

        // we calculate the distance squared not the distance to avoid using doubles
        int distanceSquared = dx*dx + dy*dy;
        return distanceSquared <= radius * radius;
    }

}
