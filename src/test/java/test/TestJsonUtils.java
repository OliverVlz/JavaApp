package test;

import models.Circle;
import models.Point;
import models.Shape;
import models.Rectangle;
import utils.JsonUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TestJsonUtils {

    public static void main(String[] args) {
        JsonUtils jsonUtils = new JsonUtils();
        
        List<Shape> shapes = new ArrayList<>();
        //shapes.add(new Circle(new Point(50, 50), 50, Color.BLUE));
        shapes.add(new Rectangle(new Point(50, 50), 100, 120, Color.BLUE));

        String filename = "shapes.json";

        // Guardar las figuras en un archivo JSON
        jsonUtils.saveToJson(shapes, filename);

        // Cargar las figuras desde el archivo JSON
        List<Shape> loadedShapes = jsonUtils.loadFromJson(filename);

        if (loadedShapes != null) {
            for (Shape shape : loadedShapes) {
                if (shape != null) {
                    System.out.println("ID: " + shape.getId() + ", Posición: (" + shape.getStart().getX() + ", " + shape.getStart().getY() + ")," + shape + " Color: " + shape.getColor());
                    //Salida de prueba serializacion DAO ID: 388fdae6-078a-4626-b23e-ac3c95c088af, Posici�n: (150, 150),Drawable.DrawableRectangle@2405f0ce Color: java.awt.Color[r=255,g=0,b=0]
                    //Salida de prueba TESTJSONUTILS ID: a197b2e6-6822-442f-9f9b-d739312b3a1d, Posici�n: (50, 50),models.Rectangle@6d78f375 Color: java.awt.Color[r=0,g=0,b=255]
                } else {
                    System.out.println("Shape is null");
                }
            }
        } else {
            System.out.println("No se pudieron cargar las figuras.");
        }
    }
}
