package test;

import DAO.DrawablesDao;
import Drawable.Drawable;
import Drawable.DrawableRectangle;
import models.Point;

import java.awt.Color;
import java.util.function.Consumer;
import models.Shape;

public class DaoTest {

    public static void main(String[] args) {
        DrawablesDao dao = new DrawablesDao();

        // Crear y agregar figuras
        DrawableRectangle rectangle = new DrawableRectangle(new Point(50, 50), 100, 120, Color.BLUE);
        dao.add(rectangle);

        String filename = "drawables.json";

        // Guardar las figuras en un archivo JSON
        dao.saveToJson(filename);

        // Limpiar y recargar las figuras desde el archivo JSON
        dao.clear();
        dao.loadFromJson(filename);
        
        

        // Verificar las figuras cargadas
        dao.getDrawables().forEach((Drawable drawable) -> {
            System.out.println("ID: " + drawable );
        });
    }
}
