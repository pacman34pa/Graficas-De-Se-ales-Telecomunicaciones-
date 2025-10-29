/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GeneradorSenalAnalogicaIPAM extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    // Parámetros de la señal
    private static final double FREQ = 2;      // Frecuencia de la señal analógica
    private static final double SAMPLE_RATE = 0.1;  // Intervalo de muestreo para PAM

    private java.util.List<Double> analogSignal = new ArrayList<>();
    private java.util.List<Double> pamSignal = new ArrayList<>();
    private java.util.List<Double> sampleTimes = new ArrayList<>();

    public GeneradorSenalAnalogicaIPAM() {
        generarSeñales();
    }

    private void generarSeñales() {
        // Generar señal analógica (onda senoidal)
        for (double t = 0; t <= 2 * Math.PI; t += 0.01) {
            analogSignal.add(Math.sin(FREQ * t));
        }

        // Generar PAM (muestreo de amplitud)
        for (double t = 0; t <= 2 * Math.PI; t += SAMPLE_RATE) {
            sampleTimes.add(t);
            pamSignal.add(Math.sin(FREQ * t));
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

        // Dibujar señal analógica
        g.setColor(Color.BLUE);
        int prevX = 0, prevY = 0;
        for (int i = 0; i < analogSignal.size(); i++) {
            int x = 50 + (int) (i * (WIDTH - 100) / (double) analogSignal.size());
            int y = (int) (HEIGHT / 2 - analogSignal.get(i) * 100);
            if (i > 0)
                g.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }

        // Dibujar PAM
        g.setColor(Color.RED);
        for (int i = 0; i < pamSignal.size(); i++) {
            int x = 50 + (int) (sampleTimes.get(i) / (2 * Math.PI) * (WIDTH - 100));
            int y = (int) (HEIGHT / 2 - pamSignal.get(i) * 100);
            g.drawLine(x, HEIGHT / 2, x, y);
            g.fillRect(x - 2, y - 2, 4, 4);  // pequeño marcador
        }

        // Etiquetas
        g.setColor(Color.BLACK);
        g.drawString("Señal Analógica (Azul)", 100, 30);
        g.drawString("Señal PAM (Rojo)", 300, 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Generación de Señal Analógica y PAM");
        GeneradorSenalAnalogicaIPAM panel = new GeneradorSenalAnalogicaIPAM();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
