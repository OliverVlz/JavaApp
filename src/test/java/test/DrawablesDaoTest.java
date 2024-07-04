package test;

import DAO.DrawablesDao;
import models.Circle;
import models.Point;
import models.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serialization.GsonShapeSerializer;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DrawablesDaoTest {

    private DrawablesDao drawablesDao;

    @BeforeEach
    public void setUp() {
        drawablesDao = new DrawablesDao();
    }

    @Test
    public void testSerializationDeserialization() {
        // Crear una figura dibujable para probar
        Circle circle = new Circle(new Point(100, 100), 50, Color.RED);

        // Agregar la figura al DAO
        drawablesDao.addShape(circle);

        // Guardar las figuras en un archivo JSON (simulado)
        drawablesDao.saveToJson("test_shapes.json");

        // Limpiar el DAO para simular una nueva carga desde el archivo
        drawablesDao.clear();

        // Cargar las figuras desde el archivo JSON
        drawablesDao.loadFromJson("test_shapes.json");

        // Obtener las figuras cargadas
        List<Shape> shapes = drawablesDao.getShapes();

        // Verificar que la lista no esté vacía
        assertNotNull(shapes);
        assertEquals(1, shapes.size());

        // Verificar que la figura cargada sea del tipo esperado (Circle en este caso)
        Shape loadedShape = shapes.get(0);
        assertNotNull(loadedShape);
        assertEquals("Circle", loadedShape.getType()); // Verificar el tipo de la figura

        // Verificar otras propiedades específicas del círculo
        Circle loadedCircle = (Circle) loadedShape;
        assertEquals(circle.getRadius(), loadedCircle.getRadius());
        assertEquals(circle.getColor(), loadedCircle.getColor());
        assertEquals(circle.getStart().getX(), loadedCircle.getStart().getX());
        assertEquals(circle.getStart().getY(), loadedCircle.getStart().getY());
    }
}
