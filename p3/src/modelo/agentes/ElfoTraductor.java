package modelo.agentes;

import jade.core.Agent;
import modelo.comunicacion.ComunicacionElfoTraductor;


import modelo.comportamientos.elfotraductor.Traducir;
import java.text.Normalizer;


/**
 *
 * @author migue-maca
 */
public class ElfoTraductor extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new Traducir());

    }

    @Override
    protected void takeDown() {
        
    }
}
