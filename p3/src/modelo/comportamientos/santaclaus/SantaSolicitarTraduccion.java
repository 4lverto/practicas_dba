package modelo.comportamientos.santaclaus;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import modelo.agentes.SantaClaus;

/**
 * @class SolicitarTraduccionElfo
 *
 * @brief Comportamiento para solicitar traducción al Elfo Traductor. Este
 * comportamiento desencadena todo el flujo de la comunicación entre todos
 * nuestros agentes.
 */
public class SantaSolicitarTraduccion extends OneShotBehaviour {

    private final String id;
    private final SantaClaus agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param id id de conversacion.
     * @param mensaje mensaje a traducir
     * @param agente agente
     */
    public SantaSolicitarTraduccion(String id, SantaClaus agente) {
        this.id = id;
        this.agente = agente;
    }

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
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
        msg.setContent(agente.obtenerMensajeTraducido());
        msg.setConversationId(id);
        agente.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals(id)
                && msg.getPerformative() == ACLMessage.INFORM) {
            String contenido = normalizarTexto(msg.getContent());
            System.out.println("\n Traducción recibida: " + contenido);
            agente.establecerMensajeTraducido(contenido);
        } else {
            System.out.println("Error en el protocolo de conversacion");
            agente.doDelete();
        }
    }

}