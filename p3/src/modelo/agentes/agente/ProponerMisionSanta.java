package modelo.agentes.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final Agente agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public ProponerMisionSanta(Agente agente) {
        this.agente = agente;
    }

    /**
     * @brief Funcion para normalizar el texto
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
        msg.setContent(agente.mensaje);
        msg.setConversationId("Evaluacion");
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProponerMisionSanta.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\nAGENTE -> '" + msg.getContent() + "'");      
        agente.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals("Evaluacion")
                && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
            String contenido = normalizarTexto(msg.getContent());

            agente.mensaje = contenido;
            agente.mensajeSanta = msg;

            System.out.println("\n\tLa propuesta del agente ha sido aceptada por Santa Claus: " + contenido);

            agente.codigoSecreto = contenido.substring(4, contenido.length() - 8);

        } else {
            if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                System.out.println("\n\tLa propuesta del agente ha sido denegada por Santa Claus");
            } else {
                System.out.println("\n\tError en el protocolo de conversacion");
            }
            agente.doDelete();
        }
    }
}
