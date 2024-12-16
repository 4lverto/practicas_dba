
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EsperarCoordenadasReno
 * 
 * @brief Comportamiento para esperar las coordenadas de un reno que previamente 
 * se han solicitado a Rudolph.
 */
public class EsperarCoordenadasReno extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarCoordenadasReno(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.blockingReceive();
        
        if (respuesta != null && respuesta.getPerformative() == ACLMessage.INFORM) {
            System.out.println("Coordenadas de reno recibidas: " + respuesta.getContent());
            this.agente.rescatarReno(); // Por ahora solo incrementa en uno el 
                                        // número de renos rescatados (habrá que 
                                        // decodificar las coordenadas antes para 
                                        // crear una Posicion). Tiene que llama 
                                        // al método (o comportamiento) que tenemos 
                                        // para moverse por el mapa).
            this.agente.modificarMensajeRudolph(respuesta);

            if (this.agente.numRenosRescatados() == this.agente.totalRenos()) {
                this.agente.addBehaviour(new SolicitarTraduccionFinal(this.agente));
            }
        }
    }
}
