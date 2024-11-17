package modelo.comportamientos;

import jade.core.behaviours.Behaviour;
import modelo.agentes.Agente;

public class PasosTotales extends Behaviour{

    private Agente agente;

    public PasosTotales(Agente agente) {
        this.agente = agente;
    }
    
    @Override
    public void action() {
        if(agente.objetivoAlcanzado()){
            System.out.println("Objetivo Alcanzado, se han dado un total de " + (agente.obtenerPasosTotales()));
        }
    }

    @Override
    public boolean done() {
        return agente.objetivoAlcanzado();
    }
}
