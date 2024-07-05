package utils;

import com.google.gson.*;
import models.*;

import java.lang.reflect.Type;

public class ShapeAdapter implements JsonDeserializer<Shape>, JsonSerializer<Shape> {

    @Override
    public JsonElement serialize(Shape shape, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", shape.getClass().getSimpleName()); // Usamos el nombre de la clase
        JsonObject shapeJson = context.serialize(shape).getAsJsonObject();
        jsonObject.add("shape", shapeJson); // Usamos "shape" en lugar de "properties"
        return jsonObject;
    }


    @Override
    public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.has("type")) {
            throw new JsonParseException("Missing 'type' field in JSON");
        }

        String className = jsonObject.get("type").getAsString();
        JsonElement shapeElement = jsonObject.get("shape");

        try {
            Class<?> shapeClass = Class.forName("models." + className);
            Shape shape = context.deserialize(shapeElement, shapeClass);
            if (shape == null) {
                throw new JsonParseException("Deserialized shape is null");
            }
            return shape;
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + className, e);
        } catch (JsonParseException e) {
            throw new JsonParseException("Error deserializing shape of type: " + className, e);
        }
    }
}
