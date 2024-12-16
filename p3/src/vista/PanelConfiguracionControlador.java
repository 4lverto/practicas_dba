
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
import vista.VistaGrafica;
import vista.VistaTexto;


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
    
    /**
     * @brief Tope para la dimensión del mapa. Almacena el límite para poder 
     * establecer la fila y columna de las posiciones.
     */
    private int topeDimensionMapa;

    
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
        JLabel etiquetaElegirMapa  = new JLabel("Elegir mapa: ");
        JComboBox<String> opciones = new JComboBox<>(this.FICHEROS_MAPAS);

        // Campos para seleccionar las coordenadas de las posicones del agente
        // y de la casilla objetivo:
        
        JLabel etiquetaPosicionAgente = new JLabel("Posición agente: ");
        
        // Etiqueta y campo para la fila del agente:
        JLabel etiquetaFilaAgente  = new JLabel("Fila: ");
        JSpinner campoFilaAgente   = new JSpinner(
                new SpinnerNumberModel(0, 0, this.topeDimensionMapa, 1));

        // Etiqueta y campo para la columna del agente:
        JLabel etiquetaColumnaAgente  = new JLabel("Columna: ");
        JSpinner campoColumnaAgente   = new JSpinner(
                new SpinnerNumberModel(0, 0, this.topeDimensionMapa, 1));
        
        // Etiqueta y campo para la fila de la casilla objetivo:
        JLabel etiquetaFilaObjetivo = new JLabel("Fila: ");
        JSpinner campoFilaObjetivo  = new JSpinner(
                new SpinnerNumberModel(6, 0, this.topeDimensionMapa, 1));
        
        // Etiqueta y campo para la columna de la casilla objetivo:
        JLabel etiquetaColumnaObjetivo = new JLabel("Columna: ");
        JSpinner campoColumnaObjetivo  = new JSpinner(
                new SpinnerNumberModel(14, 0, this.topeDimensionMapa, 1));
        
        // SANTA CLAUS, RUDOLPH...
        
        JLabel etiquetaFilaSantaClaus = new JLabel("Fila: ");
        JSpinner campoFilaSantaClaus  = new JSpinner(
                new SpinnerNumberModel(8, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaSantaClaus = new JLabel("Columna: ");
        JSpinner campoColumnaSantaClaus  = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaElfo = new JLabel("Fila: ");
        JSpinner campoFilaElfo  = new JSpinner(
                new SpinnerNumberModel(3, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaElfo = new JLabel("Columna: ");
        JSpinner campoColumnaElfo  = new JSpinner(
                new SpinnerNumberModel(9, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaRudolph = new JLabel("Fila: ");
        JSpinner campoFilaRudolph  = new JSpinner(
                new SpinnerNumberModel(1, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaRudolph = new JLabel("Columna: ");
        JSpinner campoColumnaRudolph  = new JSpinner(
                new SpinnerNumberModel(7, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno1 = new JLabel("Fila: ");
        JSpinner campoFilaReno1  = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno1 = new JLabel("Columna: ");
        JSpinner campoColumnaReno1  = new JSpinner(
                new SpinnerNumberModel(3, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno2 = new JLabel("Fila: ");
        JSpinner campoFilaReno2  = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno2 = new JLabel("Columna: ");
        JSpinner campoColumnaReno2  = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno3 = new JLabel("Fila: ");
        JSpinner campoFilaReno3  = new JSpinner(
                new SpinnerNumberModel(8, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno3 = new JLabel("Columna: ");
        JSpinner campoColumnaReno3  = new JSpinner(
                new SpinnerNumberModel(2, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno4 = new JLabel("Fila: ");
        JSpinner campoFilaReno4  = new JSpinner(
                new SpinnerNumberModel(6, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno4 = new JLabel("Columna: ");
        JSpinner campoColumnaReno4  = new JSpinner(
                new SpinnerNumberModel(6, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno5 = new JLabel("Fila: ");
        JSpinner campoFilaReno5  = new JSpinner(
                new SpinnerNumberModel(6, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno5 = new JLabel("Columna: ");
        JSpinner campoColumnaReno5  = new JSpinner(
                new SpinnerNumberModel(7, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno6 = new JLabel("Fila: ");
        JSpinner campoFilaReno6  = new JSpinner(
                new SpinnerNumberModel(7, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno6 = new JLabel("Columna: ");
        JSpinner campoColumnaReno6  = new JSpinner(
                new SpinnerNumberModel(9, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno7 = new JLabel("Fila: ");
        JSpinner campoFilaReno7  = new JSpinner(
                new SpinnerNumberModel(13, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno7 = new JLabel("Columna: ");
        JSpinner campoColumnaReno7  = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaFilaReno8 = new JLabel("Fila: ");
        JSpinner campoFilaReno8  = new JSpinner(
                new SpinnerNumberModel(11, 0, this.topeDimensionMapa, 1));
        
        JLabel etiquetaColumnaReno8 = new JLabel("Columna: ");
        JSpinner campoColumnaReno8  = new JSpinner(
                new SpinnerNumberModel(5, 0, this.topeDimensionMapa, 1));
        
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
                int filaAgente          = (Integer) campoFilaAgente.getValue();
                int columnaAgente       = (Integer) campoColumnaAgente.getValue();
                int filaObjetivo        = (Integer) campoFilaObjetivo.getValue();
                int columnaObjetivo     = (Integer) campoColumnaObjetivo.getValue();
                
                int filaRudolph = (Integer) campoFilaRudolph.getValue();
                int columnaRudolph = (Integer) campoColumnaRudolph.getValue();
                
                int filaElfo = (Integer) campoFilaElfo.getValue();
                int columnaElfo = (Integer) campoColumnaElfo.getValue();
                
                int filaSantaClaus = (Integer) campoFilaSantaClaus.getValue();
                int columnaSantaClaus = (Integer) campoColumnaSantaClaus.getValue();
                
                int filaReno1 = (Integer) campoFilaReno1.getValue();
                int columnaReno1 = (Integer) campoColumnaReno1.getValue();
                
                int filaReno2 = (Integer) campoFilaReno2.getValue();
                int columnaReno2 = (Integer) campoColumnaReno2.getValue();
                
                int filaReno3 = (Integer) campoFilaReno3.getValue();
                int columnaReno3 = (Integer) campoColumnaReno3.getValue();
                
                int filaReno4 = (Integer) campoFilaReno4.getValue();
                int columnaReno4 = (Integer) campoColumnaReno4.getValue();
                
                int filaReno5 = (Integer) campoFilaReno5.getValue();
                int columnaReno5 = (Integer) campoColumnaReno5.getValue();
                
                int filaReno6 = (Integer) campoFilaReno6.getValue();
                int columnaReno6 = (Integer) campoColumnaReno6.getValue();
                
                int filaReno7 = (Integer) campoFilaReno7.getValue();
                int columnaReno7 = (Integer) campoColumnaReno7.getValue();
                
                int filaReno8 = (Integer) campoFilaReno8.getValue();
                int columnaReno8 = (Integer) campoColumnaReno8.getValue();
                
                
                // Preparar lo necesario para iniciar la simulación mediante el
                // controlador:
                
                // Crear instancias de Posicion para el agente y el objetivo
                Posicion posAgente   = new Posicion(filaAgente, columnaAgente);
                Posicion posObjetivo = new Posicion(filaObjetivo, columnaObjetivo);
                
                Posicion posRudolph = new Posicion(filaRudolph,columnaRudolph);
                Posicion posElfo = new Posicion(filaElfo,columnaElfo);
                Posicion posSantaClaus = new Posicion(filaSantaClaus,columnaSantaClaus);
                
                Posicion posReno1 = new Posicion(filaReno1,columnaReno1);
                Posicion posReno2 = new Posicion(filaReno2,columnaReno2);
                Posicion posReno3 = new Posicion(filaReno3,columnaReno3);
                Posicion posReno4 = new Posicion(filaReno4,columnaReno4);
                Posicion posReno5 = new Posicion(filaReno5,columnaReno5);
                Posicion posReno6 = new Posicion(filaReno6,columnaReno6);
                Posicion posReno7 = new Posicion(filaReno7,columnaReno7);
                Posicion posReno8 = new Posicion(filaReno8,columnaReno8);
                
                
                Entorno entorno;
                
                try {
                    
                    // Creamos y configuramos el entorno
                    entorno = Entorno.obtenerInstancia(posAgente,
                            posObjetivo, posReno1,  posReno2,
                            posReno3,  posReno4,  posReno5,
                            posReno6,  posReno7,  posReno8,
                            posElfo,  posRudolph,
                            posSantaClaus);
                    
                    // Establecer el mapa:
                    entorno.establecerMapa(mapaSeleccionado);
                    
                    // Crear las vistas (estas se suscriben automáticamente al 
                    // entorno para observarlo y actualizarse):
                    VistaTexto vistaTextual   = new VistaTexto(entorno);
                    VistaGrafica vistaGrafica = new VistaGrafica(entorno);
                    
                    Vision sensorVision = new Vision(entorno);
                    Energia sensorEnergia = new Energia(entorno);

  
                    // Crear el controlador, el cual lanzará al agente (el 
                    // entorno le llega al agente a través del controlador, con 
                    // las posiciones y el mapa debidamente inicializados):
                    Controlador controlador = new Controlador(
                            "Agente-DBA-P2",
                            "modelo.agentes.Agente",
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
        
        gbc.insets = new Insets(8, 8, 8, 8);
        
        int fila=1;
        
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaAgente, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaAgente, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaAgente, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaAgente, gbc);
        fila++;
        
        
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 3: Título de la selección de la posición de la casilla        //
        // objetivo                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla objetivo: "), gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 4: Selector de la posición de la casilla objetivo             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaObjetivo, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaObjetivo, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaObjetivo, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaObjetivo, gbc);
        fila++;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 5: Título de la selección de la posición de la casilla        //
        // de SantaClaus                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla SantaClaus: "), gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 6: Selector de la posición de la casilla SantaClaus             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaSantaClaus, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaSantaClaus, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaSantaClaus, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaSantaClaus, gbc);
        fila++;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 7: Título de la selección de la posición de la casilla        //
        // de Rudolph                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Rudolph: "), gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 8: Selector de la posición de la casilla SantaClaus             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaRudolph, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaRudolph, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaRudolph, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaRudolph, gbc);
        fila++;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 9: Título de la selección de la posición de la casilla        //
        // del Elfo                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel("Posición casilla Elfo: "), gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 10: Selector de la posición de la casilla Elfo             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaElfo, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaElfo, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaElfo, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaElfo, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno1, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno1, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno1, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno2, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno2, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno2, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno2, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno3, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno3, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno3, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno3, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno4, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno4, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno4, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno4, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno5, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno5, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno5, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno5, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno6, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno6, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno6, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno6, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno7, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno7, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno7, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno7, gbc);
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
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaReno8, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaReno8, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaReno8, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaReno8, gbc);
        fila++;
        
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila X: Botón para ejecutar el simulador con la configuración      //
        // seleccionada                                                       //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 0, 0, 0);
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
