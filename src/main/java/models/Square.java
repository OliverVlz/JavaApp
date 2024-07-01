package models;

import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author ESTUDIANTE-2021
 */
public class Square extends Shape{
    int side;

    public Square(Point start, int side, Color color) {
        super(start, color);
        this.side = side;
    }
        
    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }
    
    /**
     *
     * @return
     */
    @Override
    public double area() {
        return side * side;
    }

    public boolean isPointInsideShape(Point point) {
        int x = point.getX();
        int y = point.getY();

        // Coordenadas del cuadrado
        int startX = getStart().getX();
        int startY = getStart().getY();

        // Verificar si el punto está dentro del cuadrado
        return x >= startX && x <= startX + side &&
               y >= startY && y <= startY + side;
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
