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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ESTUDIANTE
 */
public class MainController {
    DrawablesDao drawables;
    MainWindow window;
    AreaCalculator areaService;
    int shapeCounter;
    
    public MainController() {
       window = new MainWindow();
       drawables = new DrawablesDao();
       areaService = new AreaCalculator();
       this.shapeCounter = 0;
       
       //initializeDrawables();
       window.setPanel(drawables, this);
       areaPercentage();
       //Gson json = CustomGson.getGson(); // Usar CustomGson para obtener Gson
       //System.out.println(json.toJson(drawables.getDrawables()));

    }
    
    public void start(){
        window.setVisible(true);
    }
    
    public void addCircle() {
        DrawableCircle circle = new DrawableCircle(new Point(100, 100), 50, Color.RED, ++shapeCounter);
        drawables.addShape(circle);
        areaPercentage();
    }

    public void addRectangle() {
        DrawableRectangle rectangle = new DrawableRectangle(new Point(150, 150), 100, 200, Color.BLUE);
        drawables.addShape(rectangle);
        areaPercentage();
    }

    public void addSquare() {
        DrawableSquare square = new DrawableSquare(new Point(200, 200), 100, Color.RED);
        drawables.addShape(square);
        areaPercentage();
    }
    
    /*private void initializeDrawables(){
        
       DrawableRectangle rectangle=new DrawableRectangle(
            new Point(683, 363),400,600, Color.BLUE
       ); 
       DrawableSquare square = new DrawableSquare(
            new Point(700, 500), 100, Color.RED
       );
       DrawableElipse elipse = new DrawableElipse(
            new Point(600, 600), 100, 50, Color.BLUE
       );
       
       DrawableCircle circle = new DrawableCircle(
            new Point(683, 363), 50, Color.RED
        );
      
       drawables.add(rectangle);
       //drawables.add(elipse);
       drawables.add(circle);
       drawables.add(square);
    }*/

     public void moveSelectedShape(int id, Point newLocation) {
        Shape shape = drawables.getShapeById(id);
        if (shape != null) {
            shape.setStart(newLocation);
        }
    }
     
    private void areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        double percentage = areaService.calculatePercentage();

        String message = "Area total " + percentage + "%";
        System.out.println(message); // Imprime en la consola
        window.getPanel().updateAreaLabel(message);
        /*if (window.getPanel() != null) {
            window.getPanel().updateAreaLabel(message);
        } else {
            System.err.println("MainPanel en MainWindow es nulo");
        }*/
    }
 
}
