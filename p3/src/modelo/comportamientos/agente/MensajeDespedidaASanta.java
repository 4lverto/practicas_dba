package modelo.comportamientos.agente;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import modelo.agentes.Agente;

/**
 *
 * @author rafalpv
 */
public class MensajeDespedidaASanta extends OneShotBehaviour {

    private Agente agente;

    public MensajeDespedidaASanta(Agente agente) {
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
        ACLMessage mensajeDeDespedida = agente.obtenerMensajeSanta().createReply(ACLMessage.INFORM);
        mensajeDeDespedida.setContent(normalizarTexto(agente.obtenerMensaje()));
        agente.send(mensajeDeDespedida);

        ACLMessage respuestaDespedida = agente.blockingReceive();

        if (respuestaDespedida.getPerformative() == ACLMessage.INFORM) {
            System.out.println("He recibido " + respuestaDespedida.getContent());
        } else {
            System.out.println("Error de protocolo");
        }

        this.agente.doDelete();

    }

}
