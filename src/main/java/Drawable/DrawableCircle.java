package Drawable;

import models.Circle;
import models.Point;
import java.awt.Graphics;
import java.awt.Color;

public class DrawableCircle extends Circle implements Drawable {

    public DrawableCircle(Point start, int radius, Color color, int id) {
        super(start, radius, color, id); // Llama al constructor de la superclase Circle
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor()); // Establece el color del gráfico
        g.drawOval(this.getStart().x - getRadius(),
                   this.getStart().y - getRadius(),
                   getRadius() * 2, getRadius() * 2); // Dibuja un ovalo con el radio especificado
    }
}
