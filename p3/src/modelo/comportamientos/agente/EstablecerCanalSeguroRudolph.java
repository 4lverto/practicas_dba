package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
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
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("Rudolph", AID.ISLOCALNAME));
        msg.setContent(agente.obtenerCodigoSecreto());
        msg.setConversationId(agente.obtenerCodigoSecreto());
        agente.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals(agente.obtenerCodigoSecreto())
                && msg.getPerformative() == ACLMessage.AGREE) {
            System.out.println("Rudolph ha aceptado el codigo");
            agente.modificarMensajeRudolph(msg);
        } else {
            if (msg.getPerformative() == ACLMessage.REFUSE) {
                System.out.println("Rudolf ha rechazado el codigo");
            } else {
                System.out.println("Error en el protocolo de conversacion");
            }
            agente.doDelete();
        }
    }
}
