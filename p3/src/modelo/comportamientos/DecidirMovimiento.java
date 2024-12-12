package modelo.comportamientos;

import jade.core.behaviours.Behaviour;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.agentes.Agente;


/**
 * @class DecidirMovimiento
 * 
 * @brief Clase que representa el comportamiento del agente relativo a la 
 * decisión de su próximo movimiento en el mapa, lo cual se hace tras haber 
 * actualizado debidamente su memoria.
 */
public class DecidirMovimiento extends Behaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    
    
    /**
     * @brief Constructor por parámetro. Asigna al agente.
     * 
     * @param agente Agente del simulador.
     */
    public DecidirMovimiento(Agente agente) {
        this.agente = agente;
    }

    
    /**
     * @brief Sobreescritura del método 'action' de la clase 'Behaviour' del 
     * framework JADE. Se encarga de desencadenar la acción de decidir el 
     * próximo movimiento a realizar sobre el mapa.
     */
    @Override
    public void action() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(DecidirMovimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        agente.decidirMovimiento();

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
