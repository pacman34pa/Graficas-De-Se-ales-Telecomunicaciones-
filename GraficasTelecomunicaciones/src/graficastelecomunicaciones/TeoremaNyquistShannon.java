/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;

import javax.swing.*;
import java.awt.*;

/**
 * Visualización del Teorema de Nyquist-Shannon (Teorema del Muestreo)
 * Muestra una señal analógica senoidal y su versión muestreada.
 */
public class TeoremaNyquistShannon extends JPanel {

    // ----- CONFIGURACIÓN GENERAL -----
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;

    // Parámetros de la señal
    private static final double FRECUENCIA_SEÑAL = 5.0;     // Hz (frecuencia de la señal original)
    private static final double FRECUENCIA_MUESTREO = 20.0; // Hz (debe ser ≥ 2 * FRECUENCIA_SEÑAL)
    private static final double DURACION = 1.0;             // segundos

    // Márgenes
    private static final int MARGIN_LEFT = 80;
    private static final int MARGIN_RIGHT = 50;
    private static final int MARGIN_TOP = 70;
    private static final int MARGIN_BOTTOM = 70;

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Área de dibujo
        int x0 = MARGIN_LEFT;
        int x1 = WIDTH - MARGIN_RIGHT;
        int yTop = MARGIN_TOP;
        int yBottom = HEIGHT - MARGIN_BOTTOM;
        int yEjeCero = (yTop + yBottom) / 2;

        // Ejes
        g.setColor(Color.BLACK);
        g.drawLine(x0, yEjeCero, x1, yEjeCero); // eje de tiempo
        g.drawLine(x0, yTop, x0, yBottom); // eje de amplitud
        g.drawString("Tiempo (s)", (x0 + x1) / 2 - 30, yBottom + 40);
        g.drawString("Amplitud", 20, (yTop + yBottom) / 2 - 20);

        // ----- SEÑAL ANALÓGICA -----
        g.setColor(new Color(0, 120, 255));
        g.setStroke(new BasicStroke(2));

        int puntos = 1000;
        double dt = DURACION / puntos;
        int prevX = x0;
        int prevY = yEjeCero;

        for (int i = 0; i <= puntos; i++) {
            double t = i * dt;
            double valor = Math.sin(2 * Math.PI * FRECUENCIA_SEÑAL * t);
            int x = x0 + (int) ((x1 - x0) * t / DURACION);
            int y = yEjeCero - (int) (valor * 100);

            if (i > 0) {
                g.drawLine(prevX, prevY, x, y);
            }
            prevX = x;
            prevY = y;
        }

        g.setColor(new Color(0, 120, 255));
        g.drawString("Señal analógica continua", x1 - 250, yTop + 20);

        // ----- SEÑAL MUESTREADA -----
        g.setColor(Color.RED);
        double periodoMuestreo = 1.0 / FRECUENCIA_MUESTREO;
        int nMuestras = (int) (DURACION / periodoMuestreo);

        for (int n = 0; n <= nMuestras; n++) {
            double t = n * periodoMuestreo;
            double valor = Math.sin(2 * Math.PI * FRECUENCIA_SEÑAL * t);
            int x = x0 + (int) ((x1 - x0) * t / DURACION);
            int y = yEjeCero - (int) (valor * 100);

            // Dibujar muestra como punto y línea punteada
            g.fillOval(x - 4, y - 4, 8, 8);
            g.drawLine(x, yEjeCero, x, y);

            // Etiqueta del valor
            g.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g.drawString(String.format("n=%d", n), x - 10, yBottom - 20);
        }

        g.setColor(Color.RED);
        g.drawString(String.format("Señal muestreada (fm = %.1f Hz)", FRECUENCIA_MUESTREO), x1 - 250, yTop + 40);

        // ----- TÍTULO -----
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Teorema de Nyquist-Shannon (Muestreo de Señales)", x0 + 100, yTop - 25);

        // ----- LEYENDA -----
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.setColor(new Color(0, 120, 255));
        g.fillRect(x0 + 100, yBottom + 10, 15, 8);
        g.setColor(Color.BLACK);
        g.drawString("Señal Analógica", x0 + 120, yBottom + 20);

        g.setColor(Color.RED);
        g.fillRect(x0 + 280, yBottom + 10, 15, 8);
        g.setColor(Color.BLACK);
        g.drawString("Muestras (discretas)", x0 + 300, yBottom + 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Teorema de Nyquist-Shannon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new TeoremaNyquistShannon());
        frame.setVisible(true);
    }
}
