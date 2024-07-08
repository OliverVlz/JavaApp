
package controllers;

import views.Login;




public class MainController {
   
   
    private Login login;
        
    public MainController() {
       login = new Login();   
    }
    
    public void start(){
        login.setVisible(true);
    }
}
