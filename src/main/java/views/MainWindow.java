/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import DAO.DrawablesDao;
import Drawable.Drawable;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


import models.Circle;  // Importa el modelo de círculo
import models.Rectangle;  // Importa el modelo de rectángulo
import models.Square;
import models.Elipse;
import models.Point;
import controllers.MainController;
import javax.swing.JRadioButton;

/**
 *
 * @author ESTUDIANTE
 */
public class MainWindow extends JFrame {
    private MainPanel panel;
    private DrawablesDao drawablesDao;
    private Drawable selectedDrawable;
    private MainController controller;
    private JRadioButton redRadioButton;
    private JRadioButton blueRadioButton;
    private JSlider sizeSlider;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        this.setLayout(new BorderLayout());
        this.drawablesDao = new DrawablesDao();
    }

  
    public MainPanel getPanel() {
        return panel;
    }

    public void setPanel(DrawablesDao drawables, MainController controller){
        this.drawablesDao = drawables;
        this.controller = controller;
        panel = new MainPanel(drawables);
        this.add(panel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5));

        JButton addCircleButton = new JButton("Add Circle");
            addCircleButton.addActionListener((ActionEvent e) -> {
                Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                controller.addCircle(color); // Llamar al método con el color seleccionado
                panel.repaint();
        });

        JButton addRectangleButton = new JButton("Add Rectangle");
        addRectangleButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                controller.addRectangle(color); // Llamar al método con el color seleccionado
                panel.repaint();
        });

        JButton addSquareButton = new JButton("Add Square");
        addSquareButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                controller.addSquare(color); // Llamar al método con el color seleccionado
                panel.repaint();
        });
        
        JButton addElipseButton = new JButton("Add Elipse");
        addElipseButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                controller.addElipse(color); // Llamar al método con el color seleccionado
                panel.repaint();
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
            panel.resizeShape(value / 100.0);
        });
             
        // Agregar RadioButtons al panel
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.add(redRadioButton);
        radioButtonPanel.add(blueRadioButton);
        
        buttonPanel.add(addCircleButton);
        buttonPanel.add(addRectangleButton);
        buttonPanel.add(addSquareButton);
        buttonPanel.add(addElipseButton);
        buttonPanel.add(sizeSlider);
       
        // Agregar componentes al JFrame
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(radioButtonPanel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);

        this.setSize(1366, 726); // Tamaño inicial del JFrame
        this.setVisible(true); // Hacer visible la ventana
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainController controller = new MainController();
            MainWindow mainWindow = new MainWindow();
            mainWindow.setPanel(new DrawablesDao(), controller);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
