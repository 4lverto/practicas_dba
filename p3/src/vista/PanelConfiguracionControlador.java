
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
            "mapas/100x100-conObstaculos.txt",
            "mapas/100x100-sinObstaculos.txt"
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
                new SpinnerNumberModel(6, 0, this.topeDimensionMapa, 1));
        
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

                // Preparar lo necesario para iniciar la simulación mediante el
                // controlador:
                
                // Crear instancias de Posicion para el agente y el objetivo
                Posicion posAgente   = new Posicion(filaAgente, columnaAgente);
                Posicion posObjetivo = new Posicion(filaObjetivo, columnaObjetivo);
                Entorno entorno;
                
                try {
                    
                    // Creamos y configuramos el entorno
                    entorno = Entorno.obtenerInstancia(
                            posAgente,
                            posObjetivo);
                    
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
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 0: Selector del mapa                                          //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaElegirMapa, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(opciones, gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 1: Título de la selección de la posición inicial del agente   //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(etiquetaPosicionAgente, gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 2: Selector de la posición inicial del agente                 //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaAgente, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaAgente, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaAgente, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaAgente, gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 3: Título de la selección de la posición de la casilla        //
        // objetivo                                                           //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Posición casilla objetivo: "), gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 4: Selector de la posición de la casilla objetivo             //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // Para la fila:
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaFilaObjetivo, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoFilaObjetivo, gbc);

        // Para la columna:
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(etiquetaColumnaObjetivo, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campoColumnaObjetivo, gbc);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // Fila 5: Botón para ejecutar el simulador con la configuración      //
        // seleccionada                                                       //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

        gbc.gridx = 0;
        gbc.gridy = 5;
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
