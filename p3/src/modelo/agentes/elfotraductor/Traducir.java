package modelo.agentes.elfotraductor;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eldoi
 */
public class Traducir extends CyclicBehaviour {

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    @Override
    public void action() {
        ACLMessage msg = this.myAgent.blockingReceive();

        if (msg.getPerformative() == ACLMessage.REQUEST) {

            String contenido = normalizarTexto(msg.getContent());
            System.out.println("\n\tEl elfo traductor recibe:'" + contenido + "'");

            ACLMessage respuesta = msg.createReply(ACLMessage.INFORM);

            if (contenido.substring(0, 4).equals("Bro ") && contenido.substring(contenido.length() - 8, contenido.length()).equals(" En Plan")) {
                respuesta.setContent("Rakas Joulupukki " + contenido.substring(4, contenido.length() - 8) + " Kiitos");
            } else if (contenido.substring(0, 13).equals("Hyvaa joulua ") && contenido.substring(contenido.length() - 13, contenido.length()).equals(" Nahdaan pian")) {
                respuesta.setContent("Bro " + contenido.substring(13, contenido.length() - 13) + " En Plan");
            } else {
                System.out.println(("\n\tError en Elfo Traductor -> Error, mensaje en ningun formato conocido"));
                this.myAgent.doDelete();
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Traducir.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("\nELFO TRADUCTOR -> TOMA EL MENSAJE TRADUCIDO: '"+respuesta.getContent()+"'");
            this.myAgent.send(respuesta);
    
        } else {
            System.out.println(("\n\tError en Elfo Traductor -> Error de protocolo"));
            this.myAgent.doDelete();
        }
    }
}
