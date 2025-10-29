/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OndaSenoidal extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final double FREQ = 2; // Frecuencia de la señal

    private java.util.List<Double> signal = new ArrayList<>();

    public OndaSenoidal() {
        generarSeñal();
    }

    private void generarSeñal() {
        // Generar una señal senoidal simple
        for (double t = 0; t <= 2 * Math.PI; t += 0.01) {
            signal.add(Math.sin(FREQ * t));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Ejes
        g.setColor(Color.GRAY);
        g.drawLine(50, HEIGHT / 2, WIDTH - 50, HEIGHT / 2);
        g.drawLine(50, 50, 50, HEIGHT - 50);

        // Señal analógica
        g.setColor(Color.BLUE);
        int prevX = 0, prevY = 0;
        for (int i = 0; i < signal.size(); i++) {
            int x = 50 + (int) (i * (WIDTH - 100) / (double) signal.size());
            int y = (int) (HEIGHT / 2 - signal.get(i) * 100);
            if (i > 0)
                g.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }

        g.setColor(Color.BLACK);
        g.drawString("Señal Analógica (Onda Senoidal)", 100, 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Señal Analógica");
        OndaSenoidal panel = new OndaSenoidal();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}



