
package models;


public class Evaluador {
  
    String nombre;
    Long  password;
    String usuario;
    private int id_evaluador;

    public Evaluador() {
    }

    public Evaluador(String nombre, Long password, String usuario, int id_evaluador) {
        this.nombre = nombre;
        this.password = password;
        this.usuario = usuario;
        this.id_evaluador = id_evaluador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId_evaluador() {
        return id_evaluador;
    }

    public void setId_evaluador(int id_evaluador) {
        this.id_evaluador = id_evaluador;
    }

    

    




    
    
    
    
}

