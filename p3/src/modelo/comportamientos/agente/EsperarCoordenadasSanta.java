
package modelo.comportamientos.agente;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;

/**
 * @class EsperarCoordenadasSanta
 * 
 * @brief Comportamiento para esperar las coordenadas de Santa Claus.
 */
public class EsperarCoordenadasSanta extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    /**
     * @brief Constructor por defecto.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarCoordenadasSanta(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.blockingReceive();
        
        if (respuesta != null && respuesta.getPerformative() == ACLMessage.INFORM) {
            System.out.println("Coordenadas de Santa Claus recibidas: " + 
                    respuesta.getContent());
            this.agente.modificarMensajeSanta(respuesta);
            //this.agente.establecerPosSanta(, ); // Hay que darle una vuelta a 
            // cómo queremos extraer las coordenadas (variará dependiendo de la 
            // forma en la que se envíen desde SantaClaus.
            
            // Por aquí es necesario añadir una método para que el Agente Buscador 
            // vaya hacia Santa Claus (que llame al que tenemos para moverse por 
            // el mapa).
            this.agente.addBehaviour(new EntregarRenosSanta(this.agente));
            
            this.agente.removeBehaviour(this);
        }
    }
}