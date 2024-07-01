package Drawable;

import models.Elipse;
import models.Point;
import java.awt.Graphics;
import java.awt.Color;

public class DrawableElipse extends Elipse implements Drawable {

    public DrawableElipse(Point start, int semiMajorAxis, int semiMinorAxis, Color color) {
        super(start, semiMajorAxis, semiMinorAxis, color);
    }

    @Override
    public void draw(Graphics g, boolean selected) {
        g.setColor(selected ? Color.GREEN : getColor());
        g.drawOval(this.getStart().getX() - getSemiMajorAxis(),
                   this.getStart().getY() - getSemiMinorAxis(),
                   getWidth(), getHeight());
    }

    @Override
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
