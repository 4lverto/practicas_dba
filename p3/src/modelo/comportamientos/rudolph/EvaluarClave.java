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
            if (contenido.equals("ManoloElMejor")) {
                respuesta.setPerformative(ACLMessage.AGREE);
                respuesta.setContent("OK");
                System.out.println("\nRUDOLPH -> PERFECTO, CLAVE VERIFICADA");
            } else {
                respuesta.setPerformative(ACLMessage.REFUSE);
                respuesta.setContent("Incorrecto");
                System.out.println("\nRUDOLPH -> LA CLAVE |" + contenido + "| ES INCORRECTA, DEBERIA SER: |ManoloElMejor|");
            }
            agente.send(respuesta);
        } else {
            System.out.println("\n\tRUDOLPH -> Error de protocolo, tipo de mensaje inesperado.");
            agente.doDelete();
        }
    }
}