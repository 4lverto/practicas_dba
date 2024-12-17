/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.comportamientos.santaclaus;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import modelo.agentes.SantaClaus;
import modelo.agentes.Agente;

/**
 *
 * @author eldoi
 */
public class EnviarEvaluacion extends OneShotBehaviour {

    private final SantaClaus agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public EnviarEvaluacion(SantaClaus agente) {
        this.agente = agente;
    }

    
        /**
     * @brief No se ni pa qué voy a usar esto pero bueno
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
        msg.setContent(agente.obtenerMensajeTraducido());
        msg.setConversationId("Evaluacion");
        agente.send(msg);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals("Evaluacion")
                && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
            String contenido = normalizarTexto(msg.getContent());
            System.out.println("Respuesta recibida a peticion: " + contenido);
            agente.establecerMensajeTraducido(contenido);
        } else {
            if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL)
                System.out.println("Peticion denegada por santa");
            else
                System.out.println("Error en el protocolo de conversacion");
            agente.doDelete();
        }
    }
}
