
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;

/**
 * @class SolicitarTraduccionElfo
 * 
 * @brief Comportamiento para solicitar traducción al Elfo Traductor. Este 
 * comportamiento desencadena todo el flujo de la comunicación entre todos 
 * nuestros agentes.
 */
public class SolicitarTraduccionElfo extends OneShotBehaviour {
    
        /**
     * @brief No se ni pa qué voy a usar esto pero bueno
     * @param texto
     * @return
     */
    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {

        System.out.println("Se ha empezado la conexión");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
        msg.setContent("Bro Estoy dispuesto a ofrecerme voluntario para la misión En Plan");
        msg.setConversationId("Traduccion-Agente");
        this.myAgent.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals("Traduccion-Agente")
                && msg.getPerformative() == ACLMessage.INFORM) {
            String contenido = normalizarTexto(msg.getContent());
            System.out.println("\n Traducción recibida: " + contenido);
            System.out.println("\n Traducción esperada: Rakas Joulupukki Estoy dispuesto a ofrecerme voluntario para la mision Kiitos");
        } else {
            System.out.println("Error en el protocolo de conversacion - step" + 2);
            myAgent.doDelete();
        }
    }
    
}
