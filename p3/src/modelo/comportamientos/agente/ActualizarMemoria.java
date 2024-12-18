package modelo.comportamientos.agente;

import jade.core.behaviours.OneShotBehaviour;
import modelo.agentes.Agente;

/**
 * @class ActualizarMemoria
 *
 * @brief Clase que representa el comportamiento del agente relativo a la
 * actualización de su memoria interna, lo cual se hace previamente a la
 * decisión del próximo movimiento a ejecutar.
 */
public class ActualizarMemoria extends OneShotBehaviour {

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
        agente.actualizarMemoria();
    }
}
