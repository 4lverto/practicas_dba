
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class RescatarRenos
 * 
 * @brief Comportamiento personalizado para ir rescatando los renos.
 */
public class RescatarRenos extends Behaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public RescatarRenos(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        // Si aún hay renos por rescatar:
        if (this.agente.numRenosRescatados() < this.agente.totalRenos()) {
            // Aprovechar el mensaje iniciado anteriormente (lo tiene el agente):
            ACLMessage consulta = this.agente.obtenerMensajeRudolph().createReply();
            consulta.setPerformative(ACLMessage.QUERY_REF);
            consulta.setContent("{\"action\": \"ask_for_coordinates_reindeer_" + 
                    this.agente.numRenosRescatados() + "\"}");
            
            this.agente.modificarMensajeRudolph(consulta);
            this.agente.send(consulta);
            this.agente.addBehaviour(new EsperarCoordenadasReno(this.agente));
        }
    }

    /**
     * @brief Método para decidir el momento de parada del comportamiento.
     * @return True si ya hemos rescatado todos los renos, False en caso contrario
     */
    @Override
    public boolean done() {
        return this.agente.numRenosRescatados() >= this.agente.totalRenos();
    }
}
