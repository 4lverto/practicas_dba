
package modelo.comportamientos.agente;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EsperarTraduccionElfo
 * 
 * @brief Comportamiento para esperar la traducción del Elfo Traductor.
 */
public class EsperarTraduccionElfo extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    /**
     * @brief Constructor por defecto para instanciar al agente.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarTraduccionElfo(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.blockingReceive();
        
        if (respuesta != null && respuesta.getPerformative() == ACLMessage.INFORM) {
            System.out.println("Traducción recibida: " + respuesta.getContent());
            this.agente.addBehaviour(new ProponerMisionSanta(
                    this.agente, 
                    respuesta.getContent()));
            this.agente.removeBehaviour(this);
        }
    }
    
}
