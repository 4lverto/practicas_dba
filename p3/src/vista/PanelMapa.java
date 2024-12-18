package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import modelo.Entorno;
import modelo.Mapa;

/**
 * @class PanelMapa
 *
 * @brief Clase que representa el panel del mapa, y que mostrará cada celda de
 * un color específico según su tipo.
 */
public class PanelMapa extends JPanel {

    /**
     * @brief Factor a aplicar para el dimensionado del panel.
     */
    private static final int FACTOR = 20;

    /**
     * @brief Imágenes que representan los diferentes tipos de casillas del
     * mapa.
     */
    private static Image IMAGEN_LIBRE;
    private static Image IMAGEN_OBSTACULO;
    // private static Image IMAGEN_OBJETIVO;
    private static Image IMAGEN_VISITADA;
    private static Image IMAGEN_AGENTE;
    private static Image IMAGEN_SANTACLAUS;
    private static Image IMAGEN_ELFO;
    private static Image IMAGEN_RUDOLPH;
    private static Image IMAGEN_RENO;

    /**
     * @brief Mapa a representar.
     */
    private Mapa mapa;

    /**
     * @brief Componentes para la información adicional.
     */
    private JLabel etiquetaPosicion;
    private JLabel etiquetaObjetivo;
    private JLabel etiquetaEnergiaGastada;
    private JTextArea areaHistorico;

    /**
     * @brief Constructor por parámetro. Establece las dimensiones del panel.
     *
     * @param mapa Mapa a representar.
     */
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
        // etiquetaObjetivo = crearEtiquetaEstilizadaObjetivo("Posición del Objetivo: (" + entorno.obtenerPosObjetivo().obtenerX() + ", " + entorno.obtenerPosObjetivo().obtenerY() + ")");
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
        //panelInformacion.add(Box.createRigidArea(new Dimension(0, 10)));
        //panelInformacion.add(etiquetaObjetivo);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInformacion.add(etiquetaEnergiaGastada);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 15)));
        panelInformacion.add(scrollPane);

        add(panelInformacion, BorderLayout.EAST);

        // Cargar las imágenes
        try {
            IMAGEN_LIBRE = ImageIO.read(new File("resources/LIBRE.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_AGENTE = ImageIO.read(new File("resources/AGENTE2.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            // IMAGEN_OBJETIVO = ImageIO.read(new File("resources/SANTA.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_OBSTACULO = ImageIO.read(new File("resources/OBSTACULO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_VISITADA = ImageIO.read(new File("resources/VISITADO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_SANTACLAUS = ImageIO.read(new File("resources/SANTA.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_ELFO = ImageIO.read(new File("resources/ELFO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_RUDOLPH = ImageIO.read(new File("resources/RUDOLPH.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            IMAGEN_RENO = ImageIO.read(new File("resources/RENO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Pinta los elemetos en el mapa con cierto estilo.
     *
     * @param g Objeto con los gráficos para personalizar el panel.
     * @param entorno Entorno del simulador.
     */
    private void pintarMapa(Graphics g, Entorno entorno) {
        for (int i = 0; i < mapa.obtenerNumFilas(); i++) {
            for (int j = 0; j < mapa.obtenerNumColumnas(); j++) {
                Image imagen = null;
                switch (mapa.obtenerCasilla(i, j)) {
                    case Mapa.LIBRE:
                        imagen = IMAGEN_LIBRE;
                        break;
                    case Mapa.OBSTACULO:
                        imagen = IMAGEN_OBSTACULO;
                        break;
                    case Mapa.VISITADA:
                        imagen = IMAGEN_VISITADA;
                        break;
                    case Mapa.RENO:
                        imagen = IMAGEN_RENO;
                        break;
                    case Mapa.ELFO:
                        imagen = IMAGEN_ELFO;
                        break;
                    case Mapa.SANTACLAUS:
                        imagen = IMAGEN_SANTACLAUS;
                        break;
                    case Mapa.RUDOLPH:
                        imagen = IMAGEN_RUDOLPH;
                        break;
                    default:
                        imagen = IMAGEN_LIBRE;
                        break;
                }

                if (imagen != null) {
                    g.drawImage(imagen, j * FACTOR, i * FACTOR, FACTOR, FACTOR, this);
                }
            }
        }

        // g.drawImage(IMAGEN_OBJETIVO, entorno.obtenerPosObjetivo().obtenerY() * FACTOR, entorno.obtenerPosObjetivo().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_AGENTE, entorno.obtenerPosAgente().obtenerY() * FACTOR, entorno.obtenerPosAgente().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_SANTACLAUS, entorno.obtenerPosSantaClaus().obtenerY() * FACTOR, entorno.obtenerPosSantaClaus().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_ELFO, entorno.obtenerPosElfo().obtenerY() * FACTOR, entorno.obtenerPosElfo().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RUDOLPH, entorno.obtenerPosRudolph().obtenerY() * FACTOR, entorno.obtenerPosRudolph().obtenerX() * FACTOR, FACTOR, FACTOR, this);

        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno1().obtenerY() * FACTOR, entorno.obtenerPosReno1().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno2().obtenerY() * FACTOR, entorno.obtenerPosReno2().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno3().obtenerY() * FACTOR, entorno.obtenerPosReno3().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno4().obtenerY() * FACTOR, entorno.obtenerPosReno4().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno5().obtenerY() * FACTOR, entorno.obtenerPosReno5().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno6().obtenerY() * FACTOR, entorno.obtenerPosReno6().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno7().obtenerY() * FACTOR, entorno.obtenerPosReno7().obtenerX() * FACTOR, FACTOR, FACTOR, this);
        g.drawImage(IMAGEN_RENO, entorno.obtenerPosReno8().obtenerY() * FACTOR, entorno.obtenerPosReno8().obtenerX() * FACTOR, FACTOR, FACTOR, this);
    }

    /**
     * @brief Actualiza la información acerca del estado del agente en la
     * simulación.
     *
     * @param x Primer elemento de la coordenada de la posición del agente.
     * @param y Segundo elemento de la coordenada de la posición del agente.
     * @param energiaGastada Número de pasos dados por el agente hasta el
     * momento.
     */
    public void actualizarInformacion(int x, int y, int energiaGastada) {
        etiquetaPosicion.setText("Posición actual: (" + x + ", " + y + ")");
        etiquetaEnergiaGastada.setText("Energía gastada: " + energiaGastada);

        // Añadir al histórico con separador
        areaHistorico.append(String.format("Posición: (%d, %d) | Energía: %d%n", x, y, energiaGastada));
        areaHistorico.append("---------------------------------------\n");

        repaint();
    }

    /**
     * @brief Modificar para el mapa.
     *
     * @param mapa Mapa nuevo a asignar.
     */
    public void establecerMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    /**
     * @brief Consultor para el factor del panel.
     *
     * @return Factor del panel.
     */
    public static int obtenerFactor() {
        return FACTOR;
    }

    /**
     * @brief Crea una etiqueta con ciertos atributos de estilo.
     *
     * @param texto Texto para la etiqueta.
     *
     * @return La etiqueta estilizada con el texto pasado.
     */
    private JLabel crearEtiquetaEstilizada(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiqueta.setForeground(Color.LIGHT_GRAY);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }

    /**
     * @brief Crea una etiqueta estilizada para el objetivo.
     *
     * @param texto Texto para la etiqueta.
     *
     * @return La etiqueta estilizada con el texto pasado para el objetivo.
     */
    private JLabel crearEtiquetaEstilizadaObjetivo(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiqueta.setForeground(Color.YELLOW);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }
}
