package modelo.comunicacion;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import modelo.Posicion;
import modelo.agentes.Agente;
import modelo.estados.EstadosAgente;

/**
 *
 * @author rafalpv
 */
public class ComunicacionAgente extends Behaviour {

    private Agente agente;

    private EstadosAgente estado;
    private AID ET, SC;  // AID de ElfoTraductor

    private Posicion porObjetivo;

    private ACLMessage msgElfoTraductor, msgSantaClaus;

    public ComunicacionAgente(Agente agente) {
        super(agente);
        this.agente = agente;
        this.estado = EstadosAgente.INICIO_MISION;
        this.establecerComunicacion();
    }

    private void establecerComunicacion() {
        this.ET = new AID("ET", AID.ISLOCALNAME);
        this.SC = new AID("SC", AID.ISLOCALNAME);
    }

    @Override
    public void action() {
        switch (this.estado) {
            case INICIO_MISION:
                msgElfoTraductor = new ACLMessage(ACLMessage.REQUEST);

                msgElfoTraductor.addReceiver(this.ET);  // Enviar mensaje al AID de ElfoTraductor
                msgElfoTraductor.setContent("Bro, estoy dispuesto a ofrecerme voluntario para la misión, En plan.");
                //msgET.setConversationId("translation-request");

                agente.send(msgElfoTraductor);  // Enviar el mensaje

                this.estado = EstadosAgente.RECIBIR_CONFIRMACION_ELFO_TRADUCTOR;

            case RECIBIR_CONFIRMACION_ELFO_TRADUCTOR:
                msgElfoTraductor = agente.blockingReceive();

                if (msgElfoTraductor != null && msgElfoTraductor.getPerformative() == ACLMessage.INFORM) {
                    if (msgElfoTraductor.getSender().equals(this.ET)) {
                        System.out.println(msgElfoTraductor.getContent());
                        /*String mensajeToSanta = msgElfoTraductor.getContent();
                        
                        //Enviar Proposal a Santa
                        msgSantaClaus = new ACLMessage(ACLMessage.PROPOSE);
                        msgSantaClaus.addReceiver(this.SC);
                        msgSantaClaus.setReplyWith("validacion-request");
                        msgSantaClaus.setContent(mensajeToSanta);
                        //msgSantaClaus.setConversationId();
                        
                        agente.send(msgSantaClaus);*/

                        this.estado = EstadosAgente.ESTADO_FINAL;
                    } else {
                        System.out.println("Se esperaba que el mensaje fuese de " + this.ET);
                    }
                } else {
                    System.out.println("Se espera mensaje INFORM. " + agente.getLocalName());
                }

        }
    }

    @Override
    public boolean done() {
        return this.estado == EstadosAgente.ESTADO_FINAL;
    }

}
