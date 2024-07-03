package views;

import DAO.DrawablesDao;
import Drawable.Drawable;
import Drawable.DrawableCircle;
import Drawable.DrawableElipse;
import Drawable.DrawableRectangle;
import Drawable.DrawableSquare;
import controllers.MainController;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import models.Point;
import services.AreaCalculator;


/**
 *
 * @author ESTUDIANTE
 */
public class MainPanel extends javax.swing.JPanel {
    private  DrawablesDao drawables;
    private final JLabel areaLabel;
    private final JLabel selectedLabel; // Label para mostrar la figura seleccionada
    private Drawable selectedDrawable; // Figura seleccionada
    private MainController controller;
    private int prevX, prevY;
    private JButton rotateButton;
    private JButton deleteSelectedButton;
    private JButton deleteAllButton;
    private JLabel dibujarFigurasLabel; // Nuevo JLabel para dibujar figuras
    private JLabel lblImagen; // Etiqueta para la imagen
    private JLayeredPane layeredPane; // Panel de capas para manejar la superposición
    private JButton selectImageButton; // Botón para seleccionar imagen
    private JRadioButton redRadioButton;
    private JRadioButton blueRadioButton;
    private JSlider sizeSlider;
     private  MainWindow window;
    private final AreaCalculator areaService;
    
    public MainPanel() {
        
        initComponents();
        this.setSize(1366,726);
       drawables= new DrawablesDao ();
       
        this.setLayout(null);
        this.areaLabel = new JLabel("Area total: 0% :/");
        this.areaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.areaLabel.setForeground(Color.BLACK);
        this.add(areaLabel);  // Agregar el JLabel al panel
        this.areaLabel.setBounds(20, 20, 300, 30);
         areaService = new AreaCalculator();
        // Etiqueta para la figura seleccionada
        this.selectedLabel = new JLabel("Figura seleccionada: Ninguna");
        this.selectedLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        this.selectedLabel.setForeground(Color.RED); // Color opcional para distinguir
        this.add(selectedLabel);
        this.selectedLabel.setBounds(20, 50, 300, 30);
        
        // Configurar el JLabel para dibujar figuras
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
        dibujarFigurasLabel.setSize(800, 400); // Tamaño del JLabel
        dibujarFigurasLabel.setLocation(100, 100); // Posición del JLabel dentro del panel
        this.add(dibujarFigurasLabel); // Agregar el JLabel al panel principal
        
        
        lblImagen = new JLabel();
        lblImagen.setBounds(0, 0, 1366, 726); // Establece los límites para llenar el panel

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 726); // Establece los límites para llenar el panel
        this.add(lblImagen);
         // Agrega los JLabels al JLayeredPane en diferentes capas
        //layeredPane.add(lblImagen, JLayeredPane.DEFAULT_LAYER); // Capa inferior para la imagen
        //layeredPane.add(dibujarFigurasLabel, JLayeredPane.PALETTE_LAYER); // Capa superior para las figuras

        // Crea y configura el botón para seleccionar imagen
        selectImageButton = new JButton("Seleccionar Imagen");
        selectImageButton.setBounds(800, 100, 250, 50); // Posición y tamaño del botón
        selectImageButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(); // Llama al método para seleccionar imagen
            }
        });
         this.add(selectImageButton);
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
                double factor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;
                resizeShape(factor);
                synchronizeSliders();
            }
        });
        
        rotateButton = new JButton("Rotar Figura");
        rotateButton.setBounds(800, 530, 150, 30);
        rotateButton.addActionListener(e -> {
            rotateShape();
            synchronizeSliders(); // Actualizar los sliders después de la rotación
        });
        
        this.add(rotateButton);
        
        deleteSelectedButton = new JButton("Eliminar Figura Seleccionada");
        deleteSelectedButton.setBounds(800, 580, 200, 30);
        deleteSelectedButton.addActionListener(e -> {
            deleteSelectedShape();
        });
        
        this.add(deleteSelectedButton); 
        
        deleteAllButton = new JButton("Eliminar Todas las Figuras");
         deleteAllButton.setBounds(800, 200, 200, 30);
        deleteAllButton.addActionListener(e -> {
            deleteAllShapes();
        });
       
        this.add(deleteAllButton);
        
        /* Guardar y Cargar */
        
        JButton saveButton = new JButton("Guardar Figuras");
         saveButton.setBounds(1000, 530, 150, 30);
        saveButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                saveFigures(selectedFile.getAbsolutePath());
            }
        });
       
        this.add(saveButton);

        JButton loadButton = new JButton("Cargar Figuras");
        loadButton.setBounds(100, 600, 150, 30);
        loadButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadFigures(selectedFile.getAbsolutePath());
            }
        });
        
        this.add(loadButton);
        
        // this.controller = controller;
           
      

        JButton addCircleButton = new JButton("Add Circle");
        addCircleButton.setBounds(200, 600, 150, 30);
            addCircleButton.addActionListener((ActionEvent e) -> {
                Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addCircle(color); // Llamar al método con el color seleccionado
                repaint();
        });
        
        JButton addRectangleButton = new JButton("Add Rectangle");
        addRectangleButton.setBounds(300, 600, 150, 30);
        addRectangleButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addRectangle(color); // Llamar al método con el color seleccionado
                repaint();
        });

        JButton addSquareButton = new JButton("Add Square");
        addSquareButton.setBounds(400, 600, 150, 30);
        addSquareButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addSquare(color); // Llamar al método con el color seleccionado
                repaint();
        });
        
        JButton addElipseButton = new JButton("Add Elipse");
        addElipseButton.setBounds(500, 600, 150, 30);
        addElipseButton.addActionListener((ActionEvent e) -> {
            Color color = blueRadioButton.isSelected() ? Color.BLUE : Color.RED; // Obtener el color seleccionado
                addElipse(color); // Llamar al método con el color seleccionado
                repaint();
        });

        // Agrupar los RadioButtons para el color
        redRadioButton = new JRadioButton("Rojo");
        redRadioButton.setBounds(800,300,150,30);
        redRadioButton.setSelected(false);
        redRadioButton.addActionListener(e -> {
            if (redRadioButton.isSelected()) {
                blueRadioButton.setSelected(false);
            }
        });

        blueRadioButton = new JRadioButton("Azul");
        blueRadioButton.setBounds(800,350,150,30);
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
            resizeShape(value / 100.0);
        });
             
        // Agregar RadioButtons al panel
        //JPanel radioButtonPanel = new JPanel();
        this.add(redRadioButton);
        this.add(blueRadioButton);
        
        this.add(addCircleButton);
       this.add(addRectangleButton);
         this.add(addSquareButton);
         this.add(addElipseButton);
         this.add(sizeSlider);
       
        // Agregar componentes al JFrame
       // this.add(radioButtonPanel, BorderLayout.NORTH);
     //   this.add(panel, BorderLayout.CENTER);

     
    }

    private void deleteSelectedShape() {
        if (selectedDrawable != null) {
            drawables.remove(selectedDrawable); // Elimina la figura de la lista
            selectedDrawable = null;
            updateSelectedLabel("Ninguna figura seleccionada");
            repaint();
        }
    }
    
    private void deleteAllShapes() {
        drawables.clear(); // Borra todas las figuras de la lista
        selectedDrawable = null;
        updateSelectedLabel("Ninguna figura seleccionada");
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
                int newRadius = (int) (circle.getRadius() * factor);
                circle.setRadius(Math.max(10, Math.min(200, newRadius))); // Limitar el tamaño entre 10 y 200
            } else if (selectedDrawable instanceof Rectangle rectangle) {
                int newWidth = (int) (rectangle.getWidth() * factor);
                int newHeight = (int) (rectangle.getHeight() * factor);
                rectangle.setWidth(Math.max(10, Math.min(200, newWidth)));
                rectangle.setHeight(Math.max(20, Math.min(400, newHeight)));
            } else if (selectedDrawable instanceof Square square) {
                int newSide = (int) (square.getSide() * factor);
                square.setSide(Math.max(10, Math.min(200, newSide)));
            } else if (selectedDrawable instanceof Elipse elipse) {
                int newSemiMajorAxis = (int) (elipse.getSemiMajorAxis() * factor);
                int newSemiMinorAxis = (int) (elipse.getSemiMinorAxis() * factor);
                elipse.setSemiMajorAxis(Math.max(10, Math.min(200, newSemiMajorAxis)));
                elipse.setSemiMinorAxis(Math.max(10, Math.min(200, newSemiMinorAxis)));
            }
            repaint();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // No dibujar figuras en el panel principal
    }

    public void addCircle(Color color) {
        DrawableCircle circle = new DrawableCircle(new Point(100, 100), 50, color);
        drawables.addShape(circle);
        updateUIAfterLoading();
        //areaPercentage();
    }

    public void addRectangle(Color color) {
        DrawableRectangle rectangle = new DrawableRectangle(new Point(150, 150), 100, 200, color);
        drawables.addShape(rectangle);
        updateUIAfterLoading();
        //areaPercentage();
    }

    public void addSquare(Color color) {
        DrawableSquare square = new DrawableSquare(new Point(200, 200), 100, color);
        drawables.addShape(square);
        updateUIAfterLoading();
        //areaPercentage();
    }

    public void addElipse(Color color) {
        DrawableElipse elipse = new DrawableElipse(new Point(200, 200), 100, 50, color);
        drawables.addShape(elipse);
        updateUIAfterLoading();
        //areaPercentage();
    }
    
     public void saveFigures(String filename) {
        drawables.saveToJson(filename);
    }

    public void loadFigures(String filename) {
        drawables.loadFromJson(filename);
        // Actualiza la interfaz de usuario después de cargar las figuras
        updateUIAfterLoading();
    }
    
    private void updateUIAfterLoading() {
        // Aquí puedes actualizar el área y redibujar las figuras en el panel
        String message = areaPercentage();

    }

    public String areaPercentage() {
        List<Shape> shapes = drawables.getShapes();
        areaService.updateAreas(shapes);
        double percentage = areaService.calculatePercentage();
        return "Area total " + percentage + "%";
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

