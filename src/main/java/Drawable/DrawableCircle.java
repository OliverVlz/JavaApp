package Drawable;

import models.Circle;
import models.Point;
import java.awt.Graphics;
import java.awt.Color;

public class DrawableCircle extends Circle implements Drawable {

    public DrawableCircle(Point start, int radius, Color color) {
        super(start, radius, color);
    }

     @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawOval(this.getStart().getX() - getRadius(),
                   this.getStart().getY() - getRadius(),
                   getRadius() * 2, getRadius() * 2);
    }
}

