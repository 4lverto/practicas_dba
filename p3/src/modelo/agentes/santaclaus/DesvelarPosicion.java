package modelo.agentes.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class DesvelarPosicion extends OneShotBehaviour {

    SantaClaus santaClaus;

    public DesvelarPosicion(SantaClaus santaClaus) {
        this.santaClaus = santaClaus;
    }

    @Override
    public void action() {

        ACLMessage mensajeSolicitudCoordenadas = santaClaus.blockingReceive();
        

        if (mensajeSolicitudCoordenadas.getPerformative() == ACLMessage.INFORM) {
            santaClaus.mensajeAgente = mensajeSolicitudCoordenadas;
            
            ACLMessage mensajeConCoordenadas = santaClaus.mensajeAgente.createReply(ACLMessage.INFORM);
            mensajeConCoordenadas.setContent(santaClaus.posSantaClaus.toString());
            santaClaus.send(mensajeConCoordenadas);
        }

    }
}
