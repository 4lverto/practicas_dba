
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class SolicitarTraduccionElfo
 * 
 * @brief Comportamiento para solicitar traducción al Elfo Traductor. Este 
 * comportamiento desencadena todo el flujo de la comunicación entre todos 
 * nuestros agentes.
 */
public class SolicitarTraduccionElfo extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    /**
     * @brief Constructor por defecto para instanciar al agente.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public SolicitarTraduccionElfo(Agente agente) {
        this.agente = agente;
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage solicitud = new ACLMessage(ACLMessage.REQUEST);
            solicitud.addReceiver(new AID("ET", AID.ISLOCALNAME));
            solicitud.setContent("{\"action\": \"translate\", \"message\": "
                    + "\"Bro Estoy dispuesto a ofrecerme voluntario para la misión En Plan\"}");
            this.agente.send(solicitud);
            this.agente.addBehaviour(new EsperarTraduccionElfo(this.agente));
    }
    
}
