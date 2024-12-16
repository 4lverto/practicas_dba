package app;

import vista.PanelConfiguracionControlador;

import java.io.IOException;

/**
 * @brief Clase que ejecuta el simulador.
 * 
 * Será el punto de entrada para el sistema de simulación, iniciando el controlador
 * de simulación y comenzando dicho proceso.
 */
public class Main {
    
    /**
     * @brief Método principal que contiene la lógica de ejecución del simulador.
     * @param args Argumentos
     * @throws IOException Si ocurre algún error al iniciar el controlador
     */
    public static void main(String[] args) throws IOException {
        
        // Creamos una instancia de PanelConfiguracionControlador proporcionándole 
        // como parámetro un valor de configuración del tamaño del mapa:
        PanelConfiguracionControlador config = new PanelConfiguracionControlador(100);
        
        // Desplegamos la interfaz gráfica y comienza la simulación:
        config.iniciar();   
    }
}
