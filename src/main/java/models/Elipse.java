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
        super(start, color);
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
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

