package modelo.agentes.agente;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;

/**
 *
 * @author rafalpv
 */
public class DespedidaSanta extends OneShotBehaviour {

    private final Agente agente;

    public DespedidaSanta(Agente agente) {
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
        ACLMessage msg = agente.mensajeSanta.createReply(ACLMessage.INFORM);
        msg.setContent(normalizarTexto(agente.mensaje));
        agente.send(msg);
        
        System.out.println("\nAGENTE-> DICE A SANTA: '" + msg.getContent() +  "'");

        ACLMessage respuestaDespedida = agente.blockingReceive();

        if (respuestaDespedida.getPerformative() == ACLMessage.INFORM) {
            System.out.println("AGENTE -> SANTA DICE: " + respuestaDespedida.getContent());
        } else {
            System.out.println("AGENTE -> Error de protocolo");
        }

        this.agente.doDelete();

    }

}
