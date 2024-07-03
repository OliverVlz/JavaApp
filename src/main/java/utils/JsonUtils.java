package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import Drawable.Drawable;
import models.Shape;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JsonUtils {

    private final Gson gson;
    private static final Logger logger = Logger.getLogger(JsonUtils.class.getName());

    public JsonUtils() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveToJson(List<Drawable> drawables, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(drawables, writer);
            logger.log(Level.INFO, "Figuras guardadas en {0}", filename); // Línea de depuración
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error guardando figuras en " + filename, e);
        }
    }

    public List<Drawable> loadFromJson(String filename) {
        List<Drawable> drawables = null;
        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<Drawable>>() {}.getType();
            drawables = gson.fromJson(reader, listType);
            logger.log(Level.INFO, "Figuras cargadas desde {0}", filename); // Línea de depuración
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error cargando figuras desde " + filename, e);
        }
        return drawables;
    }
}
