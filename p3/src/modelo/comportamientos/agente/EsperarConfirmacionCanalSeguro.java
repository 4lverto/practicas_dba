
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EsperarConfirmacionCanalSeguro
 * 
 * @brief Comportamiento para esperar la confirmación de un canal seguro de 
 * comunicación por parte de Rudolph para pasar a ir obteniendo las coordenadas 
 * de los renos.
 */
public class EsperarConfirmacionCanalSeguro extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarConfirmacionCanalSeguro(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.blockingReceive();
        
        if (respuesta != null) {
            if (respuesta.getPerformative() == ACLMessage.INFORM) {
                System.out.println("Canal seguro establecido. Comenzando rescate de renos.");
                this.agente.addBehaviour(new RescatarRenos(this.agente));
            } else if (respuesta.getPerformative() == ACLMessage.FAILURE) {
                System.out.println("Fallo al establecer canal seguro: " + respuesta.getContent());
                this.agente.doDelete();
            }
            
            this.agente.removeBehaviour(this);
        }
    }
}
