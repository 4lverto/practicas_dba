
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EstablecerCanalSeguroRudolph
 * 
 * @brief Comportamiento para establecer un canal seguro de comunicación con 
 * Rudolph para pasar a ir obteniendo las coordenadas de los renos.
 */
public class EstablecerCanalSeguroRudolph extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EstablecerCanalSeguroRudolph(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage solicitud = new ACLMessage(ACLMessage.REQUEST);
        
        solicitud.addReceiver(new AID("RH", AID.ISLOCALNAME));
        solicitud.setConversationId(this.agente.obtenerCodigoSecreto());
        solicitud.setContent("{\"action\": \"establish_secure_channel\", "
                + "\"message\": \"" + this.agente.obtenerCodigoSecreto() + "\"}");
        
        this.agente.modificarMensajeRudolph(solicitud);
        this.agente.send(solicitud);
    }
}