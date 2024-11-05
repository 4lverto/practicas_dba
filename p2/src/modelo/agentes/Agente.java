
package modelo.agentes;

import jade.core.Agent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;


/**
 * @brief Clase que representa nuestro agente.
 */
public class Agente extends Agent {
    /**
     * @brief Entorno de la simulación con el que interactuará el agente para 
     * desplazarse por el mapa.
     */
    private Entorno entorno;
    
    /**
     * @brief Conjunto de sensores que podrá consultar el agente.
     */
    private ArrayList<Sensor> sensores;

    
    
    /**
     * @brief Implementación del método 'setup' de 'Agent' (JADE). Inicia el 
     * agente y los comportamientos que va a desarrollar.
     */
    @Override
    protected void setup() {
        this.sensores = new ArrayList<>();
        
        // Obtener y procesar los argumentos:
        Object[] args = getArguments();
        
        if (args != null && args.length == 1) {
            // Crear el entorno:
            if (args[0] instanceof Entorno) {
                entorno = (Entorno) args[0];
            } else {
                System.out.println("\nError: no se recibió el entorno.");
            }
        } else {
            System.out.println("\nError: no se recibieron argumentos.");
        }
        
        
        // 
        System.out.println("Soy el agente '" + getLocalName() + "'");
        doDelete();
    }
    
    /**
     * @brief Devuelve si el agente ha llegado a la casilla objetivo.
     * 
     * @return 'true' si el agente está sobre la casilla objetivo; 'false' en 
     * otro caso.
     */
    public boolean objetivoAlcanzado() {
        return (entorno.obtenerPosAgente().sonIguales(
                entorno.obtenerPosObjetivo()));
    }
}
