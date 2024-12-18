package modelo.agentes.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author rafalpv
 */
public class Despedida extends OneShotBehaviour {

    SantaClaus agente;

    public Despedida(SantaClaus santa) {
        this.agente = santa;
    }

    @Override
    public void action() {

        ACLMessage mensajeDeDespedidaAgente = agente.blockingReceive();

        if (mensajeDeDespedidaAgente.getPerformative() == ACLMessage.INFORM) {
            ACLMessage respuesta = mensajeDeDespedidaAgente.createReply(ACLMessage.INFORM);
            respuesta.setContent("!HoHoHo!");
            System.out.println("\n\tSanta ha recibido la despedida\n");
            agente.send(respuesta);
        } else {
            System.out.println("SANTA -> Error de Protocolo");
        }
        
        this.agente.doDelete();
    }

}
