
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EntregarRenosSanta
 * 
 * @brief Comportamiento para entregar los renos a Santa Claus (se le confirma 
 * la llegada a su posición).
 */
public class EntregarRenosSanta extends OneShotBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EntregarRenosSanta(Agente agente) {
        this.agente = agente;
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        // Aprovechar el canal previamente creado con Santa Claus:
        ACLMessage mensaje = this.agente.obtenerMensajeSanta().createReply();
        
        mensaje.setPerformative(ACLMessage.INFORM);
        mensaje.setContent("{\"message\": \"Rakas Joulupukki Olen täällä Kiitos\"}");
        
        this.agente.modificarMensajeSanta(mensaje);
        this.agente.send(mensaje);
        System.out.println("Renos entregados a Santa Claus.");
    }
}
