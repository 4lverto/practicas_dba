package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.agentes.Agente;

/**
 * @class SolicitarTraduccionElfo
 *
 * @brief Comportamiento para solicitar traducción al Elfo Traductor. Este
 * comportamiento desencadena todo el flujo de la comunicación entre todos
 * nuestros agentes.
 */
public class SolicitarTraduccion extends OneShotBehaviour {

    private final String mensaje;
    private final String id;
    private final Agente agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param id id de conversacion.
     * @param agente agente
     */
    public SolicitarTraduccion(String id, Agente agente) {
        this.id = id;
        this.agente = agente;
        this.mensaje = agente.obtenerMensaje();
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
        msg.setContent(mensaje);
        msg.setConversationId(id);
        agente.send(msg);

        System.out.println("AGENTE -> TRADUCEME ESTE MENSAJE: '" + msg.getContent() + "'");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SolicitarTraduccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals(id)
                && msg.getPerformative() == ACLMessage.INFORM) {
            String contenido = normalizarTexto(msg.getContent());
            System.out.println("\n\tTraduccion recibida: " + contenido);
            agente.establecerMensaje(contenido);
        } else {
            System.out.println("\n\tError en el protocolo de conversacion");
            agente.doDelete();
        }
    }

}
