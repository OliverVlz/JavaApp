package models;

import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author ESTUDIANTE-2021
 */
public class Circle extends Shape{
    private int radius;
    

    
    public Circle(Point start, int radius, Color color) {
        super(start, color);
        this.radius = radius;
    }
    
     // Constructor sin argumentos para Gson (puede ser privado)
    private Circle() {
        super(null, null); // Valores por defecto, ajusta según necesidad
        this.radius = 0; // Valores por defecto, ajusta según necesidad
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
    
    // Gson utilizará esta anotación para mapear el nombre del campo en JSON
    @SerializedName("radius")
    public int getSerializedRadius() {
        return radius;
    }

    // Gson utilizará esta anotación para mapear el nombre del campo en JSON
    @SerializedName("radius")
    public void setSerializedRadius(int radius) {
        this.radius = radius;
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