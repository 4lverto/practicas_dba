
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class ConsultarCoordenadasSanta
 * 
 * @brief Comportamiento para consultar a Santa Claus sus coordenadas para 
 * llevarle los renos.
 */
public class ConsultarCoordenadasSanta extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Mensaje previamente traducido por el Elfo Traductor.
     */
    private final String mensajeTraducido;

    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     * @param mensajeTraducido Mensaje previamente traducido por el Elfo Traductor.
     */
    public ConsultarCoordenadasSanta(Agente agente, String mensajeTraducido) {
        this.agente = agente;
        this.mensajeTraducido = mensajeTraducido;
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        // Aprovechar el canal previamente creado con Santa Claus:
        ACLMessage consulta = this.agente.obtenerMensajeSanta().createReply();
        
        consulta.setPerformative(ACLMessage.QUERY_REF);
        consulta.setContent("{\"action\": \"ask_for_coordinates_SC\", "
                + "\"message\": \"" + mensajeTraducido + "\"}");
        
        this.agente.modificarMensajeSanta(consulta);
        this.agente.send(consulta);
        this.agente.addBehaviour(new EsperarCoordenadasSanta(this.agente));
    }
}
