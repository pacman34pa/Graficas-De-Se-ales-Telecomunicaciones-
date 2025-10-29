/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficastelecomunicaciones;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * SenalAnalogicaPAM_StepByStep
 *
 * Programa que:
 * 1) Plantea el problema (x(t)=sin(2t), intervalo 0..2pi, paso muestreo = 0.4 rad)
 * 2) Genera la señal analógica (resolucion DT)
 * 3) Toma muestras uniformes (SAMPLE_RATE) <-- esto define la PAM
 * 4) Muestra en consola cada paso y las coordenadas muestreadas
 * 5) Dibuja la señal y la PAM con ejes y coordenadas marcadas
 *
 * Evita acentos en nombres para compatibilidad con compiladores que no los aceptan.
 */
public class SenalAnalogicaPAM_StepByStep extends JPanel {

    // ----- CONFIGURACION -----
    private static final int WIDTH = 900;
    private static final int HEIGHT = 520;
    private static final double FREQ = 2.0;         // frecuencia en radianes usada en x(t)=sin(FREQ*t)
    private static final double SAMPLE_RATE = 0.4;  // paso de muestreo (radianes)
    private static final double DT = 0.01;          // resolucion para dibujar la senal continua
    private static final double T_MAX = 2.0 * Math.PI;

    // margenes grafico
    private static final int MARGIN_LEFT = 80;
    private static final int MARGIN_RIGHT = 40;
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 80;

    // datos
    private final List<Double> analogTimes = new ArrayList<>();
    private final List<Double> analogValues = new ArrayList<>();
    private final List<Double> sampleTimes = new ArrayList<>();
    private final List<Double> sampleValues = new ArrayList<>();

    public SenalAnalogicaPAM_StepByStep() {
        // Ejecutar paso a paso (imprime en consola y llena estructuras)
        paso1_PlantearProblema();
        paso2_GenerarSenalAnalogica();
        paso3_TomarMuestras();
        paso4_ImprimirMuestras();
    }

    // ------------------ PASOS ------------------

    private void paso1_PlantearProblema() {
        System.out.println("PASO 1: Planteamiento del problema");
        System.out.println(" - Señal continua: x(t) = sin(" + FREQ + " t)");
        System.out.println(" - Intervalo: 0 <= t <= 2π (2*PI)");
        System.out.println(" - Resolucion para dibujar (DT) = " + DT + " rad");
        System.out.println(" - Paso de muestreo (SAMPLE_RATE) = " + SAMPLE_RATE + " rad");
        System.out.println();
    }

    private void paso2_GenerarSenalAnalogica() {
        System.out.println("PASO 2: Generacion de la senal analogica (muestreo interno para dibujar)");
        analogTimes.clear();
        analogValues.clear();
        for (double t = 0.0; t <= T_MAX + 1e-9; t += DT) {
            analogTimes.add(t);
            analogValues.add(Math.sin(FREQ * t));
        }
        System.out.println(" - Puntos generados para la senal continua: " + analogTimes.size());
        System.out.println();
    }

    private void paso3_TomarMuestras() {
        System.out.println("PASO 3: Muestreo uniforme para generar PAM");
        sampleTimes.clear();
        sampleValues.clear();
        for (double t = 0.0; t <= T_MAX + 1e-9; t += SAMPLE_RATE) {
            sampleTimes.add(t);
            sampleValues.add(Math.sin(FREQ * t));
        }
        System.out.println(" - Numero de muestras tomadas: " + sampleTimes.size());
        System.out.println();
    }

    private void paso4_ImprimirMuestras() {
        System.out.println("PASO 4: Lista de muestras (t, x(t)) con formato (t en rad, x(t)):");
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("   index\t t\t\t x(t)");
        for (int i = 0; i < sampleTimes.size(); i++) {
            System.out.printf("   %2d\t (%.2f,\t %+.2f)%n", i, sampleTimes.get(i), sampleValues.get(i));
        }
        System.out.println();
        System.out.println("PASO 5: Generacion de la señal PAM (cada muestra sera representada por un pulso vertical).");
        System.out.println("Ahora se mostrara una ventana grafica con la senal y las coordenadas marcadas.");
        System.out.println("--------------------------------------------------------------");
    }

    // ------------------ UTILIDADES GRAFICAS ------------------

    // mapa t -> x en pixeles (dentro del area util)
    private int toX(double t) {
        double usableW = WIDTH - MARGIN_LEFT - MARGIN_RIGHT;
        return MARGIN_LEFT + (int) Math.round(t / T_MAX * usableW);
    }

    // mapa y (-1..1) -> y en pixeles (dentro del area util)
    private int toY(double y) {
        double usableH = HEIGHT - MARGIN_TOP - MARGIN_BOTTOM;
        return MARGIN_TOP + (int) Math.round((1.0 - y) / 2.0 * usableH);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // cuadro de grafico
        int x0 = MARGIN_LEFT;
        int x1 = WIDTH - MARGIN_RIGHT;
        int yTop = MARGIN_TOP;
        int yBottom = HEIGHT - MARGIN_BOTTOM;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x0, yTop, x1 - x0, yBottom - yTop);
        g.setColor(Color.BLACK);
        g.drawRect(x0, yTop, x1 - x0, yBottom - yTop);

        // eje horizontal y vertical
        int yEjeCero = toY(0.0);
        g.setColor(Color.DARK_GRAY);
        g.drawLine(x0, yEjeCero, x1, yEjeCero); // eje X (t)
        g.drawLine(x0, yTop, x0, yBottom); // eje Y (amplitud)

        // etiquetas y ticks en eje X (0, pi/2, pi, 3pi/2, 2pi)
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        double[] xTicks = {0, Math.PI / 2.0, Math.PI, 3.0 * Math.PI / 2.0, 2.0 * Math.PI};
        String[] xLabels = {"0", "π/2", "π", "3π/2", "2π"};
        for (int i = 0; i < xTicks.length; i++) {
            int xt = toX(xTicks[i]);
            g.setColor(Color.BLACK);
            g.drawLine(xt, yEjeCero - 6, xt, yEjeCero + 6);
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(xLabels[i]);
            g.drawString(xLabels[i], xt - w / 2, yEjeCero + 20);
        }
        g.drawString("t (rad)", (x0 + x1) / 2 - 20, HEIGHT - 40);

        // ticks eje Y
        double[] yTicks = {-1.0, -0.5, 0.0, 0.5, 1.0};
        DecimalFormat df = new DecimalFormat("0.00");
        for (double ytVal : yTicks) {
            int yt = toY(ytVal);
            g.setColor(Color.BLACK);
            g.drawLine(x0 - 6, yt, x0 + 6, yt);
            String lab = df.format(ytVal);
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(lab);
            g.drawString(lab, x0 - 10 - w, yt + fm.getAscent() / 2 - 2);
        }
        g.drawString("Amplitud", 10, (yTop + yBottom) / 2);

        // cuadricula ligera
        g.setColor(new Color(200, 200, 200));
        for (double ytVal : yTicks) {
            int yt = toY(ytVal);
            g.drawLine(x0 + 1, yt, x1 - 1, yt);
        }

        // dibujar senal analogica (azul)
        g.setColor(new Color(30, 100, 200));
        int prevX = -1, prevY = -1;
        for (int i = 0; i < analogTimes.size(); i++) {
            int xi = toX(analogTimes.get(i));
            int yi = toY(analogValues.get(i));
            if (prevX != -1) g.drawLine(prevX, prevY, xi, yi);
            prevX = xi;
            prevY = yi;
        }

        // dibujar PAM (muestras) en rojo y etiquetar coordenadas
        g.setColor(new Color(200, 40, 40));
        g.setFont(new Font("Monospaced", Font.PLAIN, 11));
        for (int i = 0; i < sampleTimes.size(); i++) {
            double t = sampleTimes.get(i);
            double v = sampleValues.get(i);
            int xs = toX(t);
            int ys = toY(v);

            // pulso vertical desde eje 0 hasta la muestra
            g.drawLine(xs, yEjeCero, xs, ys);
            // marcador cuadrado
            g.fillRect(xs - 3, ys - 3, 6, 6);

            // coordenadas
            String coord = String.format("(%.2f, %.2f)", t, v);
            int tx = xs + 6;
            int ty = ys - 8;
            if (ty < yTop + 12) ty = ys + 16;
            if (tx + g.getFontMetrics().stringWidth(coord) > x1) tx = xs - g.getFontMetrics().stringWidth(coord) - 6;
            g.drawString(coord, tx, ty);
        }

        // leyenda e info parametros
        g.setColor(Color.BLACK);
        
        g.drawString(String.format("x(t)=sin(%.2ft)    Paso muestreo=%.2f rad    DT=%.3f", FREQ, SAMPLE_RATE, DT),
                x0 + 2, HEIGHT - 20);
    }

    // --------------- MAIN ---------------
    public static void main(String[] args) {
        // Crear panel (esto ejecuta los pasos y las impresiones en consola)
        SenalAnalogicaPAM_StepByStep panel = new SenalAnalogicaPAM_StepByStep();

        // Mostrar ventana grafica
        JFrame frame = new JFrame("Muestreo paso a paso - Senal Analogica y PAM");
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
