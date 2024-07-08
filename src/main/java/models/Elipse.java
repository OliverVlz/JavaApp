/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.awt.Color;
import java.awt.Graphics;

public class Elipse extends Shape {
    int semiMajorAxis;  // Eje mayor
    int semiMinorAxis;  // Eje menor

    public Elipse(Point start, int semiMajorAxis, int semiMinorAxis, Color color) {
        super(start, color, "Elipse");
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
    }

    public int getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(int semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public int getSemiMinorAxis() {
        return semiMinorAxis;
    }

    public void setSemiMinorAxis(int semiMinorAxis) {
        this.semiMinorAxis = semiMinorAxis;
    }

    public int getWidth() {
        return semiMajorAxis * 2;
    }

    public int getHeight() {
        return semiMinorAxis * 2;
    }

    /**
     *
     * @return
     */
    @Override
    public double area() {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }

        
    @Override
    public String getType() {
        return "Elipse";
    }
     /**
     * Verifica si un punto está dentro de la elipse.
     * @param point El punto a verificar.
     * @return true si el punto está dentro de la elipse, false de lo contrario.
     */
    public boolean isPointInsideShape(Point point) {
        // Coordenadas del punto relativas al centro de la elipse
        int dx = point.getX() - getStart().getX();
        int dy = point.getY() - getStart().getY();

        // Verificar si el punto satisface la ecuación de la elipse
        double value = Math.pow(dx, 2) / Math.pow(semiMajorAxis, 2) + Math.pow(dy, 2) / Math.pow(semiMinorAxis, 2);
        return value <= 1;
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

