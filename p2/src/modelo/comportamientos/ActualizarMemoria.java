package modelo.comportamientos;

import jade.core.behaviours.Behaviour;
import modelo.agentes.Agente;


/**
 * @class ActualizarMemoria
 * 
 * @brief Clase que representa el comportamiento del agente relativo a la 
 * actualización de su memoria interna, lo cual se hace previamente a la decisión 
 * del próximo movimiento a ejecutar.
 */
public class ActualizarMemoria extends Behaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    
    
    /**
     * @brief Constructor por parámetro. Asigna al agente.
     * 
     * @param agente Agente del simulador.
     */
    public ActualizarMemoria(Agente agente) {
        this.agente = agente;
    }

    /**
     * @brief Sobreescritura del método 'action' de la clase 'Behaviour' del 
     * framework JADE. Se encarga de desencadenar la acción de actualización de 
     * la memoria.
     */
    @Override
    public void action() {
        if(!agente.objetivoAlcanzado())
            agente.actualizarMemoria();
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
