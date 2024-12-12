
package modelo.agentes;

import jade.core.Agent;

/**
 *
 * @author migue-maca
 */
public class SantaClaus extends Agent {
    
    @Override
    protected void setup() {
        
    }

    @Override
    protected void takeDown() {
        System.out.println("Finalizado el agente " + this.getLocalName());
    }
    
}
