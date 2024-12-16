
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class SolicitarTraduccionFinal
 * 
 * @brief Comportamiento para solicitar la traducción final al Elfo Traductor, 
 * justo antes de volverse a comunicar con Santa Claus.
 */
public class SolicitarTraduccionFinal extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public SolicitarTraduccionFinal(Agente agente) {
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
                + "ń\"Bro ¿Dónde estás? En Plan\"}");
        
        this.agente.send(solicitud);
        this.agente.addBehaviour(new EsperarTraduccionFinal(this.agente));
    }
}
