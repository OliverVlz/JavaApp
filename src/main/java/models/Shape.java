package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    private Point start;
    private Color color;
    private int id;

    public Shape(Point start, Color color, int id) {
        this.start = start;
        this.color = color;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract double area();

    public abstract void draw(Graphics g);

    public abstract boolean contains(Point point);
}
