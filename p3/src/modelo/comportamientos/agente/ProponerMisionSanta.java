package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
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
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public ProponerMisionSanta(Agente agente) {
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
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.addReceiver(new AID("Santa", AID.ISLOCALNAME));
        msg.setContent(agente.obtenerMensaje());
        msg.setConversationId("Evaluacion");
        agente.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals("Evaluacion")
                && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
            String contenido = normalizarTexto(msg.getContent());
            System.out.println("Respuesta recibida a peticion: " + contenido);
            agente.establecerCodigoSecreto(contenido.substring(4, contenido.length() - 8));
        } else {
            if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                System.out.println("Peticion denegada por santa");
            } else {
                System.out.println("Error en el protocolo de conversacion");
            }
            agente.doDelete();
        }
    }
}
