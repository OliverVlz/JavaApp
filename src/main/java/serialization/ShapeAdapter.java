/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serialization;

import Drawable.DrawableRectangle;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Color;
import models.*;
import java.lang.reflect.Type;

public class ShapeAdapter implements JsonSerializer<Shape> {

    @Override
    public JsonElement serialize(Shape shape, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", shape.getType());  // Usar el método getType()
        jsonObject.addProperty("id", shape.getId());
        jsonObject.add("start", context.serialize(shape.getStart()));
        jsonObject.add("color", context.serialize(shape.getColor()));
        
        // Añadir propiedades específicas según el tipo de figura
        if (shape instanceof Circle circle) {
            jsonObject.addProperty("radius", circle.getRadius());
        } else if (shape instanceof Square square) {
            jsonObject.addProperty("side", square.getSide());
        } else if (shape instanceof Rectangle rectangle) {
            jsonObject.addProperty("width", rectangle.getWidth());
            jsonObject.addProperty("height", rectangle.getHeight());
        } else if (shape instanceof Elipse elipse) {
            jsonObject.addProperty("semiMajorAxis", elipse.getSemiMajorAxis());
            jsonObject.addProperty("semiMinorAxis", elipse.getSemiMinorAxis());
        }
        return jsonObject;
    }
    
public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String type = jsonObject.get("type").getAsString();
    Shape shape;

        switch (type) {
            
            case "Circle" -> {
                Point startCircle = context.deserialize(jsonObject.get("start"), Point.class);
                int radius = jsonObject.get("radius").getAsInt();
                Color colorCircle = context.deserialize(jsonObject.get("color"), Color.class);
                shape = new Circle(startCircle, radius, colorCircle);
            }
                
            case "Square" -> {
                Point startSquare = context.deserialize(jsonObject.get("start"), Point.class);
                int side = jsonObject.get("side").getAsInt();
                Color colorSquare = context.deserialize(jsonObject.get("color"), Color.class);
                shape = new Square(startSquare, side, colorSquare);
            }
                
            case "Rectangle" -> {
                Point startRectangle = context.deserialize(jsonObject.get("start"), Point.class);
                int width = jsonObject.get("width").getAsInt();
                int height = jsonObject.get("height").getAsInt();
                Color colorRectangle = context.deserialize(jsonObject.get("color"), Color.class);
                shape = new DrawableRectangle(startRectangle, width, height, colorRectangle);
            }
                
            case "Elipse" -> {
                Point startElipse = context.deserialize(jsonObject.get("start"), Point.class);
                int semiMajorAxis = jsonObject.get("semiMajorAxis").getAsInt();
                int semiMinorAxis = jsonObject.get("semiMinorAxis").getAsInt();
                Color colorElipse = context.deserialize(jsonObject.get("color"), Color.class);
                shape = new Elipse(startElipse, semiMajorAxis, semiMinorAxis, colorElipse);
            }
            default -> throw new JsonParseException("Unknown element type: " + type);
        }
        return shape;
    }
}