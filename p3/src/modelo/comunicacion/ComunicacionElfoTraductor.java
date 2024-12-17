package modelo.comunicacion;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import modelo.agentes.ElfoTraductor;
import modelo.estados.EstadosElfoTraductor;

/**
 *
 * @author rafalpv
 */
public class ComunicacionElfoTraductor extends Behaviour {

    private EstadosElfoTraductor estado;
    private ElfoTraductor elfoTraductor;

    private AID AB;  // AID de ElfoTraductor, debería coincidir con el nombre que el agente busca

    private ACLMessage mensajeAgenteBuscador;

    public ComunicacionElfoTraductor(ElfoTraductor elfoTraductor) {
        super(elfoTraductor);
        this.elfoTraductor = elfoTraductor;
        this.estado = EstadosElfoTraductor.ESPERANDO_MENSAJE_AGENTE;
        prepararComunicacion();
    }

    private void prepararComunicacion() {
        this.AB = new AID("AB", AID.ISLOCALNAME);

    }

    @Override
    public void action() {
        switch (this.estado) {
            case ESPERANDO_MENSAJE_AGENTE -> {

                mensajeAgenteBuscador = elfoTraductor.blockingReceive();

                if (mensajeAgenteBuscador == null) {
                    System.out.println("No se recibió ningún mensaje. ElfoTraductor sigue esperando.");
                    return;
                }

                // Verificar si el mensaje proviene del AgenteBuscador (AB)
                if (!mensajeAgenteBuscador.getSender().equals(this.AB)) {
                    System.out.println("Mensaje recibido de un remitente desconocido: " + mensajeAgenteBuscador.getSender().getLocalName());
                    return;
                }

                // Verificar si el mensaje tiene el performativo correcto (REQUEST)
                if (mensajeAgenteBuscador.getPerformative() == ACLMessage.REQUEST) {

                    String mensajeTraducido = traducirAFinlandes(mensajeAgenteBuscador.getContent());

                    // Respuesta al Agente Traductor
                    mensajeAgenteBuscador = mensajeAgenteBuscador.createReply(ACLMessage.INFORM);
                    mensajeAgenteBuscador.setContent(mensajeTraducido);
                    elfoTraductor.send(mensajeAgenteBuscador);
                    this.estado = EstadosElfoTraductor.ESTADO_FINAL;

                } else {
                    System.out.println("El mensaje recibido no tiene el performativo REQUEST.");
                }

                break;
            }
            default ->
                throw new AssertionError("Estado inesperado: " + this.estado);
        }
    }

    @Override
    public boolean done() {
        return this.estado == EstadosElfoTraductor.ESTADO_FINAL;
    }

    public static String traducirAFinlandes(String msg) {
        String finalMsg = msg;

        if (finalMsg.startsWith("Bro")) {
            finalMsg = finalMsg.replaceFirst("Bro", "Joulupukki");
        }

        if (finalMsg.endsWith("En plan.")) {
            finalMsg = finalMsg.replaceFirst("En plan\\.$", "Kiitos.");
        }

        return finalMsg;
    }
    /*
    public static String tradcuriAMilenial(String msg) {
        String finalMsg = msg;

        if (finalMsg.startsWith("Hyvää joulua")) {
            finalMsg = finalMsg.replaceFirst("Hyvää joulua", "Bro");
        }

        if (finalMsg.endsWith("Nähdään pian.")) {
            finalMsg = finalMsg.replaceFirst("Nähdään pian\\.?\\s*$", "En plan.");
        }

        return finalMsg;
    }
     */
}
