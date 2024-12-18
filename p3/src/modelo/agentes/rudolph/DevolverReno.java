package modelo.agentes.rudolph;

import modelo.Posicion;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eldoi
 */
public class DevolverReno extends CyclicBehaviour {

    private static final Posicion IGNORE = new Posicion(-1, -1);

    /**
     * @brief Instancia del agente.
     */
    private final Rudolph agente;
    
    int best = -1;

    /**
     * @param renos
     * @brief Constructor por defecto.
     * @param agente Agente que se toma para la copia.
     */
    public DevolverReno(Rudolph agente) {
        this.agente = agente;
    }

    private Posicion leerPosicion(String mensaje) {
        String[] valores = mensaje.split(",");
        return new Posicion(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
    }

    private static double distancia(Posicion origen, Posicion dest) {
        return Math.sqrt(Math.pow((origen.obtenerFil() - dest.obtenerFil()), 2.0) + Math.pow((origen.obtenerCol() - dest.obtenerCol()), 2.0));
    }

    @Override
    public void action() {
        ACLMessage msg = agente.blockingReceive();

        if (msg.getPerformative() == ACLMessage.QUERY_REF) {
            ACLMessage respuesta;
            if (msg.getConversationId().equals("ManoloElMejor")) {
                String contenido = msg.getContent();
                respuesta = msg.createReply(ACLMessage.INFORM);

                Posicion buscador = leerPosicion(contenido);
                
                
                //Marcamos el ultimo reno para ignorar
                if (best != -1) {
                    agente.renos[best] = IGNORE;
                }
                
                best = -1;
                double best_score = 99999999;
                for (int i = 0; i < agente.renos.length; ++i) {
                    if (!agente.renos[i].sonIguales(IGNORE)) {
                        double current_score = distancia(buscador, agente.renos[i]);
                        if (current_score < best_score) {
                            best = i;
                            best_score = current_score;
                        }
                    }
                }
                if (best == -1) {
                    respuesta.setContent(IGNORE.obtenerFil() + "," + IGNORE.obtenerCol());
                } else {
                    respuesta.setContent(agente.renos[best].obtenerFil() + "," + agente.renos[best].obtenerCol());
                }
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DevolverReno.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("\nRUDOLPH-> EL SIGUIENTE RENO ESTA AQUI: '" + respuesta.getContent() +  "'");
                agente.send(respuesta);
            } else {
                System.out.println(("\nRUDOLPH -> EL CODIGO ES INCORRECTO"));
                agente.doDelete();
            }
        } else {
            System.out.println(("\n\tRUDOLPH -> Error de protocolo"));
            agente.doDelete();
        }
    }
}