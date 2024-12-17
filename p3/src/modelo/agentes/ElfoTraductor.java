package modelo.agentes;

import jade.core.Agent;
import modelo.comunicacion.ComunicacionElfoTraductor;

/**
 *
 * @author migue-maca
 */
public class ElfoTraductor extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new ComunicacionElfoTraductor(this));
    }

    @Override
    protected void takeDown() {
        
    }
}
