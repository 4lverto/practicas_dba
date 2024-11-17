package modelo.comportamientos;

import jade.core.behaviours.Behaviour;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.agentes.Agente;


public class DecidirMovimiento extends Behaviour {
    
    private Agente agente;

    public DecidirMovimiento(Agente agente) {
        this.agente = agente;
    }

    
    @Override
    public void action() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(DecidirMovimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        agente.decidirMovimiento();

    }

    @Override
    public boolean done() {
        return agente.objetivoAlcanzado();
    }
    
    
}
