package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import models.Shape;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Point;

public class GsonShapeSerializer implements ShapeSerializer {

    private final Gson gson;
    private static final Logger logger = Logger.getLogger(GsonShapeSerializer.class.getName());

    public GsonShapeSerializer() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new ShapeAdapter())
                .registerTypeAdapter(Point.class, new PointAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .create();
    }

    /**
     * Obtener el objeto Gson configurado.
     *
     * @return Objeto Gson configurado.
     */
    public Gson getGson() {
        return gson;
    }

    @Override
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public String serializeList(List<? extends Shape> shapes) {
        return gson.toJson(shapes);
    }

    @Override
    public String serializeMap(Map<String, ? extends Shape> shapeMap) {
        return gson.toJson(shapeMap);
    }

    public <T> T deserialize(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public List<Shape> deserializeList(String json) {
        Type listType = new TypeToken<List<Shape>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public Map<String, Shape> deserializeMap(String json) {
        Type mapType = new TypeToken<Map<String, Shape>>() {}.getType();
        return gson.fromJson(json, mapType);
    }

    /**
     * Guarda una lista de JsonObject en un archivo JSON.
     *
     * @param jsonObjects Lista de JsonObject a guardar.
     * @param filename    Nombre del archivo JSON.
     */
    public void saveToJson(List<JsonObject> jsonObjects, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            JsonArray jsonArray = new JsonArray();
            jsonObjects.forEach(jsonArray::add);
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al guardar JSON en archivo: " + filename, e);
            // Puedes lanzar la excepción hacia arriba si es necesario:
            // throw new RuntimeException("Error al guardar JSON en archivo: " + filename, e);
        }
    }

    /**
     * Carga una lista de JsonObject desde un archivo JSON.
     *
     * @param filename Nombre del archivo JSON.
     * @return Lista de JsonObject cargados desde el archivo JSON.
     */
    public List<JsonObject> loadFromJson(String filename) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                jsonObjects.add(element.getAsJsonObject());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar JSON desde archivo: " + filename, e);
            // Puedes lanzar la excepción hacia arriba si es necesario:
            // throw new RuntimeException("Error al cargar JSON desde archivo: " + filename, e);
        }
        return jsonObjects;
    }
}
