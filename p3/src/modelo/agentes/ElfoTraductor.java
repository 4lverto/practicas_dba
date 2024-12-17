package modelo.agentes;

import jade.core.Agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;

/**
 *
 * @author migue-maca
 */
public class ElfoTraductor extends Agent {

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {

            @Override
            public void action() {
                ACLMessage msg = blockingReceive();

                if (msg.getPerformative() == ACLMessage.REQUEST) {

                    String contenido = normalizarTexto(msg.getContent());
                    System.out.println("Elfo recibe -> " + contenido);

                    ACLMessage respuesta = msg.createReply(ACLMessage.INFORM);

                    if (contenido.substring(0, 4).equals("Bro ") && contenido.substring(contenido.length() - 8, contenido.length()).equals(" En Plan")) {
                        respuesta.setContent("Rakas Joulupukki " + contenido.substring(4, contenido.length() - 8) + " Kiitos");
                    }

                    send(respuesta);
                } else {
                    System.out.println(("\nELFO TRADUCTOR -> Error de protocolo"));
                    doDelete();
                }

            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("Finalizado el agente " + this.getLocalName());
    }
}
