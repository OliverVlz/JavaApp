/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Drawable.Drawable;
import Drawable.DrawableRectangle;
import Drawable.DrawableSquare;
import java.util.ArrayList;
import java.util.List;
import models.Circle;
import models.Elipse;
import models.Shape;

/**
 *
 * @author ESTUDIANTE
 */
public class DrawablesDao {
    ArrayList <Drawable> drawables;

    public DrawablesDao() {
        drawables = new ArrayList<>();
    }
    public void add(Drawable drawable)
    {
        drawables.add(drawable);
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public List<Shape> getShapes() {
        List<Shape> shapes = new ArrayList<>();
        for (Drawable drawable : drawables) {
            if (drawable instanceof Shape shape) {
                shapes.add(shape);
            }
        }
        return shapes;
    }

    public void addShape(Circle circle) {
        drawables.add((Drawable) circle);
    }

    public void addShape(Elipse elipse) {
        drawables.add((Drawable) elipse);
    }

    public void addShape(DrawableRectangle rectangle) {
        drawables.add((Drawable) rectangle);
    }

    public void addShape(DrawableSquare square) {
        drawables.add((Drawable) square);
    }
    
}
