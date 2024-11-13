package app;

import vista.PanelConfiguracionControlador;

import java.io.IOException;



/**
 * @brief Clase que ejecuta el simulador.
 */
public class Main {
    
    /**
     * @brief Método principal que contiene la lógica de ejecución del simulador.
     * 
     * @param args Argumentos.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        PanelConfiguracionControlador config = 
                new PanelConfiguracionControlador(9);
        
        config.iniciar();
        
        
    }
    
    
}
