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
import utils.JsonUtils;

/**
 * DAO para manejar figuras dibujables.
 */
public class DrawablesDao {
    private final Map<String, Drawable> drawables;
    private final JsonUtils jsonUtils;

    public DrawablesDao() {
        drawables = new HashMap<>();
        jsonUtils = new JsonUtils();
    }

    /**
     * Añade una figura dibujable al DAO.
     * @param drawable La figura dibujable a añadir.
     */
    public void add(Drawable drawable) {
        if (drawable instanceof Shape shape) {
            drawables.put(shape.getId(), drawable);
            System.out.println("ID: " + shape.getId() + ", Posición: (" + shape.getStart().getX() + ", " + shape.getStart().getY() + ")," + shape + " Color: " + shape.getColor());
        }
    }

    
    /**
    * Elimina una figura dibujable del DAO.
    * @param drawable La figura dibujable a eliminar.
    */
   public void remove(Drawable drawable) {
       if (drawable instanceof Shape shape) {
           drawables.remove(shape.getId());
       }
   }

    /**
     * Elimina todas las figuras dibujables del DAO.
     */
    public void clear() {
        drawables.clear();
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
    
    public void saveToJson(String filename) {
        List<Shape> shapes = getShapes();
        jsonUtils.saveToJson(shapes, filename);
    }

    /**
     * Carga las figuras desde un archivo JSON.
     * @param filename El nombre del archivo desde donde se cargarán las figuras.
     */
    public void loadFromJson(String filename) {
        List<Shape> shapes = jsonUtils.loadFromJson(filename);
        if (shapes != null) {
            //drawables.clear();
            for (Shape shape : shapes) {
                if (shape instanceof Drawable drawable) {
                    drawables.put(shape.getId(), drawable);
                }
            }
        }
    }
}
