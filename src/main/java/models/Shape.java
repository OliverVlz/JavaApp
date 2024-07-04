/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.UUID;

/**
 *
 * @author ESTUDIANTE
 */
public abstract class Shape {
    String id;
    Point start;
    Color color;

    public Shape(Point start, Color color) {
        this.id = UUID.randomUUID().toString();
        this.start = start;
        this.color = color;
    }
    
    public String getId() {
        return id;
    }
    
    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public abstract double area();

     // Método abstracto para dibujar la figura
    public abstract void draw(Graphics g, boolean selected);

    // Implementación del método move para actualizar las coordenadas
    public void move(int deltaX, int deltaY) {
        this.start.setX(this.start.getX() + deltaX);
        this.start.setY(this.start.getY() + deltaY);
    }
    
    // Método abstracto para obtener el tipo de figura
    public abstract String getType();
}
