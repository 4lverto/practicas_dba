
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;


/**
 * @class EsperarTraduccionFinal
 * 
 * @brief Comportamiento para esperar la traducción final al Elfo Traductor, 
 * justo antes de volverse a comunicar con Santa Claus.
 */
public class EsperarTraduccionFinal extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarTraduccionFinal(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.blockingReceive();
        
        if (respuesta != null && respuesta.getPerformative() == ACLMessage.INFORM) {
            System.out.println("Traducción final recibida: " + respuesta.getContent());
            this.agente.addBehaviour(new ConsultarCoordenadasSanta(
                    this.agente,
                    respuesta.getContent()));
            
            this.agente.removeBehaviour(this);
        }
    }
}
