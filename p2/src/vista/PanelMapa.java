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

    private static final int FACTOR = 75;
    private Mapa mapa;

    // Componentes para la información adicional
    private JLabel etiquetaPosicion;    
    private JLabel etiquetaObjetivo;
    private JLabel etiquetaEnergiaGastada;
    private JTextArea areaHistorico;

    public PanelMapa(Entorno entorno) {
        this.mapa = entorno.obtenerMapa();

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30)); // Fondo oscuro minimalista

        // Panel para el mapa
        JPanel panelMapa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                pintarMapa(g, entorno);
            }
        };

        panelMapa.setPreferredSize(new Dimension(
                mapa.obtenerNumColumnas() * FACTOR,
                mapa.obtenerNumFilas() * FACTOR));
        panelMapa.setBackground(new Color(40, 40, 40)); // Fondo del mapa
        add(panelMapa, BorderLayout.CENTER);

        // Panel para la información adicional
        JPanel panelInformacion = new JPanel();
        panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));
        panelInformacion.setBackground(new Color(50, 50, 50)); // Fondo oscuro para la información
        panelInformacion.setBorder(new EmptyBorder(10, 10, 10, 10));

        etiquetaPosicion = crearEtiquetaEstilizada("Posición actual: (0, 0)");
        etiquetaObjetivo = crearEtiquetaEstilizadaObjetivo("Posición del Objetivo: (" + entorno.obtenerPosObjetivo().obtenerX() + ", " + entorno.obtenerPosObjetivo().obtenerY() + ")");
        etiquetaEnergiaGastada = crearEtiquetaEstilizada("Energía gastada: 0");

        JLabel tituloInformacion = crearEtiquetaEstilizada("Estado del Agente");
        tituloInformacion.setFont(new Font("Arial", Font.BOLD, 18));
        tituloInformacion.setForeground(Color.LIGHT_GRAY);
        tituloInformacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        areaHistorico = new JTextArea();
        areaHistorico.setEditable(false);
        areaHistorico.setFont(new Font("SansSerif", Font.PLAIN, 14));
        areaHistorico.setBackground(new Color(50, 50, 50));
        areaHistorico.setForeground(Color.WHITE);
        areaHistorico.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        areaHistorico.setLineWrap(true);
        areaHistorico.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(areaHistorico);
        scrollPane.setPreferredSize(new Dimension(350, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                "Histórico de Movimientos",
                0, 0, new Font("Arial", Font.BOLD, 14), Color.LIGHT_GRAY));

        panelInformacion.add(tituloInformacion);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 15)));
        panelInformacion.add(etiquetaPosicion);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInformacion.add(etiquetaObjetivo);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInformacion.add(etiquetaEnergiaGastada);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 15)));
        panelInformacion.add(scrollPane);

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

    private void pintarMapa(Graphics g, Entorno entorno) {
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
                    case Mapa.VISITADA:
                        imagen = imagenVisitada;
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
        g.drawImage(imagenObjetivo, entorno.obtenerPosObjetivo().obtenerY() * FACTOR, entorno.obtenerPosObjetivo().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(imagenAgente, entorno.obtenerPosAgente().obtenerY() * FACTOR, entorno.obtenerPosAgente().obtenerX() * FACTOR, FACTOR, FACTOR, this);
    }

    public void actualizarInformacion(int x, int y, int energiaGastada) {
        etiquetaPosicion.setText("Posición actual: (" + x + ", " + y + ")");
        etiquetaEnergiaGastada.setText("Energía gastada: " + energiaGastada);

        // Añadir al histórico con separador
        areaHistorico.append(String.format("Posición: (%d, %d) | Energía: %d%n", x, y, energiaGastada));
        areaHistorico.append("---------------------------------------\n");

        repaint();
    }

    public void establecerMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public static int obtenerFactor() {
        return FACTOR;
    }

    private JLabel crearEtiquetaEstilizada(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiqueta.setForeground(Color.LIGHT_GRAY);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }
    
    private JLabel crearEtiquetaEstilizadaObjetivo(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiqueta.setForeground(Color.YELLOW);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }
}
