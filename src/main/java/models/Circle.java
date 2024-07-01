package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Shape {
    private int radius;

    public Circle(Point start, int radius, Color color, int id) {
        super(start, color, id);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    /**
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        // To be implemented in DrawableCircle
        // This method will be overridden in DrawableCircle to draw the circle
        // DrawableCircle will handle the graphical representation
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Point point) {
        double dx = getStart().x - point.x;
        double dy = getStart().y - point.y;
        return (dx * dx + dy * dy) <= (radius * radius);
    }
}
