package modelo.agentes.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eldoi
 */
public class EnviarEvaluacion extends OneShotBehaviour {

    private final SantaClaus agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public EnviarEvaluacion(SantaClaus agente) {
        this.agente = agente;
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage msg = agente.mensajeAgente;
        String contenido = agente.mensaje;

        ACLMessage respuesta;
        if (agente.aceptado) {
            respuesta = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
            respuesta.setContent(contenido);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnviarEvaluacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\nSANTA -> " + respuesta.getContent() +  "");
            agente.send(respuesta);
            
        } else {
            respuesta = msg.createReply(ACLMessage.REJECT_PROPOSAL);
            respuesta.setContent(contenido);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnviarEvaluacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\nSANTA -> '" + respuesta.getContent() +  "'");
            agente.send(respuesta);
            agente.doDelete();
        }


    }
}
