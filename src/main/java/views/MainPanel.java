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
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import models.Shape;  // Importa el modelo de figura
import models.Circle;  // Importa el modelo de círculo
import models.Rectangle;  // Importa el modelo de rectángulo
import models.Square;
import models.Elipse;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 *
 * @author ESTUDIANTE
 */
public class MainPanel extends javax.swing.JPanel {
    private final DrawablesDao drawables;
    private final JLabel areaLabel;
    private final JLabel selectedLabel; // Label para mostrar la figura seleccionada
    private Drawable selectedDrawable; // Figura seleccionada
    private int prevX, prevY;
    //private JRadioButton redRadioButton; // RadioButton para color rojo
    //private JRadioButton blueRadioButton; // RadioButton para color azul
    
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
        
        /*// RadioButton para color rojo
        redRadioButton = new JRadioButton("Rojo");
        redRadioButton.setSelected(false); // No seleccionado por defecto
        redRadioButton.addActionListener(e -> {
            if (redRadioButton.isSelected()) {
                changeSelectedColor(Color.RED); // Cambiar color seleccionado a rojo
            }
        });
        redRadioButton.setBounds(20, 100, 80, 30);
        this.add(redRadioButton);
        
        // RadioButton para color azul (predeterminado seleccionado)
        blueRadioButton = new JRadioButton("Azul");
        blueRadioButton.setSelected(true); // Seleccionado por defecto
        blueRadioButton.addActionListener(e -> {
            if (blueRadioButton.isSelected()) {
                changeSelectedColor(Color.BLUE); // Cambiar color seleccionado a azul
            }
        });
        blueRadioButton.setBounds(120, 100, 80, 30);
        this.add(blueRadioButton);
        
        // Agrupar los RadioButtons para que solo uno pueda estar seleccionado a la vez
        ButtonGroup colorButtonGroup = new ButtonGroup();
        colorButtonGroup.add(redRadioButton);
        colorButtonGroup.add(blueRadioButton);*/
        
        // Agregar listener de mouse para detectar clics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectDrawable(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectDrawable(e.getX(), e.getY());
                prevX = e.getX();
                prevY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedDrawable != null && selectedDrawable instanceof Shape shape) {
                    int deltaX = e.getX() - prevX;
                    int deltaY = e.getY() - prevY;
                    shape.move(deltaX, deltaY);
                    prevX = e.getX();
                    prevY = e.getY();
                    repaint();
                }
            }
        });
        
         addMouseWheelListener((MouseWheelEvent e) -> {
             if (selectedDrawable != null) {
                 if (e.getWheelRotation() < 0) {
                     resizeShape(1.1);
                 } else {
                     resizeShape(0.9);
                 }
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
                circle.setRadius((int) (circle.getRadius() * factor));
            } else if (selectedDrawable instanceof Rectangle rectangle) {
                rectangle.setWidth((int) (rectangle.getWidth() * factor));
                rectangle.setHeight((int) (rectangle.getHeight() * factor));
            } else if (selectedDrawable instanceof Square square) {
                square.setSide((int) (square.getSide() * factor));
            } else if (selectedDrawable instanceof Elipse elipse) {
                elipse.setSemiMajorAxis((int) (elipse.getSemiMajorAxis() * factor));
                elipse.setSemiMinorAxis((int) (elipse.getSemiMinorAxis() * factor));
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<Shape> shapes = drawables.getShapes();
        for (Shape shape : shapes) {
            if (selectedDrawable != null && selectedDrawable.equals(shape)) {
                shape.draw(g, true); // Dibuja la figura seleccionada con una bandera
            } else {
                shape.draw(g, false); // Dibuja las demás figuras normalmente
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
