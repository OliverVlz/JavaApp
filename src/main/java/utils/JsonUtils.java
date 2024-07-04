package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import models.Shape;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Point;

public class JsonUtils {

    Gson gson;
    Logger logger = Logger.getLogger(JsonUtils.class.getName());

    public JsonUtils() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new ShapeAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .setPrettyPrinting()
                .create();
    }

    public void saveToJson(List<Shape> shapes, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(shapes, writer);
            logger.log(Level.INFO, "Figuras guardadas en {0}", filename); // Debugging line
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error guardando figuras en " + filename, e);
        }
    }

    public List<Shape> loadFromJson(String filename) {
        List<Shape> shapes = null;
        try (FileReader reader = new FileReader(filename)) {
            shapes = gson.fromJson(reader, new TypeToken<List<Shape>>() {}.getType());
            logger.log(Level.INFO, "Figuras cargadas desde {0}", filename); // Debugging line
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error cargando figuras desde " + filename, e);
        }
        return shapes;
    }

    public JsonElement serialize(Point start) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
