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

    @Override
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
