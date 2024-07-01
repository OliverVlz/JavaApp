package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import models.Shape;

public class JsonUtils {

    private final Gson gson;

    public JsonUtils() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Método para convertir una lista de figuras a JSON y guardar en un archivo
    public void saveToJson(List<Shape> shapes, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(shapes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para leer un archivo JSON y convertirlo a una lista de figuras
    public List<Shape> loadFromJson(String filename) {
        List<Shape> shapes = null;
        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<Shape>>() {}.getType();
            shapes = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shapes;
    }
}
