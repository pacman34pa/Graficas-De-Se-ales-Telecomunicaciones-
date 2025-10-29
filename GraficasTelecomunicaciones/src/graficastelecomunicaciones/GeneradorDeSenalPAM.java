/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GeneradorDeSenalPAM extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final double FREQ = 2;      // Frecuencia de la señal base
    private static final double SAMPLE_RATE = 0.2; // Paso de muestreo (entre más pequeño, más pulsos)

    private java.util.List<Double> sampleTimes = new ArrayList<>();
    private java.util.List<Double> pamValues = new ArrayList<>();

    public GeneradorDeSenalPAM() {
        generarPAM();
    }

    private void generarPAM() {
        // Generar muestras de una señal senoidal
        for (double t = 0; t <= 2 * Math.PI; t += SAMPLE_RATE) {
            sampleTimes.add(t);
            pamValues.add(Math.sin(FREQ * t));
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

        // Dibujar pulsos PAM (verticales)
        g.setColor(Color.RED);
        for (int i = 0; i < pamValues.size(); i++) {
            int x = 50 + (int) (sampleTimes.get(i) / (2 * Math.PI) * (WIDTH - 100));
            int y = (int) (HEIGHT / 2 - pamValues.get(i) * 100);
            g.drawLine(x, HEIGHT / 2, x, y);
            g.fillRect(x - 2, y - 2, 4, 4); // marcador
        }

        g.setColor(Color.BLACK);
        g.drawString("Señal PAM (Pulse Amplitude Modulation)", 100, 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Señal PAM");
        GeneradorDeSenalPAM panel = new GeneradorDeSenalPAM();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
