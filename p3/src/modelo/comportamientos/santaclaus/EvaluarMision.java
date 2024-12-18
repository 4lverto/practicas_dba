package modelo.comportamientos.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import modelo.agentes.SantaClaus;

/**
 *
 * @author Germán López
 */
public class EvaluarMision extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private SantaClaus agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EvaluarMision(SantaClaus agente) {
        this.agente = agente;
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    @Override
    public void action() {
        ACLMessage msg = agente.blockingReceive();

        if (msg.getPerformative() == ACLMessage.PROPOSE) {

            String contenido = normalizarTexto(msg.getContent());
            System.out.println("SANTA -> HE RECIBIDO:" + contenido);

            ACLMessage respuesta;
            if (contenido.substring(0, 17).equals("Rakas Joulupukki ") && contenido.substring(contenido.length() - 7, contenido.length()).equals(" Kiitos")) {
                if (Math.floor(Math.random() * 10) <= 7) {
                    agente.establecerMensajeTraducido("Hyvaa joulua hn9yar2x Nahdaan pian");
                    agente.modificarMensajeAgente(msg);
                    agente.establecerAceptado(true);
                } else {
                    agente.establecerMensajeTraducido("Hyvaa joulua NO Nahdaan pian");
                    agente.modificarMensajeAgente(msg);
                }
            } else {
                System.out.println(("\nSANTA -> Error, mensaje en formato incorrecto"));
                agente.doDelete();
            }
        } else {
            System.out.println(("\nSANTA -> Error de protocolo"));
            agente.doDelete();
        }
        
        //Version que responde directemente (puede ser util para luego
        
        //if (msg.getPerformative() == ACLMessage.REQUEST) {
        //
        //    String contenido = normalizarTexto(msg.getContent());
        //    System.out.println("SANTA -> HE RECIBIDO:" + contenido);
        //
        //    ACLMessage respuesta;
        //    if (contenido.substring(0, 17).equals("Rakas Joulupukki ") && contenido.substring(contenido.length() - 7, contenido.length()).equals(" Kiitos")) {
        //        if (Math.floor(Math.random() * 10) <= 7) {
        //            respuesta = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
        //            respuesta.setContent("Hyvaa joulua hn9yar2x Nahdaan pian");
        //        } else {
        //            respuesta = msg.createReply(ACLMessage.REJECT_PROPOSAL);
        //            respuesta.setContent("\"Hyvaa joulua NO Nahdaan pian");
        //        }
        //        agente.send(respuesta);
        //        agente.addBehaviour(new DarCoordenadasSanta());
        //    } else {
        //        System.out.println(("\nSANTA -> Error, mensaje en formato incorrecto"));
        //        agente.doDelete();
        //    }
        //} else {
        //    System.out.println(("\nSANTA -> Error de protocolo"));
        //    agente.doDelete();
        //}
    }

}