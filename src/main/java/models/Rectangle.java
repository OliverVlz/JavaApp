/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author ESTUDIANTE
 */
public class Rectangle extends Shape {
    int width,height;

    public Rectangle(Point start, int width, int height, Color color) {
        super(start, color);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     *
     * @return
     */
    @Override
    public double area() {
        return width * height;
    }
    
    public boolean isPointInsideShape(Point point) {
        int x = point.getX();
        int y = point.getY();
        int startX = getStart().getX();
        int startY = getStart().getY();

        // Verificar si el punto está dentro de las coordenadas del rectángulo
        boolean insideX = x >= startX && x <= startX + width;
        boolean insideY = y >= startY && y <= startY + height;

        return insideX && insideY;
    }   
    
    @Override
    public String getType() {
        return "Rectangle";
    }
    
    /**
     *
     * @param g
     * @param selected
     */
    @Override
    public void draw(Graphics g, boolean selected) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
