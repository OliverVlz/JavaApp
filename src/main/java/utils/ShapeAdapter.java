package utils;

import Drawable.DrawableCircle;
import Drawable.DrawableElipse;
import Drawable.DrawableRectangle;
import Drawable.DrawableSquare;
import com.google.gson.*;
import models.*;

import java.lang.reflect.Type;
import java.awt.Color;

public class ShapeAdapter implements JsonDeserializer<Shape>, JsonSerializer<Shape> {

    @Override
    public JsonElement serialize(Shape shape, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(shape).getAsJsonObject();
        jsonObject.addProperty("type", shape.getClass().getSimpleName());
        return jsonObject;
    }

   @Override
    public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.has("type")) {
            throw new JsonParseException("Missing 'type' field in JSON");
        }

        String className = jsonObject.get("type").getAsString();

        try {
            Class<?> shapeClass = Class.forName("models." + className);

            // Deserializa los campos comunes manualmente
            Point start = context.deserialize(jsonObject.get("start"), Point.class);
            Color color = context.deserialize(jsonObject.get("color"), Color.class);

            Shape shape;
            if (shapeClass == Rectangle.class) {
                int width = jsonObject.get("width").getAsInt();
                int height = jsonObject.get("height").getAsInt();
                shape = new DrawableRectangle(start, width, height, color);
                System.out.println("DrawableRectangle deserialized: " + shape);
            } else if (shapeClass == Circle.class) {
                int radius = jsonObject.get("radius").getAsInt();
                shape = new DrawableCircle(start, radius, color);
                System.out.println("DrawableCircle: " + shape);
            } else if (shapeClass == Elipse.class) {
                int semiaxisA = jsonObject.get("semiaxisA").getAsInt();
                int semiaxisB = jsonObject.get("semiaxisB").getAsInt();
                shape = new DrawableElipse(start, semiaxisA, semiaxisB, color);
                System.out.println("DrawableElipse: " + shape);
            } else if (shapeClass == Square.class) {
                int side = jsonObject.get("side").getAsInt();
                shape = new DrawableSquare(start, side, color);
                System.out.println("DrawableSquare: " + shape);
            }
            else {
                throw new JsonParseException("Unknown shape type: " + className);
            }

            // No es necesario establecer el id porque se genera automáticamente en el constructor de Shape

            return shape;
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + className, e);
        } catch (JsonParseException e) {
            throw new JsonParseException("Error deserializing shape of type: " + className, e);
        }
    }
}