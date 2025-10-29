/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Señal PAM cuantificada
 * Muestra las amplitudes dadas como barras conectadas tipo escalonadas.
 * Ejes marcados correctamente con etiquetas y valores.
 */
public class SenalPAMCuantificada extends JPanel {

    // ----- CONFIGURACIÓN GENERAL -----
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;

    // Valores de amplitud (cuantificados)
    private static final List<Integer> valoresPAM = Arrays.asList(+28, +25, +15, +25, -15, -77, +15, +89);

    // Escala para ajustar visualmente la amplitud
    private static final double ESCALA_Y = 2.0;

    // Márgenes
    private static final int MARGIN_LEFT = 80;
    private static final int MARGIN_RIGHT = 60;
    private static final int MARGIN_TOP = 60;
    private static final int MARGIN_BOTTOM = 80;

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo blanco
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Cálculo de área de dibujo
        int x0 = MARGIN_LEFT;
        int x1 = WIDTH - MARGIN_RIGHT;
        int yTop = MARGIN_TOP;
        int yBottom = HEIGHT - MARGIN_BOTTOM;
        int yEjeCero = (yTop + yBottom) / 2;

        // Fondo de la zona de señal
        g.setColor(new Color(240, 240, 240));
        g.fillRect(x0, yTop, x1 - x0, yBottom - yTop);

        // ----- EJE DE AMPLITUD (Vertical) -----
        g.setColor(Color.BLACK);
        g.drawLine(x0, yTop, x0, yBottom);
        g.drawString("Amplitud", 15, (yTop + yBottom) / 2 - 20);

        // Marcas del eje Y
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        for (int i = -100; i <= 100; i += 25) {
            int y = yEjeCero - (int) (i * ESCALA_Y);
            if (y >= yTop && y <= yBottom) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(x0, y, x1, y); // línea horizontal
                g.setColor(Color.BLACK);
                g.drawString(String.format("%+d", i), x0 - 35, y + 4);
            }
        }

        // ----- EJE DE TIEMPO (Horizontal) -----
        g.setColor(Color.BLACK);
        g.drawLine(x0, yEjeCero, x1, yEjeCero);
        g.drawString("Tiempo (muestras)", (x0 + x1) / 2 - 60, yBottom + 40);

        // Escalado horizontal
        int n = valoresPAM.size();
        int anchoPaso = (x1 - x0) / (n + 1);

        // ----- DIBUJAR SEÑAL PAM CUANTIFICADA -----
        g.setColor(new Color(200, 0, 0));
        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < n; i++) {
            int valor = valoresPAM.get(i);
            int x = x0 + (i + 1) * anchoPaso;
            int y = yEjeCero - (int) (valor * ESCALA_Y);

            // Dibuja la barra vertical (pulso)
            g.drawLine(x, yEjeCero, x, y);

            // Conecta con la siguiente muestra
            if (i < n - 1) {
                int siguiente = valoresPAM.get(i + 1);
                int xNext = x0 + (i + 2) * anchoPaso;
                int yNext = yEjeCero - (int) (siguiente * ESCALA_Y);
                g.drawLine(x, y, xNext, yNext);
            }

            // Coordenadas de cada muestra
            g.setColor(Color.BLUE);
            g.fillOval(x - 3, y - 3, 6, 6);
            g.setColor(Color.BLACK);
            g.drawString(String.format("%+d", valor), x - 12, y - 8);

            // Etiqueta de muestra
            g.drawString("n=" + (i + 1), x - 10, yBottom + 20);
        }

        // ----- TÍTULO -----
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Señal PAM Cuantificada", x0 + 60, yTop - 20);
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g.drawString("Valores: " + valoresPAM, x0 + 60, yTop - 5);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Señal PAM Cuantificada (Ejes marcados)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new SenalPAMCuantificada());
        frame.setVisible(true);
    }
}
