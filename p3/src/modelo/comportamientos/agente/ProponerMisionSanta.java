
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class ProponerMisionSanta
 * 
 * @brief Comportamiento para proponerse el Agente Buscador como voluntario para 
 * la misión ante Santa Claus.
 */
public class ProponerMisionSanta extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Mensaje traducido por el Elfo Traductor para Santa Claus.
     */
    private final String mensajeTraducido;

    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     * @param mensajeTraducido Mensaje traducido por el Elfo Traductor para 
     * Santa Claus.
     */
    public ProponerMisionSanta(Agente agente, String mensajeTraducido) {
        this.agente           = agente;
        this.mensajeTraducido = mensajeTraducido;
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage propuesta = new ACLMessage(ACLMessage.PROPOSE);
        
        propuesta.addReceiver(new AID("SC", AID.ISLOCALNAME));
        propuesta.setContent("{\"action\": \"proposal_to_do_mission\", "
                + "\"message\": \"" + mensajeTraducido + "\"}");
        
        propuesta.setConversationId("mission-proposal");
        this.agente.modificarMensajeSanta(propuesta);
        this.agente.send(propuesta);
        this.agente.addBehaviour(new EsperarRespuestaSanta(this.agente));
    }
}
