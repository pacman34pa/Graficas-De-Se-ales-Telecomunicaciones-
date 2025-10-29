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
 * Señal PCM (Pulse Code Modulation)
 * Dibuja la representación digital de una señal cuantificada,
 * mostrando la conversión a binario (8 bits por muestra)
 * y la forma de onda digital.
 */
public class SenalPCM extends JPanel {

    // ----- CONFIGURACIÓN GENERAL -----
    private static final int WIDTH = 950;
    private static final int HEIGHT = 500;

    // Valores cuantificados de entrada (ejemplo)
    private static final List<Integer> valores = Arrays.asList(+28, +25, +15, +25, -15, -77, +15, +89);

    // Número de bits por muestra
    private static final int BITS = 8;

    // Márgenes de dibujo
    private static final int MARGIN_LEFT = 80;
    private static final int MARGIN_RIGHT = 50;
    private static final int MARGIN_TOP = 70;
    private static final int MARGIN_BOTTOM = 80;

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Área útil
        int x0 = MARGIN_LEFT;
        int x1 = WIDTH - MARGIN_RIGHT;
        int yTop = MARGIN_TOP;
        int yBottom = HEIGHT - MARGIN_BOTTOM;
        int yEje = (yTop + yBottom) / 2;

        // ----- Ejes -----
        g.setColor(Color.BLACK);
        g.drawLine(x0, yEje, x1, yEje); // eje del tiempo
        g.drawLine(x0, yTop, x0, yBottom); // eje de amplitud

        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Señal PCM (Pulse Code Modulation)", x0 + 80, yTop - 20);

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("Tiempo (bits)", (x0 + x1) / 2 - 40, yBottom + 40);
        g.drawString("Nivel lógico", 20, (yTop + yBottom) / 2 - 20);

        // ----- Escalado -----
        int min = valores.stream().min(Integer::compareTo).get();
        int max = valores.stream().max(Integer::compareTo).get();
        double escala = 255.0 / (max - min);

        // Codificación binaria (8 bits)
        String[] codigos = new String[valores.size()];
        for (int i = 0; i < valores.size(); i++) {
            int nivel = (int) ((valores.get(i) - min) * escala); // normalización
            codigos[i] = String.format("%8s", Integer.toBinaryString(nivel)).replace(' ', '0');
        }

        // ----- Dibujo de la señal PCM -----
        int anchoBit = (x1 - x0) / (valores.size() * (BITS + 2));
        int nivelAlto = yEje - 50;
        int nivelBajo = yEje + 50;
        int x = x0 + 30;

        g.setFont(new Font("Monospaced", Font.PLAIN, 11));

        for (int i = 0; i < valores.size(); i++) {
            String bits = codigos[i];
            int xBit = x;

            // Texto informativo
            g.setColor(Color.BLACK);
            g.drawString(String.format("M%d: %+3d → %s", i + 1, valores.get(i), bits), x - 10, yBottom + 25);

            // Dibuja cada bit como un pulso rectangular
            for (int j = 0; j < bits.length(); j++) {
                char bit = bits.charAt(j);
                int yNivel = (bit == '1') ? nivelAlto : nivelBajo;

                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(2));
                g.drawLine(xBit, yNivel, xBit + anchoBit, yNivel);

                // línea vertical si cambia el nivel
                if (j > 0 && bits.charAt(j) != bits.charAt(j - 1)) {
                    g.setColor(Color.GRAY);
                    g.drawLine(xBit, nivelAlto - 10, xBit, nivelBajo + 10);
                }

                // marca el bit
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(bit), xBit + anchoBit / 4, nivelBajo + 20);

                xBit += anchoBit;
            }

            x += (BITS + 3) * anchoBit; // separación entre muestras
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Señal PCM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(new SenalPCM());
        frame.setVisible(true);
    }
}
