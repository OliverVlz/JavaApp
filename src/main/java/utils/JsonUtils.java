package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Shape;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Point;

public class JsonUtils {

    private final Gson gson;
    Logger logger = Logger.getLogger(JsonUtils.class.getName());

    public JsonUtils() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new ShapeAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .registerTypeAdapter(Point.class, new PointAdapter())
                .setPrettyPrinting()
                .create();
    }

    public void saveToJson(List<Shape> shapes, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(shapes, writer);
            logger.log(Level.INFO, "Figuras guardadas en {0}" + shapes +"", filename);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error guardando figuras en " + filename, e);
        }
    }

    public List<Shape> loadFromJson(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Type shapeListType = new TypeToken<List<Shape>>() {}.getType();
            logger.log(Level.INFO, "Figurasasdasda" + shapeListType +"", filename);
            return gson.fromJson(reader, shapeListType);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error cargando figuras desde " + filename, e);
            return null;
        }
    }

    public Gson getGson() {
        return gson;
    }
}
