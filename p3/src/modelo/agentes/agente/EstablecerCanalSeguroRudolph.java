package modelo.agentes.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final Agente agente;

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
        msg.setContent(agente.codigoSecreto);
        msg.setConversationId(agente.codigoSecreto);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(EstablecerCanalSeguroRudolph.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\nAGENTE-> ESTE ES MI CODIGO SECRETO: '" + msg.getContent() +  "'");
        agente.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals(agente.codigoSecreto)
                && msg.getPerformative() == ACLMessage.AGREE) {
            System.out.println("\n\tRudolph ha aceptado el codigo");
            agente.mensajeRudolph = msg;
            agente.mensaje = "Bro ¿Donde estas? En Plan";
        } else {
            if (msg.getPerformative() == ACLMessage.REFUSE) {
                System.out.println("\n\tRudolf ha rechazado el codigo");
            } else {
                System.out.println("\n\tError en el protocolo de conversacion");
            }
            agente.doDelete();
        }
    }
}
