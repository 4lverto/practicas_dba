/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.comportamientos.elfotraductor;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;

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
            System.out.println("ELFO TRADUCTOR -> HE RECIBIDO:" + contenido);

            ACLMessage respuesta = msg.createReply(ACLMessage.INFORM);

            if (contenido.substring(0, 4).equals("Bro ") && contenido.substring(contenido.length() - 8, contenido.length()).equals(" En Plan")) {
                respuesta.setContent("Rakas Joulupukki " + contenido.substring(4, contenido.length() - 8) + " Kiitos");
            } else if (contenido.substring(0, 17).equals("Rakas Joulupukki ") && contenido.substring(contenido.length() - 7, contenido.length()).equals(" Kiitos")) {
                respuesta.setContent("Bro " + contenido.substring(17, contenido.length() - 7) + " En Plan");
            } else if (contenido.substring(0, 13).equals("Hyvaa joulua ") && contenido.substring(contenido.length() - 13, contenido.length()).equals(" Nahdaan pian")) {
                respuesta.setContent("Bro " + contenido.substring(13, contenido.length() - 13) + " En Plan");
            } else {
                System.out.println(("\nELFO TRADUCTOR -> Error, mensaje en ningun formato conocido"));
                this.myAgent.doDelete();
            }

            this.myAgent.send(respuesta);
        } else {
            System.out.println(("\nELFO TRADUCTOR -> Error de protocolo"));
            this.myAgent.doDelete();
        }
    }
}
