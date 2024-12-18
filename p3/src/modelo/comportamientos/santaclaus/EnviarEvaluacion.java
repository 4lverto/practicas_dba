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
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage msg = agente.obtenerMensajeAgente();
        String contenido = agente.obtenerMensajeTraducido();

        ACLMessage respuesta;
        if (agente.obtenerAceptado()) {
                respuesta = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
                respuesta.setContent(contenido);
                agente.send(respuesta);
            } else {
                respuesta = msg.createReply(ACLMessage.REJECT_PROPOSAL);
                respuesta.setContent(contenido);
                agente.send(respuesta);
                agente.doDelete();
            }
            
    }
}