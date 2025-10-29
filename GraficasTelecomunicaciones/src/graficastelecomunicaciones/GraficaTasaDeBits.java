/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;

/**
 * Demostración gráfica de la relación lineal:
 * Tasa de bits = Tasa de muestreo × Bits por muestra
 */
public class GraficaTasaDeBits extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    // Tasa de muestreo fija (por ejemplo 8 kHz)
    private static final int tasaMuestreo = 8000;

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Márgenes
        int marginLeft = 80;
        int marginRight = 50;
        int marginTop = 70;
        int marginBottom = 80;

        int x0 = marginLeft;
        int x1 = WIDTH - marginRight;
        int y0 = HEIGHT - marginBottom;
        int y1 = marginTop;

        // Ejes
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawLine(x0, y0, x1, y0); // eje X
        g.drawLine(x0, y0, x0, y1); // eje Y

        // Etiquetas de los ejes
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Bits por muestra (n)", (x0 + x1) / 2 - 70, HEIGHT - 35);
        g.drawString("Tasa de bits (bps)", 20, y1 - 10);

        // Título
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Demostración del Teorema de la Tasa de Bits", x0 + 120, marginTop - 30);

        // Rango de valores
        int nMax = 16; // hasta 16 bits
        int tasaMax = tasaMuestreo * nMax; // 8000 * 16 = 128000 bps

        // Dibujar la línea de relación lineal
        g.setColor(new Color(0, 100, 255));
        g.setStroke(new BasicStroke(3));

        int prevX = x0, prevY = y0;
        for (int n = 0; n <= nMax; n++) {
            double tasaBits = tasaMuestreo * n;
            int x = x0 + (int) ((x1 - x0) * n / (double) nMax);
            int y = y0 - (int) ((y0 - y1) * (tasaBits / tasaMax));

            if (n > 0)
                g.drawLine(prevX, prevY, x, y);

            prevX = x;
            prevY = y;
        }

        // Escalas en los ejes
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.setColor(Color.BLACK);

        // Marcas del eje X (bits por muestra)
        for (int n = 0; n <= nMax; n += 2) {
            int x = x0 + (int) ((x1 - x0) * n / (double) nMax);
            g.drawLine(x, y0 - 5, x, y0 + 5);
            g.drawString(String.valueOf(n), x - 5, y0 + 20);
        }

        // Marcas del eje Y (tasa de bits)
        for (int i = 0; i <= 5; i++) {
            int tasa = i * (tasaMax / 5);
            int y = y0 - (int) ((y0 - y1) * (tasa / (double) tasaMax));
            g.drawLine(x0 - 5, y, x0 + 5, y);
            g.drawString(String.valueOf(tasa), x0 - 70, y + 5);
        }

        // Leyenda del comportamiento
        g.setFont(new Font("SansSerif", Font.ITALIC, 13));
        g.setColor(Color.DARK_GRAY);
        g.drawString("La tasa de bits aumenta linealmente con los bits por muestra", x0 + 100, y1 - 25);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demostración gráfica: Tasa de Bits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new GraficaTasaDeBits());
        frame.setVisible(true);
    }
}

