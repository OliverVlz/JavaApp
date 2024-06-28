import javax.swing.*;
import java.awt.*;

public class DrawingExample extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar un rectángulo
        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new DrawingExample());
        frame.setVisible(true);
    }
}
