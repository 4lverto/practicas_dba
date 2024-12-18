package modelo.comportamientos.rudolph;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Rudolph;

/**
 * Clase que permite a Rudolph recibir y procesar mensajes.
 */
public class EvaluarClave extends OneShotBehaviour {

    /**
     * @brief Instancia del agente.
     */
    private Rudolph agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public EvaluarClave(Rudolph agente) {
        this.agente = agente;
    }

    @Override
    public void action() {
        ACLMessage msg = agente.blockingReceive();

        if (msg.getPerformative() == ACLMessage.REQUEST) {

            String contenido = msg.getContent();

            ACLMessage respuesta = msg.createReply();
            if (contenido.equals("hn9yar2x")) {
                respuesta.setPerformative(ACLMessage.AGREE);
                respuesta.setContent("OK");
                System.out.println("RUDOLPH -> Clave verificada.");
            } else {
                respuesta.setPerformative(ACLMessage.REFUSE);
                respuesta.setContent("Incorrecto");
                System.out.println("RUDOLPH -> Clave |" + contenido + "| incorrecta, deberia ser: |hn9yar2x|");
            }
            agente.send(respuesta);
        } else {
            System.out.println("RUDOLPH -> Error de protocolo, tipo de mensaje inesperado.");
            agente.doDelete();
        }
    }
}
