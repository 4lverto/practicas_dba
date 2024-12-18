package modelo.agentes.agente;

import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Posicion;

/**
 * @class DecidirMovimiento
 *
 * @brief Clase que representa el comportamiento del agente relativo a la
 * decisión de su próximo movimiento en el mapa, lo cual se hace tras haber
 * actualizado debidamente su memoria.
 */
public class DecidirMovimiento extends OneShotBehaviour {

    /**
     * @brief Instancia del agente.
     */
    private final Agente agente;

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
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(DecidirMovimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<Posicion> camino = Astar.busqueda(agente.mapaMemoria, agente.entorno.obtenerPosAgente(), agente.posObjetivo);

        if (camino != null && !camino.isEmpty()) {
            agente.sensores = agente.entorno.actualizarPercepciones(camino.get(camino.size() - 2));
        }
    }

    @Override
    public int onEnd() {
        if (agente.entorno.obtenerPosAgente().sonIguales(agente.posObjetivo)) {
            return 1;
        } else {
            return 0;
        }
    }
}
