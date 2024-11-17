package modelo.comportamientos;

import jade.core.behaviours.Behaviour;
import java.util.logging.Level;
import java.lang.System.Logger;
import modelo.agentes.Agente;


public class ActualizarMemoria extends Behaviour {
    
    private Agente agente;

    public ActualizarMemoria(Agente agente) {
        this.agente = agente;
    }

    
    @Override
    public void action() {
        if(!agente.objetivoAlcanzado())
            agente.actualizarMemoria();
    }

    @Override
    public boolean done() {
        return agente.objetivoAlcanzado();
    }
    
}
