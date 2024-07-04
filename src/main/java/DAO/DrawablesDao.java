package DAO;

import Drawable.Drawable;
import com.google.gson.JsonObject;
import models.*;
import serialization.GsonShapeSerializer;
import serialization.ShapeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO para manejar figuras dibujables.
 */
public class DrawablesDao {
    private final Map<String, JsonObject> drawables; // Usamos JsonObject para mantener la estructura original
    private final GsonShapeSerializer gsonShapeSerializer;
    private final ShapeAdapter shapeAdapter;

    public DrawablesDao() {
        drawables = new HashMap<>();
        gsonShapeSerializer = new GsonShapeSerializer();
        shapeAdapter = new ShapeAdapter();
    }

    /**
     * Añade una figura dibujable al DAO.
     *
     * @param drawable La figura dibujable a añadir.
     */
    public void add(Drawable drawable) {
        if (drawable instanceof Shape shape) {
            String id = shape.getId();
            if (!drawables.containsKey(id)) {
                JsonObject serializedShape = shapeAdapter.serialize(shape, Shape.class, null).getAsJsonObject();
                drawables.put(id, serializedShape);
            }
        }
    }

    /**
     * Elimina una figura dibujable del DAO.
     *
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
     *
     * @return Una lista de figuras dibujables.
     */
    public List<Drawable> getDrawables() {
        List<Drawable> drawableList = new ArrayList<>();
        for (JsonObject jsonObject : drawables.values()) {
            Shape shape = gsonShapeSerializer.deserialize(jsonObject.toString(), Shape.class);
            if (shape instanceof Drawable drawable) {
                drawableList.add(drawable);
            }
        }
        return drawableList;
    }

    /**
     * Obtiene todas las figuras de tipo Shape.
     *
     * @return Una lista de figuras de tipo Shape.
     */
    public List<Shape> getShapes() {
        List<Shape> shapes = new ArrayList<>();
        for (JsonObject jsonObject : drawables.values()) {
            Shape shape = gsonShapeSerializer.deserialize(jsonObject.toString(), Shape.class);
            if (shape != null) {
                shapes.add(shape);
            }
        }
        return shapes;
    }

    /**
     * Actualiza una figura dibujable en el DAO.
     *
     * @param id              El identificador de la figura a actualizar.
     * @param updatedDrawable La nueva figura actualizada.
     */
    public void updateDrawable(String id, Drawable updatedDrawable) {
        if (drawables.containsKey(id)) {
            JsonObject serializedShape = shapeAdapter.serialize((Shape) updatedDrawable, updatedDrawable.getClass(), null).getAsJsonObject();
            drawables.put(id, serializedShape);
        }
    }

    /**
     * Guarda las figuras dibujables en un archivo JSON.
     *
     * @param filename Nombre del archivo JSON.
     */
    public void saveToJson(String filename) {
        gsonShapeSerializer.saveToJson(new ArrayList<>(drawables.values()), filename);
    }

    /**
     * Carga las figuras dibujables desde un archivo JSON.
     *
     * @param filename Nombre del archivo JSON.
     */
    public void loadFromJson(String filename) {
        List<JsonObject> jsonObjects = gsonShapeSerializer.loadFromJson(filename);
        drawables.clear();
        for (JsonObject jsonObject : jsonObjects) {
            String id = jsonObject.get("id").getAsString();
            drawables.put(id, jsonObject);
        }
    }
    
    /*........................................................................
    public void loadFromJson(String filename) {
        List<JsonObject> jsonObjects = gsonShapeSerializer.loadFromJson(filename);
        drawables.clear();
        for (JsonObject jsonObject : jsonObjects) {
            Shape shape = gsonShapeSerializer.deserialize(jsonObject.toString(), Shape.class); // Convertir JsonObject a String
            if (shape != null) {
                drawables.put(shape.getId(), jsonObject); // Guardar el JsonObject original
            }
        }
    }
    */

    /**
     * Añade una figura dibujable al DAO.
     *
     * @param shape La figura dibujable a añadir.
     */
    public void addShape(Shape shape) {
        String id = shape.getId();
        JsonObject serializedShape = shapeAdapter.serialize(shape, Shape.class, null).getAsJsonObject();
        drawables.put(id, serializedShape);
    }
}
