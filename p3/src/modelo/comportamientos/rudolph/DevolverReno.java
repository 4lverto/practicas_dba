package modelo.comportamientos.rudolph;

import modelo.Posicion;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Rudolph;

/**
 *
 * @author eldoi
 */
public class DevolverReno extends CyclicBehaviour {

    private static final Posicion IGNORE = new Posicion(-1, -1);

    /**
     * @brief Instancia del agente.
     */
    private Rudolph agente;
    private Posicion[] renos;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public DevolverReno(Posicion[] renos, Rudolph agente) {
        this.agente = agente;
        this.renos = renos;
    }

    private Posicion leerPosicion(String mensaje) {
        String[] valores = mensaje.split(",");
        return new Posicion(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
    }

    private static double distancia(Posicion origen, Posicion dest) {
        return Math.sqrt(Math.pow((origen.obtenerX() - dest.obtenerX()), 2.0) + Math.pow((origen.obtenerY() - dest.obtenerY()), 2.0));
    }

    @Override
    public void action() {
        ACLMessage msg = agente.blockingReceive();

        if (msg.getPerformative() == ACLMessage.QUERY_REF) {
            ACLMessage respuesta;
            if (msg.getConversationId().equals("hn9yar2x")) {
                String contenido = msg.getContent();
                respuesta = msg.createReply(ACLMessage.INFORM);

                Posicion buscador = leerPosicion(contenido);
                int best = -1;
                double best_score = 99999999;
                for (int i = 0; i < renos.length; ++i) {
                    if (!renos[i].sonIguales(IGNORE)) {
                        double current_score = distancia(buscador, renos[i]);
                        if (current_score < best_score) {
                            best = i;
                            best_score = current_score;
                        }
                    }
                }
                if (best == -1) {
                    respuesta.setContent(IGNORE.obtenerX() + "," + IGNORE.obtenerY());
                } else {
                    respuesta.setContent(renos[best].obtenerX() + "," + renos[best].obtenerY());
                    renos[best] = IGNORE;
                }
                agente.send(respuesta);
            } else {
                System.out.println(("RUDOLPH -> Error, codigo incorrecto"));
                agente.doDelete();
            }
        } else {
            System.out.println(("RUDOLPH -> Error de protocolo"));
            agente.doDelete();
        }
    }
}
