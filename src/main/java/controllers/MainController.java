/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import DAO.DrawablesDao;
import Drawable.Drawable;
import Drawable.DrawableRectangle;
import Drawable.DrawableElipse;
import Drawable.DrawableCircle;
import Drawable.DrawableSquare;

//import com.google.gson.Gson;
import models.Point;
import models.Shape;
import services.AreaCalculator;
import java.util.List;
import views.MainWindow;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import models.Circle;
import models.Elipse;
import models.Rectangle;
import models.Square;

/**
 *
 * @author ESTUDIANTE
 */
public class MainController {
    private final DrawablesDao drawables;
    private final MainWindow window;
    private final AreaCalculator areaService;
    private Drawable selectedDrawable; // Figura seleccionada
    
    public MainController() {
       window = new MainWindow();
       drawables = new DrawablesDao();
       areaService = new AreaCalculator();
       
       //initializeDrawables();
       window.setPanel(drawables, this);
       //areaPercentage();
       
       
       //Gson json = CustomGson.getGson(); // Usar CustomGson para obtener Gson
       //System.out.println(json.toJson(drawables.getDrawables()));

    }
    
    public void start(){
        window.setVisible(true);
    }
    
    public void addCircle(Color color) {
        DrawableCircle circle = new DrawableCircle(new Point(100, 100), 50, color);
        drawables.addShape(circle);
        refresh();
        //areaPercentage();
    }

    public void addRectangle(Color color) {
        DrawableRectangle rectangle = new DrawableRectangle(new Point(150, 150), 100, 200, color);
        drawables.addShape(rectangle);
        refresh();
        //areaPercentage();
    }

    public void addSquare(Color color) {
        DrawableSquare square = new DrawableSquare(new Point(200, 200), 100, color);
        drawables.addShape(square);
        refresh();
        //areaPercentage();
    }

    public void addElipse(Color color) {
        DrawableElipse elipse = new DrawableElipse(new Point(200, 200), 100, 50, color);
        drawables.addShape(elipse);
        refresh();
        //areaPercentage();
    }
    
     public void saveFigures(String filename) {
        drawables.saveDrawablesToJson(filename);
    }

    public void loadFigures(String filename) {
        drawables.loadDrawablesFromJson(filename);
        // Actualiza la interfaz de usuario después de cargar las figuras
        refresh();
    }
    
    private void refresh() {
        
        List<Drawable> drawablesList = drawables.getDrawables();
        for (Drawable drawable : drawablesList) {
            if (drawable instanceof Shape shape) {
                if (selectedDrawable != null) {
                    String message = "Seleccionado: " + shape.getId();
                    System.out.println("ID " + shape.getId()); // Imprimir ID en consola
                    window.getPanel().updateSelectedLabel(message);
                }
                else{
                    String message = "Ninguna figura seleccionada";
                    window.getPanel().updateSelectedLabel(message);
                }    
            }
        }
        // Aquí puedes actualizar el área y redibujar las figuras en el panel
        String areaText = areaPercentage();
        window.getPanel().updateAreaLabel(areaText);
        window.getPanel().repaint();  // Redibuja el panel con las nuevas figuras cargadas
    }

    public String areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        double percentage = areaService.calculatePercentage();
        String message = "Area total " + percentage + "%";
        return message;
    }
    /*private void areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        double percentage = areaService.calculatePercentage();

        String message = "Area total " + percentage + "%";
        System.out.println(message); // Imprime en la consola
        window.getPanel().updateAreaLabel(message);
        
    }*/
    
    public void deleteSelectedShape() {
        if (selectedDrawable != null) {
            drawables.remove(selectedDrawable); // Elimina la figura de la lista
            selectedDrawable = null;
            refresh();
        }
    }
    
    public void deleteAllShapes() {
        drawables.clear(); // Borra todas las figuras de la lista
        selectedDrawable = null;
        refresh();
    }
    
    public void rotateShape() {
        if (selectedDrawable != null) {
            if (selectedDrawable instanceof Rectangle rectangle) {
                int currentWidth = rectangle.getWidth();
                int currentHeight = rectangle.getHeight();
                rectangle.setWidth(currentHeight);
                rectangle.setHeight(currentWidth);
            } else if (selectedDrawable instanceof Elipse elipse) {
                int currentSemiMajorAxis = elipse.getSemiMajorAxis();
                int currentSemiMinorAxis = elipse.getSemiMinorAxis();
                elipse.setSemiMajorAxis(currentSemiMinorAxis);
                elipse.setSemiMinorAxis(currentSemiMajorAxis);
            }
        }
    }
    
    /**
     * Método para seleccionar una figura según las coordenadas del clic.
     * @param x Coordenada X del clic.
     * @param y Coordenada Y del clic.
     */
    public String selectDrawable(int x, int y) {
        List<Drawable> drawablesList = drawables.getDrawables();
        for (Drawable drawable : drawablesList) {
            if (drawable instanceof Shape shape) {
                if (isPointInsideShape(x, y, shape)) {
                    selectedDrawable = drawable;
                    String message = "Seleccionado: " + shape.getId();
                    System.out.println("ID " + shape.getId()); // Imprimir ID en consola
                    return message; // Retornar el mensaje de selección
                }
            }
        }

        // Si no se seleccionó ninguna figura
        selectedDrawable = null;
        String message = "Ninguna figura seleccionada";
        return message; // Retornar el mensaje de no selección
    }

    
    
    /**
     * Método para verificar si un punto (x, y) está dentro de una figura.
     * @param x Coordenada X del punto.
     * @param y Coordenada Y del punto.
     * @param shape Figura a verificar.
     * @return true si el punto está dentro de la figura, false de lo contrario.
     */
    private boolean isPointInsideShape(int x, int y, Shape shape) {
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            int dx = x - circle.getStart().getX();
            int dy = y - circle.getStart().getY();
            return dx * dx + dy * dy <= circle.getRadius() * circle.getRadius();
        } else if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            int startX = rectangle.getStart().getX();
            int startY = rectangle.getStart().getY();
            return x >= startX && x <= startX + rectangle.getWidth() &&
                   y >= startY && y <= startY + rectangle.getHeight();
        } else if (shape instanceof Square) {
            Square square = (Square) shape;
            int startX = square.getStart().getX();
            int startY = square.getStart().getY();
            return x >= startX && x <= startX + square.getSide() &&
                   y >= startY && y <= startY + square.getSide();
        } else if (shape instanceof Elipse) {
            Elipse elipse = (Elipse) shape;
            int centerX = elipse.getStart().getX();
            int centerY = elipse.getStart().getY();
            double dx = x - centerX;
            double dy = y - centerY;
            return (dx * dx) / (elipse.getSemiMajorAxis() * elipse.getSemiMajorAxis()) +
                   (dy * dy) / (elipse.getSemiMinorAxis() * elipse.getSemiMinorAxis()) <= 1;
        }
        return false; // Si la figura no es reconocida, devuelve false
    }

    public void resizeShape(double factor) {
        if (selectedDrawable != null) {
            if (selectedDrawable instanceof Circle circle) {
                int newRadius = (int) (circle.getRadius() * factor);
                circle.setRadius(Math.max(10, Math.min(200, newRadius))); // Limitar el tamaño entre 10 y 200
            } else if (selectedDrawable instanceof Rectangle rectangle) {
                int newWidth = (int) (rectangle.getWidth() * factor);
                int newHeight = (int) (rectangle.getHeight() * factor);
                rectangle.setWidth(Math.max(10, Math.min(200, newWidth)));
                rectangle.setHeight(Math.max(20, Math.min(400, newHeight)));
            } else if (selectedDrawable instanceof Square square) {
                int newSide = (int) (square.getSide() * factor);
                square.setSide(Math.max(10, Math.min(200, newSide)));
            } else if (selectedDrawable instanceof Elipse elipse) {
                int newSemiMajorAxis = (int) (elipse.getSemiMajorAxis() * factor);
                int newSemiMinorAxis = (int) (elipse.getSemiMinorAxis() * factor);
                elipse.setSemiMajorAxis(Math.max(10, Math.min(200, newSemiMajorAxis)));
                elipse.setSemiMinorAxis(Math.max(10, Math.min(200, newSemiMinorAxis)));
            }
        }
    }
 
}
