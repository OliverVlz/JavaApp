
package controllers;

import views.MainWindow;



public class MainController {
   
    private  MainWindow window;
  
    
    public MainController() {
       window = new MainWindow();   
    }
    
    public void start(){
        window.setVisible(true);
    }
}
