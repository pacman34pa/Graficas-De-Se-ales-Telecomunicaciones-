/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;

/**
 * Ejercicio práctico: Aplicación del Teorema de Nyquist–Shannon
 * Muestra dos señales muestreadas (una correcta, una con aliasing)
 * y la señal analógica original.
 */
public class EjercicioTeoremaNyquist extends JPanel {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;

    private static final double FRECUENCIA_SENAL = 5.0;  // Hz
    private static final double DURACION = 1.0;          // segundos

    // Dos frecuencias de muestreo a comparar
    private static final double FM_CORRECTA = 20.0; // Cumple Nyquist
    private static final double FM_BAJA = 8.0;      // Viola Nyquist

    private static final int MARGIN_LEFT = 80;
    private static final int MARGIN_RIGHT = 50;
    private static final int MARGIN_TOP = 100;   // más margen superior
    private static final int MARGIN_BOTTOM = 70;

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo blanco
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
        g.drawLine(x0, yEjeCero, x1, yEjeCero);
        g.drawLine(x0, yTop, x0, yBottom);
        g.drawString("Tiempo (s)", (x0 + x1) / 2 - 30, yBottom + 40);
        g.drawString("Amplitud", 20, (yTop + yBottom) / 2 - 20);

        // Señal analógica (azul)
        g.setColor(new Color(0, 120, 255));
        g.setStroke(new BasicStroke(2));
        int prevX = x0, prevY = yEjeCero;
        int puntos = 1000;
        double dt = DURACION / puntos;
        for (int i = 0; i <= puntos; i++) {
            double t = i * dt;
            double valor = Math.sin(2 * Math.PI * FRECUENCIA_SENAL * t);
            int x = x0 + (int) ((x1 - x0) * t / DURACION);
            int y = yEjeCero - (int) (valor * 100);
            if (i > 0) g.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }
        g.drawString("Señal analógica (5 Hz)", x1 - 250, yTop + 20);

        // Señal muestreada correcta (rojo)
        dibujarMuestras(g, x0, x1, yEjeCero, FRECUENCIA_SENAL, FM_CORRECTA, Color.RED, "fm=20 Hz (Cumple Nyquist)");

        // Señal muestreada baja (verde)
        dibujarMuestras(g, x0, x1, yEjeCero, FRECUENCIA_SENAL, FM_BAJA, Color.GREEN.darker(), "fm=8 Hz (Aliasing)");

        // ----- TÍTULO -----
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Aplicación del Teorema de Nyquist–Shannon", x0 + 150, yTop - 60);

        // ----- LEYENDA (ahora arriba del gráfico) -----
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));

        int leyendaY = yTop - 35;  // posición arriba del área del gráfico

        // Señal analógica
        g.setColor(new Color(0, 120, 255));
        g.fillRect(x0 + 50, leyendaY - 8, 15, 8);
        g.setColor(Color.BLACK);
        g.drawString("Señal analógica", x0 + 70, leyendaY);

        // Muestreo correcto
        g.setColor(Color.RED);
        g.fillRect(x0 + 220, leyendaY - 8, 15, 8);
        g.setColor(Color.BLACK);
        g.drawString("Muestreo correcto: fm = 10Hz", x0 + 240, leyendaY);

        // Muestreo insuficiente
        g.setColor(Color.GREEN.darker());
        g.fillRect(x0 + 420, leyendaY - 8, 15, 8);
        g.setColor(Color.BLACK);
        g.drawString("Muestreo insuficiente (aliasing): fm = 8Hz", x0 + 440, leyendaY);
    }

    private void dibujarMuestras(Graphics2D g, int x0, int x1, int yEjeCero,
                                 double fSenal, double fMuestreo, Color color, String etiqueta) {
        g.setColor(color);
        double periodoM = 1.0 / fMuestreo;
        int nMuestras = (int) (DURACION / periodoM);

        for (int n = 0; n <= nMuestras; n++) {
            double t = n * periodoM;
            double valor = Math.sin(2 * Math.PI * fSenal * t);
            int x = x0 + (int) ((x1 - x0) * t / DURACION);
            int y = yEjeCero - (int) (valor * 100);

            g.fillOval(x - 3, y - 3, 6, 6);
            g.drawLine(x, yEjeCero, x, y);
        }

        // (no se dibujan etiquetas dentro del gráfico)
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejercicio: Teorema de Nyquist-Shannon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new EjercicioTeoremaNyquist());
        frame.setVisible(true);
    }
}


