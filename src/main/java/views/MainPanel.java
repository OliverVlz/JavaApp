/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import DAO.DrawablesDao;
import Drawable.Drawable;
import controllers.MainController;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import models.Shape;  // Importa el modelo de figura
import models.Circle;  // Importa el modelo de círculo
import models.Rectangle;  // Importa el modelo de rectángulo
import models.Square;
import models.Elipse;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;


/**
 *
 * @author ESTUDIANTE
 */
public class MainPanel extends javax.swing.JPanel {
    private final DrawablesDao drawables;
    private final JLabel areaLabel;
    private final JLabel selectedLabel; // Label para mostrar la figura seleccionada
    private Drawable selectedDrawable; // Figura seleccionada
    private MainController controller;
    private int prevX, prevY;
    private JButton rotateButton;
    private JButton deleteSelectedButton;
    private JButton deleteAllButton;
    private JRadioButton redRadioButton;
    private JRadioButton blueRadioButton;
    private JSlider sizeSlider;
    
    //private JRadioButton redRadioButton; // RadioButton para color rojo
    //private JRadioButton blueRadioButton; // RadioButton para color azul
    
    /**
     * Creates new form MainPanel
     * @param dao
     */
    public MainPanel(DrawablesDao dao) {
        initComponents();
        this.setSize(1366, 726);
        this.drawables = dao;
        this.setLayout(new BorderLayout()); // Establecer el layout principal

        this.areaLabel = new JLabel("Área total: 0%");
        this.areaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.areaLabel.setForeground(Color.BLACK);
        this.add(areaLabel, BorderLayout.NORTH); // Agregar el JLabel al panel en la parte superior

        // Etiqueta para la figura seleccionada
        this.selectedLabel = new JLabel("Figura seleccionada: Ninguna");
        this.selectedLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.selectedLabel.setForeground(Color.RED); // Color opcional para distinguir
        this.add(selectedLabel, BorderLayout.NORTH); // Agregar la etiqueta en la parte superior

        // Agregar listener de mouse para detectar clics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.selectDrawable(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                controller.selectDrawable(e.getX(), e.getY());
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
                double factor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;
                controller.resizeShape(factor);
                synchronizeSliders();
            }
        });
        
        // Panel para los botones y controles
        JPanel controlPanel = new JPanel(null);
        controlPanel.setLayout(null);
        controlPanel.setPreferredSize(new java.awt.Dimension(1366, 600)); // Tamaño preferido del panel de control

        // Botones y otros controles
        rotateButton = new JButton("Rotar Figura");
        rotateButton.setBounds(20, 530, 150, 30);
        rotateButton.addActionListener(e -> {
            controller.rotateShape();
            synchronizeSliders(); // Actualizar los sliders después de la rotación
        });
        controlPanel.add(rotateButton);

        deleteSelectedButton = new JButton("Eliminar Figura Seleccionada");
        deleteSelectedButton.setBounds(180, 530, 200, 30);
        deleteSelectedButton.addActionListener(e -> {
            controller.deleteSelectedShape();
        });
        controlPanel.add(deleteSelectedButton);

        deleteAllButton = new JButton("Eliminar Todas las Figuras");
        deleteAllButton.setBounds(400, 530, 200, 30);
        deleteAllButton.addActionListener(e -> {
            controller.deleteAllShapes();
        });
        controlPanel.add(deleteAllButton);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 5));
        buttonPanel.setBounds(20, 580, 600, 100); // Ajustar según sea necesario

        JButton addCircleButton = new JButton("Add Circle");
        addCircleButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
            controller.addCircle(color); // Llamar al método con el color seleccionado
            repaint();
        });

        JButton addRectangleButton = new JButton("Add Rectangle");
        addRectangleButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
            controller.addRectangle(color); // Llamar al método con el color seleccionado
            repaint();
        });

        JButton addSquareButton = new JButton("Add Square");
        addSquareButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
            controller.addSquare(color); // Llamar al método con el color seleccionado
            repaint();
        });

        JButton addElipseButton = new JButton("Add Elipse");
        addElipseButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
            controller.addElipse(color); // Llamar al método con el color seleccionado
            repaint();
        });

        // Agrupar los RadioButtons para el color
        redRadioButton = new JRadioButton("Rojo");
        redRadioButton.setSelected(false);
        redRadioButton.addActionListener(e -> {
            if (redRadioButton.isSelected()) {
                blueRadioButton.setSelected(false);
            }
        });

        blueRadioButton = new JRadioButton("Azul");
        blueRadioButton.setSelected(true);
        blueRadioButton.addActionListener(e -> {
            if (blueRadioButton.isSelected()) {
                redRadioButton.setSelected(false);
            }
        });

        sizeSlider = new JSlider(50, 150, 100);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setMinorTickSpacing(5);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.addChangeListener((ChangeEvent e) -> {
            int value = sizeSlider.getValue();
            controller.resizeShape(value / 100.0);
        });

        JButton saveButton = new JButton("Guardar Figuras");
        saveButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                controller.saveFigures(selectedFile.getAbsolutePath());
            }
        });

        JButton loadButton = new JButton("Cargar Figuras");
        loadButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                controller.loadFigures(selectedFile.getAbsolutePath());
            }
        });

        // Agregar RadioButtons al panel
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.add(redRadioButton);
        radioButtonPanel.add(blueRadioButton);
        radioButtonPanel.setBounds(20, 100, 200, 50); // Ajustar según sea necesario

        buttonPanel.add(addCircleButton);
        buttonPanel.add(addRectangleButton);
        buttonPanel.add(addSquareButton);
        buttonPanel.add(addElipseButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(sizeSlider);

        controlPanel.add(radioButtonPanel);
        controlPanel.add(buttonPanel);

        this.add(controlPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }


    /**
     * Método para actualizar el texto de la figura seleccionada.
     * @param text Texto a mostrar para la figura seleccionada.
     */
    public void updateSelectedLabel(String text) {
        selectedLabel.setText(text);
    }
    /**
     * Método para actualizar el texto del área.
     * @param text Texto a mostrar en el área.
     */
    public void updateAreaLabel(String text) {
        areaLabel.setText(text);
    }
    
    public void setController(MainController controller) {
        this.controller = controller;
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

    private void synchronizeSliders() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
