package DAO;

import Drawable.Drawable;
import Drawable.DrawableRectangle;
import Drawable.DrawableSquare;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Circle;
import models.Elipse;
import models.Shape;

/**
 * DAO para manejar figuras dibujables.
 */
public class DrawablesDao {
    private final Map<String, Drawable> drawables;

    public DrawablesDao() {
        drawables = new HashMap<>();
    }

    /**
     * Añade una figura dibujable al DAO.
     * @param drawable La figura dibujable a añadir.
     */
    public void add(Drawable drawable) {
        if (drawable instanceof Shape shape) {
            drawables.put(shape.getId(), drawable);
        }
    }

    /**
     * Obtiene todas las figuras dibujables.
     * @return Una lista de figuras dibujables.
     */
    public List<Drawable> getDrawables() {
        return new ArrayList<>(drawables.values());
    }

    /**
     * Obtiene todas las figuras de tipo Shape.
     * @return Una lista de figuras de tipo Shape.
     */
    public List<Shape> getShapes() {
        List<Shape> shapes = new ArrayList<>();
        for (Drawable drawable : drawables.values()) {
            if (drawable instanceof Shape shape) {
                shapes.add(shape);
            }
        }
        return shapes;
    }

    /**
     * Añade un círculo al DAO.
     * @param circle El círculo a añadir.
     */
    public void addShape(Circle circle) {
        drawables.put(circle.getId(), (Drawable) circle);
    }

    /**
     * Añade una elipse al DAO.
     * @param elipse La elipse a añadir.
     */
    public void addShape(Elipse elipse) {
        drawables.put(elipse.getId(), (Drawable) elipse);
    }

    /**
     * Añade un rectángulo dibujable al DAO.
     * @param rectangle El rectángulo a añadir.
     */
    public void addShape(DrawableRectangle rectangle) {
        drawables.put(rectangle.getId(), (Drawable) rectangle);
    }

    /**
     * Añade un cuadrado dibujable al DAO.
     * @param square El cuadrado a añadir.
     */
    public void addShape(DrawableSquare square) {
        drawables.put(square.getId(), (Drawable) square);
    }

    /**
     * Actualiza una figura dibujable en el DAO.
     * @param id El identificador de la figura a actualizar.
     * @param updatedDrawable La nueva figura actualizada.
     */
    public void updateDrawable(String id, Drawable updatedDrawable) {
        if (drawables.containsKey(id)) {
            drawables.put(id, updatedDrawable);
        }
    }
}
