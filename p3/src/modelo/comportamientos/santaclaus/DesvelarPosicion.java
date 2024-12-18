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

        ACLMessage mensajeSolicitudCoordenadas = santaClaus.blockingReceive();
        

        if (mensajeSolicitudCoordenadas.getPerformative() == ACLMessage.INFORM) {
            santaClaus.modificarMensajeAgente(mensajeSolicitudCoordenadas);
            
            ACLMessage mensajeConCoordenadas = santaClaus.obtenerMensajeAgente().createReply(ACLMessage.INFORM);
            mensajeConCoordenadas.setContent(santaClaus.obtenerPosicionSantaClaus().toString());
            santaClaus.send(mensajeConCoordenadas);
        }

    }
}
