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
   
    private  MainWindow window;
  
    
    public MainController() {
       window = new MainWindow();
        
     
    }
    
    public void start(){
        window.setVisible(true);
    }
    
       

 
}
