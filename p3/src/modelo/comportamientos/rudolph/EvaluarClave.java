package modelo.comportamientos.rudolph;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import modelo.Entorno;
/**
 * Clase que permite a Rudolph recibir y procesar mensajes.
 */
public class EvaluarClave extends OneShotBehaviour {

    private boolean finalizado = false;
    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.blockingReceive();

        if (msg != null) {
            String contenido = normalizarTexto(msg.getContent());
            System.out.println("RUDOLPH -> HE RECIBIDO: " + contenido);

            if (msg.getPerformative() == ACLMessage.REQUEST) {
                ACLMessage respuesta = msg.createReply();
                if (contenido.contains("CLAVE")) {
                    respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    respuesta.setContent("Posiciones confirmadas: X=10, Y=21");
                    System.out.println("RUDOLPH -> Posiciones enviadas correctamente.");
                } else {
                    respuesta.setPerformative(ACLMessage.FAILURE);
                    respuesta.setContent("Error al obtener las posiciones.");
                    System.out.println("RUDOLPH -> No se pudieron enviar las posiciones.");
                }
                myAgent.send(respuesta);
            } else {
                System.out.println("RUDOLPH -> Mensaje en formato incorrecto.");
            }
        } else {
            System.out.println("RUDOLPH -> Error de protocolo, tipo de mensaje inesperado.");
        }

        finalizado = true;
    }
}                                                                                                                                                                           