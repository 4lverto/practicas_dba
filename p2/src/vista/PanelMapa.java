package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import modelo.Entorno;
import modelo.Mapa;

public class PanelMapa extends JPanel {
    private Image imagenLibre;
    private Image imagenObstaculo;
    private Image imagenObjetivo;
    private Image imagenVisitada;
    private Image imagenAgente;

    private static final int FACTOR = 78;
    private Mapa mapa;


    // Componentes para la información adicional
    private JLabel etiquetaPosicion;
    private JLabel etiquetaCasillasVisitadas;
    private JLabel etiquetaEnergiaGastada;

    public PanelMapa(Entorno entorno) {
        this.mapa = entorno.obtenerMapa();

        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY); // Fondo general oscuro

        // Panel para el mapa
        JPanel panelMapa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                pintarMapa(g);
            }
        };

        panelMapa.setPreferredSize(new Dimension(
                mapa.obtenerNumColumnas() * FACTOR,
                mapa.obtenerNumFilas() * FACTOR));
        panelMapa.setBackground(Color.BLACK); // Fondo negro para el mapa
        add(panelMapa, BorderLayout.CENTER);

        // Panel para la información adicional
        JPanel panelInformacion = new JPanel();
        panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));
        panelInformacion.setBackground(Color.GRAY); // Fondo gris para el panel de información
        panelInformacion.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margen interno

        etiquetaPosicion = crearEtiquetaEstilizada("Posición actual: (0, 0)");
        etiquetaCasillasVisitadas = crearEtiquetaEstilizada("Casillas visitadas: 0");
        etiquetaEnergiaGastada = crearEtiquetaEstilizada("Energía gastada: 0");

        // Añadimos un título al panel de información
        JLabel tituloInformacion = crearEtiquetaEstilizada("Estado del Agente");
        tituloInformacion.setFont(new Font("Arial", Font.BOLD, 16));
        tituloInformacion.setForeground(Color.WHITE);
        tituloInformacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelInformacion.add(tituloInformacion);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre título y contenido
        panelInformacion.add(etiquetaPosicion);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio entre etiquetas
        panelInformacion.add(etiquetaCasillasVisitadas);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio entre etiquetas
        panelInformacion.add(etiquetaEnergiaGastada);

        add(panelInformacion, BorderLayout.EAST);

        // Cargar las imágenes
        try {
            imagenLibre = ImageIO.read(new File("resources/LIBRE.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenAgente = ImageIO.read(new File("resources/AGENTE3.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenObjetivo = ImageIO.read(new File("resources/OBJETIVO2.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenObstaculo = ImageIO.read(new File("resources/OBSTACULO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenVisitada = ImageIO.read(new File("resources/VISITADO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pintarMapa(Graphics g) {
        for (int i = 0; i < mapa.obtenerNumFilas(); i++) {
            for (int j = 0; j < mapa.obtenerNumColumnas(); j++) {
                Image imagen = null;
                switch (mapa.obtenerCasilla(i, j)) {
                    case Mapa.LIBRE:
                        imagen = imagenLibre;
                        break;
                    case Mapa.OBSTACULO:
                        imagen = imagenObstaculo;
                        break;
                    case Mapa.OBJETIVO:
                        imagen = imagenObjetivo;
                        break;
                    case Mapa.VISITADA:
                        imagen = imagenVisitada;
                        break;
                    case Mapa.AGENTE:
                        imagen = imagenAgente;
                        break;
                    default:
                        imagen = imagenLibre;
                        break;
                }

                if (imagen != null) {
                    g.drawImage(imagen, j * FACTOR, i * FACTOR, FACTOR, FACTOR, this);
                }
            }
        }
    }

    public void actualizarInformacion(int x, int y, int casillasVisitadas, int energiaGastada) {
        etiquetaPosicion.setText("Posición actual: (" + x + ", " + y + ")");
        etiquetaCasillasVisitadas.setText("Casillas visitadas: " + casillasVisitadas);
        etiquetaEnergiaGastada.setText("Energía gastada: " + energiaGastada);
        repaint();
    }

    public void establecerMapa(Mapa mapa) {
        actualizarInformacion(WIDTH, WIDTH, ALLBITS, HEIGHT);
        this.mapa = mapa;
    }

    public static int obtenerFactor() {
        return FACTOR;
    }

    private JLabel crearEtiquetaEstilizada(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 14));
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }
}
