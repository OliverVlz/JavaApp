package utils;

import com.google.gson.*;
import models.*;

import java.lang.reflect.Type;

public class ShapeAdapter implements JsonDeserializer<Shape>, JsonSerializer<Shape> {

    @Override
    public JsonElement serialize(Shape shape, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", shape.getClass().getSimpleName());
        jsonObject.add("properties", context.serialize(shape));
        return jsonObject;
    }

    @Override
    public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Verificar que el campo "type" existe y es válido
        if (!jsonObject.has("type")) {
            throw new JsonParseException("Missing 'type' field in JSON");
        }
        String type = jsonObject.get("type").getAsString();

        // Verificar que el campo "properties" existe y no es nulo
        if (!jsonObject.has("properties") || jsonObject.get("properties").isJsonNull()) {
            throw new JsonParseException("Missing or null 'properties' field in JSON");
        }
        JsonElement propertiesElement = jsonObject.get("properties");

        try {
            // Deserializar según el tipo obtenido
            return context.deserialize(propertiesElement, Class.forName("models." + type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }
}

