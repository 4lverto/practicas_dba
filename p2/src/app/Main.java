package app;

import vista.PanelConfiguracionControlador;
import controlador.Controlador;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Energia;
import modelo.sensores.Sensor;
import modelo.sensores.Vision;
import vista.VistaGrafica;
import vista.VistaTexto;


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
