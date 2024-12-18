/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.comportamientos.agente;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Posicion;
import modelo.agentes.Agente;

/**
 *
 * @author eldoi
 */
public class SolicitarReno extends OneShotBehaviour {

    private static final Posicion IGNORE = new Posicion(-1, -1);

    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    private Posicion posicion_reno;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public SolicitarReno(Agente agente) {
        this.agente = agente;
    }

    private Posicion leerPosicion(String mensaje) {
        String[] valores = mensaje.split(",");
        return new Posicion(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
    }

    @Override
    public void action() {
        ACLMessage msg = agente.obtenerMensajeRudolph();

        ACLMessage respuesta = msg.createReply(ACLMessage.QUERY_REF);
        respuesta.setContent(agente.obtenerPosAgente().obtenerX() + "," + agente.obtenerPosAgente().obtenerY());
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SolicitarReno.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\nAGENTE -> ESTOY AQUI: " + respuesta.getContent() + ". DONDE DEBO IR ??" );
        agente.send(respuesta);

        msg = this.myAgent.blockingReceive();

        if (msg.getConversationId().equals(agente.obtenerCodigoSecreto())
                && msg.getPerformative() == ACLMessage.INFORM) {
            posicion_reno = leerPosicion(msg.getContent());
            System.out.println("\n\tPosicion reno recibida: " + posicion_reno.obtenerX() + " " + posicion_reno.obtenerY());
            agente.modificarMensajeRudolph(msg);
            agente.establecerPosObjetivo(posicion_reno.obtenerX(), posicion_reno.obtenerY());
        } else {
            System.out.println("\n\tError en el protocolo de conversacion");
            agente.doDelete();
        }
    }

    @Override
    public int onEnd() {
        if (posicion_reno.sonIguales(IGNORE)) {
            return 1;
        } else {
            return 0;
        }
    }
}