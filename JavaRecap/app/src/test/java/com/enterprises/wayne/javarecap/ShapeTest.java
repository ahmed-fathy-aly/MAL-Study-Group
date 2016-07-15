package com.enterprises.wayne.javarecap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 */
public class ShapeTest
{
    @Test
    public void testCircle()
    {
        // create a circle
        Circle circle = new Circle(1, 2, 2);

        // move it
        circle.move(3, 4);
        assertEquals(4, circle.getCenterX());
        assertEquals(6, circle.getCenterY());

        // calculate area
        double area = circle.getArea();
        assertEquals(Math.PI * 2 * 2, area, 0.0000001);

        // check if a point is inside
        assertEquals(true, circle.contains(5,7));
        assertEquals(false, circle.contains(6, 8));
    }

    @Test
    public void testRectangle()
    {
        // create a rectangle
        Rectangle rectangle= new Rectangle(1, 2, 4, 4);

        // move it
        rectangle.move(3, 4);
        assertEquals(4, rectangle.getCenterX());
        assertEquals(6, rectangle.getCenterY());

        // calculate area
        double area = rectangle.getArea();
        assertEquals(4*4, area, 0.00001);

        // check if a point is inside
        assertEquals(true, rectangle.contains(5,7));
        assertEquals(true, rectangle.contains(6, 8));
    }

    @Test
    public void testPolymorphism()
    {
        // create a list of different shapes
        Circle circle = new Circle(1, 2, 2);
        Rectangle rectangle= new Rectangle(1, 2, 4, 4);
        Shape[] shapes = {circle, rectangle};

        // find areas
        for (Shape shape : shapes)
            System.out.println(shape.getClass().getSimpleName() + " " + shape.getArea());

        // try casting, we know that shape[1] is a rectangle
        Rectangle castedRectangle = (Rectangle) shapes[1];
        System.out.println("is square " + castedRectangle.isSquare());
    }
}