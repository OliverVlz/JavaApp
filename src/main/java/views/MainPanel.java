package views;

import DAO.DrawablesDao;
import Drawable.Drawable;
import Drawable.DrawableCircle;
import Drawable.DrawableElipse;
import Drawable.DrawableRectangle;
import Drawable.DrawableSquare;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.BorderFactory;
import models.Shape; 
import models.Circle;  
import models.Rectangle; 
import models.Square;
import models.Elipse;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import models.AreaEvaluada;
import models.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import services.AreaCalculator;




public class MainPanel extends javax.swing.JPanel {
    
    
    
    //declaraciones
    private  DrawablesDao drawables;
    private Drawable selectedDrawable; // Figura seleccionada
    private  AreaCalculator areaService;
    //declaraciones.label
    private final JLabel areaLabel;
    private final JLabel selectedLabel; 
    private JLabel dibujarFigurasLabel;
    private JLabel lblImagen;
   
    //declaraciones.botones
    private int prevX, prevY;
    private JButton rotateButton;
    private JButton deleteSelectedButton;
    private JButton deleteAllButton;
    private JButton selectImageButton;
    private JRadioButton redRadioButton;
    private JRadioButton blueRadioButton;
    private JSlider sizeSlider;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addCircleButton;
    private JButton addRectangleButton;
    private JButton addElipseButton;
    private JButton addSquareButton;
    private JButton guardarevaluacion;
    //guardarevaluciacon
    public double percentage;
    //constructor
    public MainPanel() {
        
        
        initComponents();
        this.setSize(1366,726);
        this.setLayout(null);
       
        //Instancias
        drawables= new DrawablesDao ();
        areaService = new AreaCalculator();
        
        //instancias.label
        this.areaLabel = new JLabel("Area total: 0% :/");
        this.selectedLabel = new JLabel("Figura seleccionada: Ninguna");
        lblImagen = new JLabel();
        dibujarFigurasLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar las figuras en el JLabel
                List<Shape> shapes = drawables.getShapes();
                for (Shape shape : shapes) {
                    shape.draw(g, false); // Dibujar las figuras normalmente
                }
            }
        };
         
        //instancias.button
        selectImageButton = new JButton("Select Image");
        rotateButton = new JButton("Rotate Figure");
        deleteSelectedButton = new JButton("Delete Selected Figure");
        deleteAllButton = new JButton("Delete Figures");
        saveButton = new JButton("Save Figures");
        loadButton = new JButton("Load Figures");
        addCircleButton = new JButton("Add Circle");
        addRectangleButton = new JButton("Add Rectangle");
        addSquareButton = new JButton("Add Square");
        addElipseButton = new JButton("Add Elipse");
        redRadioButton = new JRadioButton("Red");  
        blueRadioButton = new JRadioButton("Blue");
        sizeSlider = new JSlider(50, 150, 100);
        guardarevaluacion = new JButton("Save Evaluation");
        
        //posicion de label
        this.areaLabel.setBounds(20, 20, 300, 30);
        selectedLabel.setBounds(20,50,300,30);
        lblImagen.setBounds(100, 100, 800, 500); 
        dibujarFigurasLabel.setBounds(100,100,800,500);
                
        //posicion de botones        
        selectImageButton.setBounds(970, 100, 150, 30); 
        rotateButton.setBounds(1050, 280, 130, 30);
        deleteSelectedButton.setBounds(945, 320, 200, 30);
        deleteAllButton.setBounds(910, 280, 130, 30);
        saveButton.setBounds(1050, 440, 130, 30);
        loadButton.setBounds(910, 440, 130, 30);
        addCircleButton.setBounds(910, 200, 130, 30);
        addRectangleButton.setBounds(910, 240, 130, 30);
        addSquareButton.setBounds(1050, 240, 130, 30);        
        addElipseButton.setBounds(1050, 200, 130, 30);
        redRadioButton.setBounds(930,160,100,30);       
        blueRadioButton.setBounds(1060,160,100,30);
        sizeSlider.setBounds(910,370,270,50);
        guardarevaluacion.setBounds(945, 500, 200, 30);
       
        //eventos button
        selectImageButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(); // Llama al método para seleccionar imagen
            }
        });
        rotateButton.addActionListener(e -> {
            rotateShape();
            synchronizeSliders(); // Actualizar los sliders después de la rotación
        });
        deleteSelectedButton.addActionListener(e -> {
            deleteSelectedShape();
        });
        deleteAllButton.addActionListener(e -> {
            deleteAllShapes();
        });
        saveButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                saveFigures(selectedFile.getAbsolutePath());
            }
        });          
        loadButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadFigures(selectedFile.getAbsolutePath());
            }
        });
        addCircleButton.addActionListener((ActionEvent e) -> {
                Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addCircle(color); // Llamar al método con el color seleccionado
                repaint();
        });
        addRectangleButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addRectangle(color); // Llamar al método con el color seleccionado
                repaint();
        });
        addSquareButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addSquare(color); // Llamar al método con el color seleccionado
                repaint();
        });
        addElipseButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addElipse(color); // Llamar al método con el color seleccionado
                repaint();
        });
        redRadioButton.addActionListener(e -> {
            if (redRadioButton.isSelected()) {
                blueRadioButton.setSelected(false);
            }
        });
        blueRadioButton.addActionListener(e -> {
            if (blueRadioButton.isSelected()) {
                redRadioButton.setSelected(false);
            }
        });
        sizeSlider.addChangeListener((ChangeEvent e) -> {
            int value = sizeSlider.getValue();
            resizeShape(value / 100.0);             
        });
        guardarevaluacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
        AreaEvaluada areasEvaluadas = new AreaEvaluada();
        areasEvaluadas.setId_area_evaluada(1);        
        areasEvaluadas.setId_area(1);
        areasEvaluadas.setId_evaluador(1);
        areasEvaluadas.setPorcentaje_bosque(percentage);
        areasEvaluadas.setEstado("evaluado");
        
        
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/areaevaluada"; 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        
        HttpEntity<AreaEvaluada> request = new HttpEntity<>(areasEvaluadas, headers);
        ResponseEntity<String> response= restTemplate.exchange(url,HttpMethod.POST,request,String.class);
            }
        });
    
             
         //eventos mouse
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
                double factor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;
                resizeShape(factor);
                synchronizeSliders();
            }
        });
        
        // configuracion de etiqutias    
        this.selectedLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.areaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.selectedLabel.setForeground(Color.LIGHT_GRAY); 
        this.areaLabel.setForeground(Color.LIGHT_GRAY);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setMinorTickSpacing(5);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 8));
        lblImagen.setBackground(Color.LIGHT_GRAY); 
        lblImagen.setOpaque(true);
        dibujarFigurasLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY,8));       
        redRadioButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        redRadioButton.setBackground(Color.darkGray);
        redRadioButton.setForeground(Color.LIGHT_GRAY); 
        blueRadioButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        blueRadioButton.setBackground(Color.darkGray); 
        blueRadioButton.setForeground(Color.LIGHT_GRAY);
        this.setBackground(Color.darkGray);
        
        //componentes añadidos al panel
        this.add(selectedLabel);          
        this.add(dibujarFigurasLabel); // Agregar el JLabel al panel principal                    
        this.add(lblImagen);
        this.add(areaLabel);           
        this.add(selectImageButton);
        this.add(rotateButton);       
        this.add(deleteSelectedButton);      
        this.add(deleteAllButton);    
        this.add(saveButton);   
        this.add(loadButton);
        this.add(redRadioButton);
        this.add(blueRadioButton);
        this.add(addCircleButton);
        this.add(addRectangleButton);
        this.add(addSquareButton);
        this.add(addElipseButton);
        this.add(sizeSlider);      
        this.add(guardarevaluacion);
    }
    
        //metodos    
        private void deleteSelectedShape() {
        if (selectedDrawable != null) {
            drawables.remove(selectedDrawable); // Elimina la figura de la lista
            selectedDrawable = null;
            updateSelectedLabel("Ninguna figura seleccionada");
            areaPercentage();
            repaint();
                  }
         }    
        private void deleteAllShapes() {
             drawables.clear(); // Borra todas las figuras de la lista
             selectedDrawable = null;
             updateSelectedLabel("Ninguna figura seleccionada");
             areaPercentage();
             repaint();
          }
        private void selectImage() {
             JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
               if (result == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getPath();
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
                     lblImagen.setIcon(imageIcon); // Muestra la imagen seleccionada en lblImagen
                }
         }        
        private void rotateShape() {
              if (selectedDrawable != null) {
                     if (selectedDrawable instanceof Rectangle rectangle) {
                            int currentWidth = rectangle.getWidth();
                            int currentHeight = rectangle.getHeight();
                            rectangle.setWidth(currentHeight);
                            rectangle.setHeight(currentWidth);
                            repaint();
                    } else if (selectedDrawable instanceof Elipse elipse) {
                            int currentSemiMajorAxis = elipse.getSemiMajorAxis();
                            int currentSemiMinorAxis = elipse.getSemiMinorAxis();
                            elipse.setSemiMajorAxis(currentSemiMinorAxis);
                            elipse.setSemiMinorAxis(currentSemiMajorAxis);
                            repaint();
                     }
              }
        }   
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
    selectedDrawable = null;
    updateSelectedLabel("Ninguna figura seleccionada");
    repaint();
}
        public void updateAreaLabel(String text) {
            areaLabel.setText(text);
        }    
        public void updateSelectedLabel(String text) {
            selectedLabel.setText(text);
        }    
        private boolean isPointInsideShape(int x, int y, Shape shape) {
    if (shape instanceof Circle) {
        Circle circle = (Circle) shape;
        int centerX = circle.getStart().getX();
        int centerY = circle.getStart().getY();
        int radius = circle.getRadius();
        
        // Calcula la distancia al cuadrado desde el punto dado al centro del círculo
        double distanceSquared = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        
        // Compara con el radio al cuadrado para determinar si está dentro o en el borde
        return distanceSquared <= Math.pow(radius, 2);
        
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
        int semiMajorAxis = elipse.getSemiMajorAxis();
        int semiMinorAxis = elipse.getSemiMinorAxis();
        
        // Calcula la distancia normalizada al centro de la elipse
        double dx = (x - centerX) * 1.0 / semiMajorAxis;
        double dy = (y - centerY) * 1.0 / semiMinorAxis;
        
        // Compara con la ecuación de la elipse normalizada: (dx^2 + dy^2) <= 1
        return (dx * dx) + (dy * dy) <= 1;
    }
    
    return false; // Si la figura no es reconocida, devuelve false
}
        public void resizeShape(double factor) {
            if (selectedDrawable != null) {
                if (selectedDrawable instanceof Circle circle) {
                    int newRadius = (int) (circle.getRadius() * factor);
                    circle.setRadius(Math.max(10, Math.min(200, newRadius))); // Limitar el tamaño entre 10 y 200
                    areaPercentage();
                } else if (selectedDrawable instanceof Rectangle rectangle) {
                    int newWidth = (int) (rectangle.getWidth() * factor);
                    int newHeight = (int) (rectangle.getHeight() * factor);
                    rectangle.setWidth(Math.max(10, Math.min(200, newWidth)));
                    rectangle.setHeight(Math.max(20, Math.min(400, newHeight)));
                    areaPercentage();
                } else if (selectedDrawable instanceof Square square) {
                    int newSide = (int) (square.getSide() * factor);
                    square.setSide(Math.max(10, Math.min(200, newSide)));
                    areaPercentage();
                } else if (selectedDrawable instanceof Elipse elipse) {
                    int newSemiMajorAxis = (int) (elipse.getSemiMajorAxis() * factor);
                    int newSemiMinorAxis = (int) (elipse.getSemiMinorAxis() * factor);
                    elipse.setSemiMajorAxis(Math.max(10, Math.min(200, newSemiMajorAxis)));
                    elipse.setSemiMinorAxis(Math.max(10, Math.min(200, newSemiMinorAxis)));
                    areaPercentage();
                }
                repaint();
            }
        }       
        public void addCircle(Color color) {
            DrawableCircle circle = new DrawableCircle(new Point(100, 100), 50, color);
            drawables.addShape(circle);
            areaPercentage();
        }
        public void addRectangle(Color color) {
            DrawableRectangle rectangle = new DrawableRectangle(new Point(150, 150), 100, 200, color);
            drawables.addShape(rectangle);            
            areaPercentage();
        }
        public void addSquare(Color color) {
            DrawableSquare square = new DrawableSquare(new Point(200, 200), 100, color);
            drawables.addShape(square);
            areaPercentage();
        }
        public void addElipse(Color color) {
            DrawableElipse elipse = new DrawableElipse(new Point(200, 200), 100, 50, color);
            drawables.addShape(elipse);
            areaPercentage();
        }
        public void saveFigures(String filename) {
            drawables.saveToJson(filename);
        }
        public void loadFigures(String filename) {
            drawables.loadFromJson(filename);
            // Actualiza la interfaz de usuario después de cargar las figuras
           areaPercentage();
           repaint();
        }
        private void areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        percentage = areaService.calculatePercentage();

        String message = "Area total " + percentage + "%";
        System.out.println(message); // Imprime en la consola
        updateAreaLabel(message);
        /*if (window.getPanel() != null) {
            window.getPanel().updateAreaLabel(message);
        } else {
            System.err.println("MainPanel en MainWindow es nulo");
        }*/
    }  
        private void initComponents() {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1366, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 726, Short.MAX_VALUE)
            );
        }    
        private void synchronizeSliders() {
            // Implementar la sincronización de sliders si es necesario
        }
         @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // No dibujar figuras en el panel principal
        }
}

    /*
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
*/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

