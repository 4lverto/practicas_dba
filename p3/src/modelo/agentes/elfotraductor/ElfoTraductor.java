package modelo.agentes.elfotraductor;

import jade.core.Agent;

/**
 *
 * @author migue-maca
 */
public class ElfoTraductor extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Traducir());
    }
}
