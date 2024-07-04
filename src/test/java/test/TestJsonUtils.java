package test;

import models.Circle;
import models.Point;
import models.Shape;
import utils.JsonUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TestJsonUtils {

    public static void main(String[] args) {
        JsonUtils jsonUtils = new JsonUtils();
        
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(new Point(50, 50), 20, Color.BLUE));

        String filename = "shapes.json";

        // Guardar las figuras en un archivo JSON
        jsonUtils.saveToJson(shapes, filename);

        // Cargar las figuras desde el archivo JSON
        List<Shape> loadedShapes = jsonUtils.loadFromJson(filename);

        // Imprimir las figuras cargadas para verificar
        for (Shape shape : loadedShapes) {
            System.out.println("ID: " + shape.getId() + ", Posición: (" + shape.getStart().getX() + ", " + shape.getStart().getY() + "), Color: " + shape.getColor());
        }
    }
}
