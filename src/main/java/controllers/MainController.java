/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import DAO.DrawablesDao;
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

/**
 *
 * @author ESTUDIANTE
 */
public class MainController {
    private final DrawablesDao drawables;
    private final MainWindow window;
    private final AreaCalculator areaService;
    
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
        updateUIAfterLoading();
        //areaPercentage();
    }

    public void addRectangle(Color color) {
        DrawableRectangle rectangle = new DrawableRectangle(new Point(150, 150), 100, 200, color);
        drawables.addShape(rectangle);
        updateUIAfterLoading();
        //areaPercentage();
    }

    public void addSquare(Color color) {
        DrawableSquare square = new DrawableSquare(new Point(200, 200), 100, color);
        drawables.addShape(square);
        updateUIAfterLoading();
        //areaPercentage();
    }

    public void addElipse(Color color) {
        DrawableElipse elipse = new DrawableElipse(new Point(200, 200), 100, 50, color);
        drawables.addShape(elipse);
        updateUIAfterLoading();
        //areaPercentage();
    }
    
     public void saveFigures(String filename) {
        drawables.saveDrawablesToJson(filename);
    }

    public void loadFigures(String filename) {
        drawables.loadDrawablesFromJson(filename);
        // Actualiza la interfaz de usuario después de cargar las figuras
        updateUIAfterLoading();
    }
    
    private void updateUIAfterLoading() {
        // Aquí puedes actualizar el área y redibujar las figuras en el panel
        String message = areaPercentage();
        window.getPanel().updateAreaLabel(message);
        window.getPanel().repaint();  // Redibuja el panel con las nuevas figuras cargadas
    }

    public String areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        double percentage = areaService.calculatePercentage();
        return "Area total " + percentage + "%";
    }
    /*private void areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        double percentage = areaService.calculatePercentage();

        String message = "Area total " + percentage + "%";
        System.out.println(message); // Imprime en la consola
        window.getPanel().updateAreaLabel(message);
        
    }*/
    

 
}
