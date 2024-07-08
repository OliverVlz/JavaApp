
package models;


public class AreaEvaluada {
    
     public long id_area_evaluada;
     public String estado;
     public double porcentaje_bosque;
     public long id_area;
     public long id_propietario;
     public long id_evaluador;

    public AreaEvaluada() {
    }

    public AreaEvaluada(int id_area_evaluada, String estado, long porcentaje_bosque, long id_area, long id_propietario, long id_evaluador) {
        this.id_area_evaluada = id_area_evaluada;
        this.estado = estado;
        this.porcentaje_bosque = porcentaje_bosque;
        this.id_area = id_area;
        this.id_propietario = id_propietario;
        this.id_evaluador = id_evaluador;
    }

    public long getId_area_evaluada() {
        return id_area_evaluada;
    }

    public void setId_area_evaluada(long id_area_evaluada) {
        this.id_area_evaluada = id_area_evaluada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPorcentaje_bosque() {
        return porcentaje_bosque;
    }

    public void setPorcentaje_bosque(double porcentaje_bosque) {
        this.porcentaje_bosque = porcentaje_bosque;
    }

    public long getId_area() {
        return id_area;
    }

    public void setId_area(long id_area) {
        this.id_area = id_area;
    }

    public long getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(long id_propietario) {
        this.id_propietario = id_propietario;
    }

    public long getId_evaluador() {
        return id_evaluador;
    }

    public void setId_evaluador(long id_evaluador) {
        this.id_evaluador = id_evaluador;
    }
     
}
