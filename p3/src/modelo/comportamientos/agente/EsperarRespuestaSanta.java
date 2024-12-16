
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EsperarRespuestaSanta
 * 
 * @brief Comportamiento para esperar la respuesta a la propuesta por parte de 
 * Santa Claus.
 */
public class EsperarRespuestaSanta extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;
    
    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarRespuestaSanta(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.blockingReceive();
        
        if (respuesta != null) {
            if (respuesta.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                System.out.println("Propuesta aceptada por Santa Claus.");
                this.agente.establecerCodigoSecreto(extraerCodigoSecreto(respuesta.getContent()));
                this.agente.addBehaviour(new EstablecerCanalSeguroRudolph(this.agente));
            } else if (respuesta.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                System.out.println("Propuesta rechazada. Terminando ejecución.");
                this.agente.doDelete();
            }
            
            this.agente.removeBehaviour(this);
        }
    }

    /**
     * @brief Extrae el código secreto del mensaje.
     * 
     * @param contenido Mensaje que contiene el código secreto.
     * 
     * @return El código secreto ya extraído.
     */
    private String extraerCodigoSecreto(String contenido) {
        return "el_codigo_secreto_que_sea"; // No sé, una constante o lo que sea.
    }
}
