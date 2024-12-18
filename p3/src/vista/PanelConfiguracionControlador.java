package vista;

import controlador.Controlador;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Energia;
import modelo.sensores.Vision;

/**
 * @class PanelConfiguracionControlador
 *
 * @brief Clase que representa un panel para la configuración del controlador.
 * Permite seleccionar el mapa para la simulación, así como la posición inicial
 * del agente y la posición de la casilla objetivo.
 */
public class PanelConfiguracionControlador extends JFrame {

    /**
     * @brief Array con los mapas disponibles.
     */
    private static final String[] FICHEROS_MAPAS = {
        "mapas/100x100-sinObstaculos.txt",
        "mapas/100x100-conObstaculos.txt"
    };

    private static final String[] Nombres_Agentes = {
        "Agente",
        "Elfo",
        "Santa",
        "Rudolph"
    };

    private static final String[] Clases_Agentes = {
        "modelo.agentes.agente.Agente",
        "modelo.agentes.elfotraductor.ElfoTraductor",
        "modelo.agentes.santaclaus.SantaClaus",
        "modelo.agentes.rudolph.Rudolph"
    };

    /**
     * @brief Tope para la dimensión del mapa. Almacena el límite para poder
     * establecer la fila y columna de las posiciones.
     */
    private final int topeDimensionMapa;

    /**
     * @brief Constructor por parámetro.
     *
     * @param tope Tope para la dimensión del mapa.
     */
    public PanelConfiguracionControlador(int tope) {
        this.topeDimensionMapa = tope;

        // Configuración de la ventana:
        setTitle("Panel de configuración");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar los componentes
        iniciarComponentes();
    }

    /**
     * @brief Inicia los componentes. Para ello, crea el panel y añade los
     * diferentes elementos a este.
     */
    private void iniciarComponentes() {
        // Crear un panel que actuará como contenedor de todos los elementos de la IU gráfica.
        // Los elementos "grid" servirán para realizar la distribución de los componentes
        // en el panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Selector para los mapas:
        JLabel etiquetaElegirMapa = new JLabel("Elegir mapa: ");
        JComboBox<String> opciones = new JComboBox<>(PanelConfiguracionControlador.FICHEROS_MAPAS);

        // Campos para seleccionar las coordenadas de las posicones del agente
        // y de la casilla objetivo:
        JLabel etiquetaPosicionAgente = new JLabel("Posición agente: ");

        // Etiqueta y campo para la fila del agente:
        JLabel etiquetaFilaAgente = new JLabel("Fila: ");
        JSpinner campoFilaAgente = new JSpinner(
                new SpinnerNumberModel(99, 0, this.topeDimensionMapa, 1));

        // Etiqueta y campo para la columna del agente:
        JLabel etiquetaColumnaAgente = new JLabel("Columna: ");
        JSpinner campoColumnaAgente = new JSpinner(
                new SpinnerNumberModel(99, 0, this.topeDimensionMapa, 1));

        
        // //////////////// //
        // NUEVO PRÁCTICA 3 //
        // //////////////// //
        
        JLabel etiquetaFilaSantaClaus = new JLabel("Fila: ");
        JSpinner campoFilaSantaClaus = new JSpinner(
                new SpinnerNumberModel(0, 0, this.topeDimensionMapa, 1));

        JLabel etiquetaColumnaSantaClaus = new JLabel("Columna: ");
        JSpinner campoColumnaSantaClaus = new JSpinner(
                new SpinnerNumberModel(0, 0, this.topeDimensionMapa, 1));        
        
        
        JLabel[] etiquetaFilaReno = new JLabel[8];
        JSpinner[] campoFilaReno = new JSpinner[8];

        JLabel[] etiquetaColumnaReno = new JLabel[8];
        JSpinner[] campoColumnaReno = new JSpinner[8];
        
        etiquetaFilaReno[0] = new JLabel("Fila: ");
        campoFilaReno[0] = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[0] = new JLabel("Columna: ");
        campoColumnaReno[0] = new JSpinner(
                new SpinnerNumberModel(7, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[1] = new JLabel("Fila: ");
        campoFilaReno[1] = new JSpinner(
                new SpinnerNumberModel(11, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[1] = new JLabel("Columna: ");
        campoColumnaReno[1] = new JSpinner(
                new SpinnerNumberModel(71, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[2] = new JLabel("Fila: ");
        campoFilaReno[2] = new JSpinner(
                new SpinnerNumberModel(23, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[2] = new JLabel("Columna: ");
        campoColumnaReno[2] = new JSpinner(
                new SpinnerNumberModel(39, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[3] = new JLabel("Fila: ");
        campoFilaReno[3] = new JSpinner(
                new SpinnerNumberModel(42, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[3] = new JLabel("Columna: ");
        campoColumnaReno[3] = new JSpinner(
                new SpinnerNumberModel(61, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[4] = new JLabel("Fila: ");
        campoFilaReno[4] = new JSpinner(
                new SpinnerNumberModel(46, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[4] = new JLabel("Columna: ");
        campoColumnaReno[4] = new JSpinner(
                new SpinnerNumberModel(17, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[5] = new JLabel("Fila: ");
        campoFilaReno[5] = new JSpinner(
                new SpinnerNumberModel(59, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[5] = new JLabel("Columna: ");
        campoColumnaReno[5] = new JSpinner(
                new SpinnerNumberModel(39, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[6] = new JLabel("Fila: ");
        campoFilaReno[6] = new JSpinner(
                new SpinnerNumberModel(86, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[6] = new JLabel("Columna: ");
        campoColumnaReno[6] = new JSpinner(
                new SpinnerNumberModel(84, 0, this.topeDimensionMapa, 1));

        etiquetaFilaReno[7] = new JLabel("Fila: ");
        campoFilaReno[7] = new JSpinner(
                new SpinnerNumberModel(90, 0, this.topeDimensionMapa, 1));

        etiquetaColumnaReno[7] = new JLabel("Columna: ");
        campoColumnaReno[7] = new JSpinner(
                new SpinnerNumberModel(4, 0, this.topeDimensionMapa, 1));

        // ////////////////////////////////////////// //
        // LÓGICA DE EJECUCIÓN AL PRESIONAR EL BOTÓN: //
        // ////////////////////////////////////////// //
        // Botón para aplicar la configuración y ejecutar el simulador:
        JButton botonEjecutar = new JButton("Ejecutar");

        botonEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Recuperamos los valores seleccionados por el usuario para el mapa,
                // las coordenadas del agente y las del objetivo.
                String mapaSeleccionado = (String) opciones.getSelectedItem();
                int filaAgente = (Integer) campoFilaAgente.getValue();
                int columnaAgente = (Integer) campoColumnaAgente.getValue();
                
                /*
                int filaObjetivo = (Integer) campoFilaObjetivo.getValue();
                int columnaObjetivo = (Integer) campoColumnaObjetivo.getValue();
                */
                
                // //////// //
                // NUEVO P3 //
                // //////// //
                

                int filaSantaClaus = (Integer) campoFilaSantaClaus.getValue();
                int columnaSantaClaus = (Integer) campoColumnaSantaClaus.getValue();
                
                int[] filaReno = new int[8];
                int[] columnaReno = new int[8];

                filaReno[0] = (Integer) campoFilaReno[0].getValue();
                columnaReno[0] = (Integer) campoColumnaReno[0].getValue();

                filaReno[1] = (Integer) campoFilaReno[1].getValue();
                columnaReno[1] = (Integer) campoColumnaReno[1].getValue();

                filaReno[2] = (Integer) campoFilaReno[2].getValue();
                columnaReno[2] = (Integer) campoColumnaReno[2].getValue();

                filaReno[3] = (Integer) campoFilaReno[3].getValue();
                columnaReno[3] = (Integer) campoColumnaReno[3].getValue();

                filaReno[4] = (Integer) campoFilaReno[4].getValue();
                columnaReno[4] = (Integer) campoColumnaReno[4].getValue();

                filaReno[5] = (Integer) campoFilaReno[5].getValue();
                columnaReno[5] = (Integer) campoColumnaReno[5].getValue();

                filaReno[6] = (Integer) campoFilaReno[6].getValue();
                columnaReno[6] = (Integer) campoColumnaReno[6].getValue();

                filaReno[7] = (Integer) campoFilaReno[7].getValue();
                columnaReno[7] = (Integer) campoColumnaReno[7].getValue();

                // Preparar lo necesario para iniciar la simulación mediante el
                // controlador:
                // Crear instancias de Posicion para el agente y el objetivo
                
                Posicion posAgente = new Posicion(filaAgente, columnaAgente);


                Posicion posSantaClaus = new Posicion(filaSantaClaus, columnaSantaClaus);

                Posicion[] posReno = new Posicion[8];
                
                posReno[0] = new Posicion(filaReno[0], columnaReno[0]);
                posReno[1] = new Posicion(filaReno[1], columnaReno[1]);
                posReno[2] = new Posicion(filaReno[2], columnaReno[2]);
                posReno[3] = new Posicion(filaReno[3], columnaReno[3]);
                posReno[4] = new Posicion(filaReno[4], columnaReno[4]);
                posReno[5] = new Posicion(filaReno[5], columnaReno[5]);
                posReno[6] = new Posicion(filaReno[6], columnaReno[6]);
                posReno[7] = new Posicion(filaReno[7], columnaReno[7]);

                
                
                Entorno entorno;

                try {

                    // Creamos y configuramos el entorno
                    entorno = Entorno.obtenerInstancia(posAgente, posReno, posSantaClaus);

                    // Establecer el mapa:
                    entorno.establecerMapa(mapaSeleccionado);

                    // Crear las vistas (estas se suscriben automáticamente al 
                    // entorno para observarlo y actualizarse):
                    VistaTexto vistaTextual = new VistaTexto(entorno);
                    VistaGrafica vistaGrafica = new VistaGrafica(entorno);

                    Vision sensorVision = new Vision(entorno);
                    Energia sensorEnergia = new Energia(entorno);

                    // Crear el controlador, el cual lanzará al agente (el 
                    // entorno le llega al agente a través del controlador, con 
                    // las posiciones y el mapa debidamente inicializados):
                    Controlador controlador = new Controlador(
                            Nombres_Agentes,
                            Clases_Agentes,
                            entorno);

                    // Iniciar la simulación. Esta terminará cuando el agente 
                    // llegue a la casilla objetivo (el agente lo controla 
                    // internamente):
                    controlador.ejecutar();

                } catch (IOException ex) {
                    Logger.getLogger(
                            PanelConfiguracionControlador.class.getName()).log(
                            Level.SEVERE, null, ex);
                }

                dispose(); // Cerrar el panel de configuración.
            }
        });

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Distribución de los componentes del panel                          //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.insets = new Insets(8, 8, 8, 40);

        int fila = 1;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 0: Selector del mapa                                          //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaElegirMapa, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(opciones, gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 1: Título de la selección de la posición inicial del agente   //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(etiquetaPosicionAgente, gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 2: Selector de la posición inicial del agente                 //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaAgente, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaAgente, gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaAgente, gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaAgente, gbc);
        fila++;

        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 5: Título de la selección de la posición de la casilla        //
        // de SantaClaus                                                      //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;

        panel.add(new JLabel("Posición casilla SantaClaus: "), gbc);
        
        // Dejar una columna vacía
        gbc.gridx = 1; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(" "), gbc);

        // Dejar una columna vacía
        gbc.gridx = 1; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(" "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 6: Selector de la posición de la casilla SantaClaus           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);


        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaSantaClaus, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaSantaClaus, gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaSantaClaus, gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaSantaClaus, gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 11: Título de la selección de la posición de la casilla        //
        // del Reno 1                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 1: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 12: Selector de la posición de la casilla Reno 1             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[0], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[0], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[0], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[0], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 13: Título de la selección de la posición de la casilla        //
        // del Reno 2                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 2: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 14: Selector de la posición de la casilla Reno 2             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[1], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[1], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[1], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[1], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 15: Título de la selección de la posición de la casilla        //
        // del Reno 3                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 3: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 16: Selector de la posición de la casilla Reno 3             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[2], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[2], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[2], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[2], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 17: Título de la selección de la posición de la casilla        //
        // del Reno 4                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 4: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 18: Selector de la posición de la casilla Reno 4             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[3], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[3], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[3], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[3], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 19: Título de la selección de la posición de la casilla        //
        // del Reno 5                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 5: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 20: Selector de la posición de la casilla Reno 5             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[4], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[4], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[4], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[4], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 21: Título de la selección de la posición de la casilla        //
        // del Reno 6                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 6: "), gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 22: Selector de la posición de la casilla Reno 6             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[5], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[5], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[5], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[5], gbc);
        fila++;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 23: Título de la selección de la posición de la casilla        //
        // del Reno 7                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 7: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 24: Selector de la posición de la casilla Reno 7             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[6], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[6], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[6], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[6], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 25: Título de la selección de la posición de la casilla        //
        // del Reno 8                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Reno 8: "), gbc);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 26: Selector de la posición de la casilla Reno 8             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        // Dejar una columna vacía
        gbc.gridx = 0; // Columna vacía para el espacio
        gbc.gridy = fila;
        panel.add(new JLabel(""), gbc);

        // Para la fila:
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno[7], gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno[7], gbc);

        // Para la columna:
        gbc.gridx = 3;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno[7], gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno[7], gbc);
        fila++;

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila X: Botón para ejecutar el simulador con la configuración      //
        // seleccionada                                                       //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonEjecutar, gbc);

        // Añadir el panel al frame:
        add(panel);
    }

    /**
     * @brief Inicia el panel, haciéndolo visible en la pantalla.
     */
    public void iniciar() {
        setVisible(true);
    }
}
