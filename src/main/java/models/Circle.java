package models;

import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author ESTUDIANTE-2021
 */
public class Circle extends Shape{
    int radius;

    public Circle(Point start, int radius, Color color) {
        super(start, color);
        this.radius = radius;
    }
    
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    /**
     *
     * @return
     */
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    public boolean isPointInsideShape(Point point) {
        int dx = point.getX() - getStart().getX();
        int dy = point.getY() - getStart().getY();
        return dx * dx + dy * dy <= radius * radius;
    }
    
    /**
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}