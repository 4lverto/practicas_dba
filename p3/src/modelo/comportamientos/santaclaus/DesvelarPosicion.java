package modelo.comportamientos.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.agentes.SantaClaus;

public class DesvelarPosicion extends OneShotBehaviour {

    SantaClaus santaClaus;

    public DesvelarPosicion(SantaClaus santaClaus) {
        this.santaClaus = santaClaus;
    }

    @Override
    public void action() {

        ACLMessage msg = santaClaus.blockingReceive();

        if (msg.getPerformative() == ACLMessage.INFORM) {
            santaClaus.modificarMensajeAgente(msg);
            
            ACLMessage respuesta = santaClaus.obtenerMensajeAgente().createReply(ACLMessage.INFORM);
            respuesta.setContent(santaClaus.obtenerPosicionSantaClaus().toString());
            santaClaus.send(respuesta);
        }

    }
}
