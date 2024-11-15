
package vista;

import modelo.Entorno;
import modelo.sensores.Sensor;
import modelo.sensores.Energia;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @class VistaGrafica
 * 
 * @brief Clase que representa la vista gráfica del simulador. Esta utiliza la
 * biblioteca Swing para representar el entorno (mapa, agente y casilla 
 * objetivo).
 */
public class VistaGrafica extends Vista {
    
    /**
     * @brief Ventana gráfica principal para el simulador.
     */
    private JFrame ventana;
    
    /**
     * @brief Panel para el mapa a visualizar.
     */
    private PanelMapa panelMapa;

    /**
     * @brief Constructor por parámetro. Inicializa la vista y crea la ventana
     * principal con una configuración por defecto.
     * 
     * @param entorno Instancia del entorno.
     */
    public VistaGrafica(Entorno entorno) throws IOException {
        this.entorno = entorno;
        // Añadirse al entorno (observado) como observador:
        this.entorno.registrarVista(this);
        
        this.ventana   = new JFrame("Prática 2 de DBA");
        this.panelMapa = new PanelMapa(entorno);
        
        // Configuración de la ventana principal:
        int anchoVentana = 
                entorno.obtenerMapa().obtenerNumColumnas() * PanelMapa.obtenerFactor();
        int altoVentana  = 
                entorno.obtenerMapa().obtenerNumFilas() * PanelMapa.obtenerFactor();
        
        this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ventana.setLayout(new BorderLayout());
        this.ventana.add(this.panelMapa, BorderLayout.CENTER);
        this.ventana.setSize(anchoVentana, altoVentana);
        this.ventana.pack();
        this.ventana.setLocationRelativeTo(null); // Centra la ventana
        this.ventana.setVisible(true);
    }

    /**
     * @brief Actualiza la vista en función del estado del entorno.
     * Implementación del método abstracto de Vista.
     */
    @Override
    public void actualizar() {
        this.panelMapa.establecerMapa(this.entorno.obtenerMapa());
        this.panelMapa.actualizarInformacion(this.entorno.obtenerPosAgente().obtenerX(), this.entorno.obtenerPosAgente().obtenerY(), 0,0);
        this.panelMapa.repaint(); // Para repintar la pantalla.
    }
}
