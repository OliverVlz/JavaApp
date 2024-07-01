/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import DAO.DrawablesDao;
import Drawable.Drawable;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import models.Shape;  // Importa el modelo de figura
import models.Circle;  // Importa el modelo de círculo
import models.Rectangle;  // Importa el modelo de rectángulo
import models.Square;
import models.Elipse;

/**
 *
 * @author ESTUDIANTE
 */
public class MainPanel extends javax.swing.JPanel {
    private final DrawablesDao drawables;
    private final JLabel areaLabel;
    private final JLabel selectedLabel; // Label para mostrar la figura seleccionada
    private Drawable selectedDrawable; // Figura seleccionada
    
    /**
     * Creates new form MainPanel
     * @param dao
     */
    public MainPanel(DrawablesDao dao) {
        initComponents();
        this.setSize(1366,726);
        this.drawables=dao;
        this.setLayout(null);
        this.areaLabel = new JLabel("Area total: 0%zxvzxcvzxcvzxcvxzcv");
        this.areaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.areaLabel.setForeground(Color.BLACK);
        this.add(areaLabel);  // Agregar el JLabel al panel
        this.areaLabel.setBounds(20, 20, 300, 30);
        
        // Etiqueta para la figura seleccionada
        this.selectedLabel = new JLabel("Figura seleccionada: Ninguna");
        this.selectedLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.selectedLabel.setForeground(Color.RED); // Color opcional para distinguir
        this.add(selectedLabel);
        this.selectedLabel.setBounds(20, 50, 300, 30);
        
         // Agregar listener de mouse para detectar clics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectDrawable(e.getX(), e.getY());
            }
        });
    }
    
    /**
     * Método para seleccionar una figura según las coordenadas del clic.
     * @param x Coordenada X del clic.
     * @param y Coordenada Y del clic.
     */
    private void selectDrawable(int x, int y) {
        List<Drawable> drawablesList = drawables.getDrawables();
        for (Drawable drawable : drawablesList) {
            if (drawable instanceof Shape shape) {
                if (isPointInsideShape(x, y, shape)) {
                    selectedDrawable = drawable;
                    updateSelectedLabel("Seleccionado: " + shape.getId());
                    System.out.println("ID " + shape.getId()); // Imprimir ID en consola
                    repaint();
                    return; // Solo seleccionamos la primera figura que encontramos
                }
            }
        }
        // Si no se seleccionó ninguna figura
        selectedDrawable = null;
        updateSelectedLabel("Ninguna figura seleccionada");
        repaint();
    }
    
    /**
     * Método para actualizar el texto del área.
     * @param text Texto a mostrar en el área.
     */
    public void updateAreaLabel(String text) {
        areaLabel.setText(text);
    }
    
    /**
     * Método para actualizar el texto de la figura seleccionada.
     * @param text Texto a mostrar para la figura seleccionada.
     */
    public void updateSelectedLabel(String text) {
        selectedLabel.setText(text);
    }
    
    /**
     * Método para verificar si un punto (x, y) está dentro de una figura.
     * @param x Coordenada X del punto.
     * @param y Coordenada Y del punto.
     * @param shape Figura a verificar.
     * @return true si el punto está dentro de la figura, false de lo contrario.
     */
    private boolean isPointInsideShape(int x, int y, Shape shape) {
        // Implementa la lógica adecuada para determinar si el punto (x, y) está dentro de la figura.
        // Por ejemplo, para círculos, rectángulos, etc., verifica las coordenadas adecuadas.
        // Aquí un ejemplo básico para círculos:
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            int dx = x - circle.getStart().getX();
            int dy = y - circle.getStart().getY();
            return dx * dx + dy * dy <= circle.getRadius() * circle.getRadius();
        }
        // Agrega más lógica según el tipo de figura si es necesario
        return false; // Placeholder, implementa la lógica adecuada
    }
    

    /**
     * Método para dibujar las figuras.
     * @param g Objeto Graphics para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<Shape> shapes = drawables.getShapes();
        for (Shape shape : shapes) {
            shape.draw(g);

            // Dibujar indicador visual para la figura seleccionada, si existe
            if (selectedDrawable instanceof Shape && selectedDrawable.equals(shape)) {
                g.setColor(Color.GREEN);

                // Verificar si es un círculo y dibujar el indicador visual apropiado
                if (shape instanceof Circle) {
                    Circle circle = (Circle) shape;
                    g.drawOval(circle.getStart().getX() - 5, circle.getStart().getY() - 5,
                               circle.getRadius() * 2 + 10, circle.getRadius() * 2 + 10);
                }
                // Agregar más verificaciones para otros tipos de figuras si es necesario
            }
        }
    }


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
