/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;

/**
 * Ejercicio: Relación entre bits por muestra y niveles de cuantificación
 * Fórmula: N = 2^n
 */
public class BitsPorMuestra extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        int marginLeft = 80;
        int marginRight = 50;
        int marginTop = 80;
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
        g.drawString("n (bits por muestra)", (x0 + x1) / 2 - 70, HEIGHT - 30);
        g.drawString("N (niveles de cuantificación)", 10, y1 - 10);

        // Título
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Relación entre Bits por Muestra y Niveles de Cuantificación", x0 + 60, marginTop - 30);

        // Escalas
        int nMax = 10;
        double Nmax = Math.pow(2, nMax);

        // Dibujar curva N = 2^n
        g.setColor(new Color(0, 100, 255));
        g.setStroke(new BasicStroke(2));

        int prevX = x0, prevY = y0;
        for (int n = 0; n <= nMax; n++) {
            double N = Math.pow(2, n);
            int x = x0 + (int) ((x1 - x0) * n / (double) nMax);
            int y = y0 - (int) ((y0 - y1) * (N / Nmax));
            if (n > 0) g.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }

        // Ejes de valores (marcas)
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (int n = 0; n <= nMax; n++) {
            int x = x0 + (int) ((x1 - x0) * n / (double) nMax);
            g.drawLine(x, y0 - 5, x, y0 + 5);
            g.drawString(String.valueOf(n), x - 5, y0 + 20);
        }

        // Marcar niveles representativos
        for (int N = 0; N <= Nmax; N *= 2) {
            if (N == 0) N = 1;
            int y = y0 - (int) ((y0 - y1) * (N / Nmax));
            g.drawLine(x0 - 5, y, x0 + 5, y);
            g.drawString(String.valueOf(N), x0 - 45, y + 5);
        }

        // Punto específico: n=8, N=256
        int nPunto = 8;
        double NPunto = 256;
        int xP = x0 + (int) ((x1 - x0) * nPunto / (double) nMax);
        int yP = y0 - (int) ((y0 - y1) * (NPunto / Nmax));

        g.setColor(Color.RED);
        g.fillOval(xP - 5, yP - 5, 10, 10);

        // Etiqueta del punto
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString("n = 8 bits → N = 256 niveles", xP - 100, yP - 15);
        g.drawLine(xP, yP, xP - 20, yP - 10);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Relación Bits por muestra - Niveles de cuantificación");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new BitsPorMuestra());
        frame.setVisible(true);
    }
}
