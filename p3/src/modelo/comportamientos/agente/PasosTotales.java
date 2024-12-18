package modelo.comportamientos.agente;

import jade.core.behaviours.OneShotBehaviour;
import modelo.agentes.Agente;

/**
 * @class PasosTotales
 *
 * @brief Clase que representa el comportamiento del agente relativo a dar la
 * información de la cantidad de pasos (energía) gastados hasta llegar a la
 * casilla objetivo.
 */
public class PasosTotales extends OneShotBehaviour {

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
        System.out.println("\nOBJETIVO ALCANZADO");/* -> se han necesitado un total de "
                + (agente.obtenerPasosTotales()) + " pasos.");*/

    }
}
