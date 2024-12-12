package modelo.comportamientos;

import jade.core.behaviours.Behaviour;
import modelo.agentes.Agente;


/**
 * @class PasosTotales
 * 
 * @brief Clase que representa el comportamiento del agente relativo a dar la 
 * información de la cantidad de pasos (energía) gastados hasta llegar a la 
 * casilla objetivo.
 */
public class PasosTotales extends Behaviour {

    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    
    
    /**
     * @brief Constructor por parámetro. Asigna al agente.
     * 
     * @param agente Agente del simulador.
     */
    public PasosTotales(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Sobreescritura del método 'action' de la clase 'Behaviour' del 
     * framework JADE. Se encarga de desencadenar la acción de mostrar el número 
     * de pasos totales que ha dado durante la simulación.
     */
    @Override
    public void action() {
        if(agente.objetivoAlcanzado()){
            System.out.println("Objetivo Alcanzado, se han necesitado un total de " + 
                    (agente.obtenerPasosTotales()) + " pasos.");
            
            System.out.println("Revisa la traza seguida en la interfaz grafica");
        }
    }

    /**
     * @brief Sobreescritura del método 'done' de la clase 'Behaviour' del 
     * framework JADE. Permite controlar cuándo este comportamiento ha 
     * finalizado o no.
     * 
     * @return 'true' en caso de que el agente haya llegado a la casilla 
     * objetivo; 'false' en caso contrario.
     */
    @Override
    public boolean done() {
        return agente.objetivoAlcanzado();
    }
}
