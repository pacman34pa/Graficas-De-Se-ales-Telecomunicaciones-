/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;

/**
 * Representación gráfica de la Tasa de Bits:
 * Fórmula: tasaBits = tasaMuestreo * bitsPorMuestra
 */
public class TasaDeBits extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int tasaMuestreo = 8000; // 8 kHz

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

        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Bits por muestra (n)", (x0 + x1) / 2 - 60, HEIGHT - 35);
        g.drawString("Tasa de bits (bps)", 15, y1 - 10);

        // Título
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Relación entre Bits por muestra y Tasa de Bits", x0 + 100, marginTop - 25);

        // Escalas
        int nMax = 16;
        int tasaMax = tasaMuestreo * nMax; // 8000 * 16 = 128000

        // Dibujar curva lineal
        g.setColor(new Color(0, 120, 255));
        g.setStroke(new BasicStroke(2));

        int prevX = x0, prevY = y0;
        for (int n = 1; n <= nMax; n++) {
            double tasaBits = tasaMuestreo * n;
            int x = x0 + (int) ((x1 - x0) * n / (double) nMax);
            int y = y0 - (int) ((y0 - y1) * (tasaBits / tasaMax));

            if (n > 1)
                g.drawLine(prevX, prevY, x, y);

            // Punto y etiqueta
            g.fillOval(x - 3, y - 3, 6, 6);
            g.drawString(String.valueOf(n), x - 3, y0 + 20);
            g.drawString(String.format("%.0f", tasaBits), x - 15, y - 10);

            prevX = x;
            prevY = y;
        }

        // Etiquetas en Y
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (int i = 0; i <= 5; i++) {
            int tasa = i * (tasaMax / 5);
            int y = y0 - (int) ((y0 - y1) * (tasa / (double) tasaMax));
            g.drawLine(x0 - 5, y, x0 + 5, y);
            g.drawString(String.valueOf(tasa), x0 - 60, y + 5);
        }

        // Resaltar un punto de ejemplo (n=8)
        int nEjemplo = 8;
        double tasaEjemplo = tasaMuestreo * nEjemplo;
        int xE = x0 + (int) ((x1 - x0) * nEjemplo / (double) nMax);
        int yE = y0 - (int) ((y0 - y1) * (tasaEjemplo / tasaMax));
        g.setColor(Color.RED);
        g.fillOval(xE - 6, yE - 6, 12, 12);
        g.setColor(Color.BLACK);
        g.drawString("Ejemplo: n = 8 bits → 64,000 bps", xE - 200, yE - 45);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tasa de Bits - Relación con bits por muestra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new TasaDeBits());
        frame.setVisible(true);
    }
}
